package com.example.chatup.model

import java.util.*

class ImageMessage(
    val imagePath: String,
    override val time: Date,
    override val senderId: String,
    override val type: String = MessageType.IMG
) : Message {
    //Now create an interface to link these
    //Initialize an empty constructor for this
    constructor() : this("", Date(0), "")

    //Constructor value:


}