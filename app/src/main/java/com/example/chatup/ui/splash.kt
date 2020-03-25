package com.example.chatup.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatup.MainActivity
import com.example.chatup.ui.auth.SignIn_act
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.intentFor

class splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //We ll write some lines of code in this to see weather the user is logged in or not
        if(FirebaseAuth.getInstance().currentUser == null){
            //When we are not signed in
            startActivity(intentFor<SignIn_act>())
        }
        else{
            startActivity(intentFor<MainActivity>())
        }
            finish()
        //To finish the splash screen
    }
}
