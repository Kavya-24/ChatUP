package com.example.chatup.recyclerView_item

import android.view.Gravity
import android.widget.FrameLayout
import com.example.chatup.R
import com.example.chatup.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.list_text_message.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.wrapContent
import java.text.SimpleDateFormat

abstract class MessageItem(private val message: Message) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        setTimeMsg(viewHolder)
        setRelRootAl(viewHolder)

    }


    //Use this as the intermediate class
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
}