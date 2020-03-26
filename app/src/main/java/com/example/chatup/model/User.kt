package com.example.chatup.model

data class User(val name: String, val bio: String, val profilePicture: String?) {
    //This is the users' class and hpw the data is retrieved.
    //The profile picture is not set by default, will be set

    //We ll create a primary constructor to argue with initial values
    //This constructor is because of Firestore
    constructor() : this("", "", null)


}