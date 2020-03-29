package com.example.chatup.recyclerView_item

import android.content.Context
import com.example.chatup.R
import com.example.chatup.model.TextMessage
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.list_text_message.*

class TextmsgItem(val message: TextMessage, val context: Context) : MessageItem(message) {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView_message_text.text = message.text
        super.bind(viewHolder, position)


//        setTimeMsg(viewHolder)
//        setRelRootAl(viewHolder)
        //viewHolder.textView_message_time.text = message.
        //function for getting dtae and time
    }


    override fun getLayout() = R.layout.list_text_message

    //To not dapat over and over again the old messages
    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        //return super.isSameAs(other)
        if (other !is TextmsgItem)
            return false
        if (this.message != other.message) {
            return false
        }
        return true
    }

    override fun equals(other: Any?): Boolean {
        //return super.equals(other)
        return isSameAs(other as? TextmsgItem)
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + context.hashCode()
        return result
    }

}


