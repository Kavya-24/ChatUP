package com.example.chatup.recyclerView_item

import android.content.Context
import android.view.textclassifier.ConversationActions
import com.example.chatup.R
import com.example.chatup.model.TextMessage
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

class TextmsgItem (val message: TextMessage,val context : Context) : Item(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getLayout() = R.layout.list_text_message


}