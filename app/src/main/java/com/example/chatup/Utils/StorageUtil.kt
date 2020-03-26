package com.example.chatup.Utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

object StorageUtil {
    private val storageInstance: FirebaseStorage by lazy { FirebaseStorage.getInstance() }

    private val cureentUserRef: StorageReference
        //read only property
        get() = storageInstance.reference.child(
            FirebaseAuth.getInstance().uid ?: throw  NullPointerException("UId is null")
        )

    //We ll create a fucntion uploadProfilePicture , takes inputof image array and higher order function onSuccess

    fun uploadProfilePicture(imageByte: ByteArray, onSuccess: (imagePath: String) -> Unit) {
        //Unit is returning void
        //Now we ll create a location (called reference) to store the image bytes
        val ref = cureentUserRef.child("profilePictures/${UUID.nameUUIDFromBytes(imageByte)}")
        //We always need to create a ew file so as to avoid image caching. We craete unique names in the UUID class by the bytes everytime
        ref.putBytes(imageByte).addOnSuccessListener {
            onSuccess(ref.path)
            //This path will be stored on the firestore
        }

    }

    //We return this funtion with the equlas
    fun pathToRef(path: String) = storageInstance.getReference(path)

}
