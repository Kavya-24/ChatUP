package com.example.chatup.Utils

import android.content.Context
import android.util.Log
import com.example.chatup.model.User
import com.example.chatup.recyclerView_item.PersonItemUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.kotlinandroidextensions.Item

object FirestoreUtil {
    //Lazy Initialization :lazy() is a function that takes a lambda and returns an instance of Lazy<T>
    // which can serve as a delegate for implementing a lazy property: the first call to get() executes
    // the lambda passed to lazy() and remembers the result, subsequent calls to get() simply return the remembered result.

    //This object will only have one instance
    //Create instance that will be lazil initialized
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    //What this does is sets the value of FirebaseFirestore only when we need it


    //This comprises of docs and collections. data>docs>collection Each user has its own document
    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document(
            "users/${FirebaseAuth.getInstance().currentUser?.uid ?: throw NullPointerException(
                "UID Not found"
            )}"
        )
    //Get the instance in the document of firebase and save information. A separate document for each user to be initialized

    //When first time. onComplete has no params Unit is void in Java
    fun initCurrentUserIfFirstTime(onComplete: () -> Unit) {
        //We want to get the current user document refernece
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                //If reference does not exists create a new user
                val newUser =
                    User(FirebaseAuth.getInstance().currentUser?.displayName ?: "", "", null)
                //Contain the document in firestore
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            } else {
                onComplete()
            }
        }
    }

    //Write other functions as well
    fun updateUser(name: String = "", bio: String = "", profilePicture: String? = "") {
        //Initializing these values to blank at first
        val userFieldMap = mutableMapOf<String, Any>()

        //Field map is to send multiple params together
        //We can update any number of strings in this
        //We ll check what values of the user are already there
        if (name.isNotBlank()) userFieldMap["name"] = name
        if (bio.isNotBlank()) userFieldMap["bio"] = bio
        if (profilePicture != null) userFieldMap["profilePicture"] = profilePicture
        currentUserDocRef.update(userFieldMap)

    }

    fun getCurrentUser(onComplete: (User) -> Unit) {
        currentUserDocRef.get().addOnSuccessListener {
            it.toObject(User::class.java)?.let { it1 -> onComplete(it1) }
        }
    }

    //A fucntion to update the list of users
    fun addUsersListener(context: Context, onListen: (List<Item>) -> Unit): ListenerRegistration {

        return firestoreInstance.collection("users")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("Firestore", "User Listener Error", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                //Or else, create a mutable list of the users
                val items = mutableListOf<Item>()
                //Get the documents for the particu;ar user
                querySnapshot?.documents?.forEach {
                    //We come to the currently signed user
                    //'it' is the doc snapshot, we ll filter the user that we want
                    if (it.id != FirebaseAuth.getInstance().currentUser?.uid) {
                        // This is not the person who is signed in, we show it in the list
                        it.toObject(User::class.java)
                            ?.let { it1 -> PersonItemUser(it1, it.id, context) }?.let { it2 ->
                            items.add(it2)
                        }

                    }
                }
                onListen(items)

            }
    }

    fun removeListener(registration: ListenerRegistration) = registration.remove()

}