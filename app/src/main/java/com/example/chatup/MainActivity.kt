package com.example.chatup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Parameters

        nav_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_all_people -> {
                    //TODO: Show all ppl fragment
                    true

                }
                R.id.navigation_dashboard -> {
                    //TODO: Show all dashboard fragment
                    true

                }
                else -> false


            }
        }

    }
}
