package com.example.chatup

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.chatup.Fragment.Profile
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
                    // replaceFragment()
                    true

                }
                R.id.navigation_dashboard -> {
                    //We ll create a replace fragment function or simply navigate
                    replaceFragment(Profile())
//                    val i = Intent(applicationContext, Profile::class.java)
//                    startActivity(i)
                    true

                }
                else -> false


            }
        }

    }

    @SuppressLint("CommitTransaction")
    private fun replaceFragment(fragment: Fragment) {
        //Replace the fragments
        supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.fragment_layout,
                fragment
            )       //This transaction should be completed with a commit call
            commit()
        }
//
//    }

    }
}