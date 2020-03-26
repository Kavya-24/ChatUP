package com.example.chatup.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.chatup.MainActivity
import com.example.chatup.R
import com.example.chatup.Utils.FirestoreUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import org.jetbrains.anko.*

class SignIn_act : AppCompatActivity() {


    private val RC_code = 1

    //private val signInProviders= listOf(Auth)
    //email authentication
    private val signInProviders =
        listOf(
            AuthUI.IdpConfig.EmailBuilder()
                .setAllowNewAccounts(true)
                .setRequireName(true)
                .build()
        )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_act)

        val b: Button = findViewById(R.id.account_sign_in)
        b.setOnClickListener {

            val i = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(signInProviders).build()

            startActivityForResult(i, RC_code)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_code) {
            //When the access code is correct
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                //Get a toast that the account will be set.
                //Toast.makeText(this, "Setting up your account", Toast.LENGTH_SHORT).show()
                //Initializ current user in firebase
                val progressDialog = indeterminateProgressDialog("Setting up your account")
                //Start activity to the main activity now.
                //intentFor is of Anko
                //NewTask(), clearTask() : Flags so that we can not go back to signUp Screen
                FirestoreUtil.initCurrentUserIfFirstTime {
                    startActivity(intentFor<MainActivity>().newTask().clearTask())
                    //TODO : What will happen when this is clicked
                    progressDialog.dismiss()
                    //This is going to dismiss it for the next time

                }
                //

                //If the details are correct and we ask the user to save the information in thje dialog box
//                val progressDialog = AlertDialog.Builder(context)
//                progressDialog.setTitle("Save result?")
//                progressDialog.setMessage("Do you want to save this information?")
//                progressDialog.setPositiveButton("Yes", { dialogInterface: DialogInterface, i: Int ->
//
//                })
//
//                progressDialog.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int -> })
//
//                progressDialog.show()
                //Now start the function
                //NewTask and ClearTask()

//            findNavController().navigate(R.id.action_signIn_to_navigation_home)
                //   startActivity(intentFor<MainActivity>(),)


            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (response == null) {
                    //When there is nothing, return else display the error when the activity is cancelled
                    return
                }

                // "?." is the safe access operator. If the value is null, it ignores everything after it
                when (response.error?.errorCode) {
                    ErrorCodes.NO_NETWORK -> longToast("No network connection, please try again later")
                    ErrorCodes.DEVELOPER_ERROR -> longToast("Developer Error")
                    ErrorCodes.UNKNOWN_ERROR -> longToast("Unknown error occurred")
                    //Check anko dependencies for the below
                    // ErrorCodes.PROVIDER_ERROR -> longSnackbar(layout_signIn,"Provider Error")
                    //Layout_signIn is the id of the constraint
                }
            }


        }

    }

//Now we ll create a splash screen without any layouts initially for decision making
}