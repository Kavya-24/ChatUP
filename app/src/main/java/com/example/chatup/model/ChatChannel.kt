package com.example.chatup.model

data class ChatChannel(val userIds : MutableList<String>) {

//Primary constructor
    constructor() : this(mutableListOf())
    //This is an empty mutable list

}



