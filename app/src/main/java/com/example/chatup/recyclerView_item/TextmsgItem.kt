package com.example.chatup.recyclerView_item

import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import com.example.chatup.R
import com.example.chatup.model.TextMessage
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.list_text_message.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.wrapContent
import java.text.SimpleDateFormat

class TextmsgItem(val message: TextMessage, val context: Context) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView_message_text.text = message.text
        setTimeMsg(viewHolder)
        setRelRootAl(viewHolder)
        //viewHolder.textView_message_time.text = message.
        //function for getting dtae and time
    }

    private fun setTimeMsg(viewHolder: ViewHolder) {
        val datFormat =
            SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT)
        viewHolder.textView_message_time.text = datFormat.format(message.time)
    }

    private fun setRelRootAl(viewHolder: ViewHolder) {
        if (message.senderId == FirebaseAuth.getInstance().currentUser?.uid) {
            viewHolder.message_root.apply {
                //When we send the message it shoulb=d be white/ or rect white drawble
                backgroundResource = R.drawable.rect_round_primary_color

                val RightAl = FrameLayout.LayoutParams(wrapContent, wrapContent, Gravity.END)

                this.layoutParams = RightAl
                //layoutParams(RightAl)
            }
        } else {
            viewHolder.message_root.apply {
                backgroundResource = R.drawable.rect_round_white
                //Now to align it to right for us
                val leftAl = FrameLayout.LayoutParams(wrapContent, wrapContent, Gravity.START)
                this.layoutParams = leftAl

                //h,w,grav params
            }

        }
    }

    override fun getLayout() = R.layout.list_text_message

    //To not dapat over and over again the old messages
    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        //return super.isSameAs(other)
        if (other !is TextmsgItem)
            return false
        if (this.message != other.message)
            return false
        return true
    }

    override fun equals(other: Any?): Boolean {
        //return super.equals(other)
        return isSameAs(other as? TextmsgItem)
    }

}


