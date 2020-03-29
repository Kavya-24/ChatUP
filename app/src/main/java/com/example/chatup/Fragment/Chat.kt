package com.example.chatup.Fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatup.R
import com.example.chatup.Utils.FirestoreUtil
import com.example.chatup.Utils.StorageUtil.uploadMessageImage
import com.example.chatup.glide_module.GlideApp
import com.example.chatup.model.ImageMessage
import com.example.chatup.model.MessageType
import com.example.chatup.model.TextMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.list_img_message.*
import java.io.ByteArrayOutputStream
import java.util.*


class Chat : AppCompatActivity() {
    //Request code to send image
    val RC_SELECT_IMAGE = 2

    private lateinit var currentChannelId: String
    private lateinit var messagesListenerRegistration: ListenerRegistration
    private var initMsg = true
    private lateinit var messageSection: Section
    private lateinit var selectedImageByes: ByteArray

    //The above is for image message
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        //To get the arrow to find its way back to main activity
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val un = intent.getStringExtra("user_name")
        supportActionBar?.title = un


        val otherUsrId = intent.getStringExtra("userid")

        if (otherUsrId != null) {
            FirestoreUtil.getChatChannel(otherUsrId) { channelId ->
                //This loads all the functions
                currentChannelId = channelId
                messagesListenerRegistration =
                    FirestoreUtil.addChatMessagesListener(channelId, this, this::onMessagesChange)
                imageView_send.setOnClickListener {
                    val msgToSend = TextMessage(
                        editText_message.text.toString(),
                        Calendar.getInstance().time,
                        FirebaseAuth.getInstance().currentUser!!.uid,
                        MessageType.TXT
                    )
                    editText_message.setText("")
                    FirestoreUtil.sendMessgae(msgToSend, currentChannelId)
                }

                //To send image messages
                fab_send_image.setOnClickListener {

                    val intent = Intent().apply {
                        type = "image/*"
                        //This was to define the path
                        action = Intent.ACTION_GET_CONTENT
                        //To fetch the image and then render it
                        putExtra(
                            Intent.EXTRA_MIME_TYPES,
                            arrayOf("image/jpeg", "image/png")
                        )
                    }
                    //        startActivityForResult(intent, RC_SELECT_IMAGE)
                    startActivityForResult(
                        Intent.createChooser(intent, "Select Image"),
                        RC_SELECT_IMAGE
                    )
                    //  startActivityForResult(intent, RC_SELECT_IMAGE)
//                    val intent = Intent(Intent.ACTION_PICK)
//                    intent.type = "image/*"
//                    startActivityForResult(intent, RC_SELECT_IMAGE)
                }


            }
        }


    }

    //  @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK &&
            data != null && data.data != null
        ) {
            val selectedImagePath = data.data

            val selectedImageBmp =
                MediaStore.Images.Media.getBitmap(contentResolver, selectedImagePath)
            //We use output stream to convert
            val outputStream = ByteArrayOutputStream()
            //Convert it
            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            selectedImageByes = outputStream.toByteArray()
            GlideApp.with(this).load(selectedImageByes).into(imageview_message_image)

            uploadMessageImage(selectedImageByes) { imagePath ->
                //This will store the message path of what we ll be sending
                val messageToSend =
                    ImageMessage(
                        imagePath, Calendar.getInstance().time,
                        FirebaseAuth.getInstance().currentUser!!.uid
                    )

                FirestoreUtil.sendMessgae(messageToSend, currentChannelId)
            }
        }
    }


    private fun onMessagesChange(message: List<Item>) {
        //message item in this
        //  toast("Loading Chat")
        //This function is called when the messgaes refresh
        fun init() {
            recycler_view_messages.apply {
                layoutManager = LinearLayoutManager(this@Chat)
                adapter = GroupAdapter<ViewHolder>().apply {
                    messageSection = Section(message)
                    //Attach it to the adapter
                    this.add(messageSection)
                }
            }

            initMsg = false
        }

        fun update() = messageSection.update(message)

        if (initMsg) {
            init()
        } else {
            update()
        }

        //We want it to show the last message
        recycler_view_messages.scrollToPosition(recycler_view_messages.adapter?.itemCount!! - 1)
    }

}
