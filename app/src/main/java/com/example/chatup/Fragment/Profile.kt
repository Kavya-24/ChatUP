package com.example.chatup.Fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chatup.R
import com.example.chatup.Utils.FirestoreUtil
import com.example.chatup.Utils.StorageUtil
import com.example.chatup.glide_module.GlideApp
import com.example.chatup.ui.auth.SignIn_act
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.io.ByteArrayOutputStream

/**
 * A simple [Fragment] subclass.
 */
class Profile : Fragment() {
    //RC is request code, which acts as a flag
    private val RC_SELECT_IMGAE = 2
    private lateinit var selectedImageByes: ByteArray

    //This was for the image
    private var pictureJustChanged = false
    //Var can be changed

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        root.apply {
            imageView_profile_picture.setOnClickListener {
                val intent = Intent().apply {
                    type = "image/*"
                    action = Intent.ACTION_GET_CONTENT
                    putExtra(
                        Intent.EXTRA_MIME_TYPES,
                        arrayOf("image/jpeg", "image/png", "image/gif", "image/pdf")
                    )
                }
                startActivityForResult(intent, RC_SELECT_IMGAE)
                //This is the intent to select the profile picture


            }

            btn_save.setOnClickListener {
                //We ll check if there is already an image initialized
                //We do this by getting the property member from this class
                if (::selectedImageByes.isInitialized) {
                    //We ll update the photo to cloud storage
                    //We ll upload to storage util
                    StorageUtil.uploadProfilePicture(selectedImageByes) { imagePath ->
                        FirestoreUtil.updateUser(
                            editText_name.text.toString(),
                            editText_bio.text.toString(),
                            imagePath
                        )
                    }

                }
                //When it is not initialized
                else {
                    FirestoreUtil.updateUser(
                        editText_name.text.toString(),
                        editText_bio.text.toString(),
                        null
                    )

                }
            }

            //SignOut Button
            btn_sign_out.setOnClickListener {
                AuthUI.getInstance().signOut(this@Profile.context!!).addOnCompleteListener {
                    val i = Intent(context, SignIn_act::class.java)
                    startActivity(i)
                }


            }
        }



        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SELECT_IMGAE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            //If successful, we get a path of the image from our device
            val selectedImagePath = data.data
            val selectedImageBitmap =
                MediaStore.Images.Media.getBitmap(activity?.contentResolver, selectedImagePath)
            //Now apply a jpeg compression on the stream
            val outputStream = ByteArrayOutputStream()
            selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

            //Now fill the byte array
            selectedImageByes = outputStream.toByteArray()
            //Load picture in the holder now and save ot online
            GlideApp.with(this).load(selectedImageByes).into(imageView_profile_picture)
            //This was because it was already in memory with this


            pictureJustChanged = true

        }

    }

    override fun onStart() {
        super.onStart()
        //Load the current user from the firestore
        FirestoreUtil.getCurrentUser { user ->
            //These brackets take time to load dat from the server
            if (this@Profile.isVisible) {
                editText_name.setText(user.name)

                editText_bio.setText(user.bio)
                if (!pictureJustChanged && user.profilePicture != null) {

                    GlideApp.with(this).load(StorageUtil.pathToRef(user.profilePicture))
                        .placeholder(R.drawable.ic_home_black_24dp).into(imageView_profile_picture)

                }
            }
        }
    }


}
