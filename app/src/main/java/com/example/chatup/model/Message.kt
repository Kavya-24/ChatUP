package com.example.chatup.model

import java.util.*

object MessageType{
    const val  TXT = "TXT"
    const val  IMG = "IMG"
}

interface Message {

    val time : Date
    val senderId : String
    val type : String
    //Text is differeent for the text msg and image link


}