package com.example.chatup.Fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chatup.AppCOnstants
import com.example.chatup.R
import com.example.chatup.Utils.FirestoreUtil
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.kotlinandroidextensions.Item
import org.jetbrains.anko.toast

class Chat : AppCompatActivity() {


    private lateinit var messagesListenerRegistration: ListenerRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        //To get the arrow to find its way back to main activity
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("user_name")


        val otherUsrId = intent.getStringExtra("userid")

        if(otherUsrId != null){
        FirestoreUtil.getChatChannel(otherUsrId) { channelId ->
            //This loads all the functions
            messagesListenerRegistration =
                FirestoreUtil.addChatMessagesListener(channelId, this, this::onMessagesChange)
        }}

//        else {
//            otherUsrId= 1
//        }
    }

    private fun onMessagesChange(message : List<Item>) {
        //message item in this
        toast("Loading Chat")

    }

}
