package com.example.chatup.ui

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.chatup.MainActivity
import com.example.chatup.R
import com.example.chatup.ui.auth.SignIn_act
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.intentFor

class splash : AppCompatActivity() {

    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler = Handler()
        handler.postDelayed({

            //We ll write some lines of code in this to see weather the user is logged in or not


            if (FirebaseAuth.getInstance().currentUser == null) {
                //When we are not signed in
                startActivity(intentFor<SignIn_act>())
            } else {
                startActivity(intentFor<MainActivity>())
            }

            finish()

        }, 1000)     //1 seconds delay

        // finish()
        //To finish the splash screen
    }
}
