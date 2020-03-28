package com.example.chatup.Fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatup.R
import com.example.chatup.Utils.FirestoreUtil
import com.example.chatup.model.MessageType
import com.example.chatup.model.TextMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*

class Chat : AppCompatActivity() {


    private lateinit var messagesListenerRegistration: ListenerRegistration
    private var initMsg = true
    private lateinit var messageSection: Section
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
                    FirestoreUtil.sendMessgae(msgToSend, channelId)
                }

                //To send image messages
                fab_send_image.setOnClickListener {
                    //TODO : Image
                }


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
