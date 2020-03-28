package com.example.chatup.model

import java.util.*

class TextMessage(val text : String, override val time : Date, override val senderId : String, override val type : String = MessageType.TXT) : Message {
    //Now create an interface to link these
    //Initialize an empty constructor for this
    constructor() : this("",Date(0),"")


}