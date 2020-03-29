package com.example.chatup.recyclerView_item

import android.content.Context
import com.example.chatup.R
import com.example.chatup.Utils.StorageUtil
import com.example.chatup.glide_module.GlideApp
import com.example.chatup.model.ImageMessage
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.list_img_message.*

class ImageMsgItem(val img: ImageMessage, val context: Context) : MessageItem(img) {
    override fun bind(viewHolder: ViewHolder, position: Int) {
//        viewHolder.textView_message_text.text = message.text
        super.bind(viewHolder, position)
//        viewHolder.imageview_message_image.image = img.imagePath
        GlideApp.with(context).load(StorageUtil.pathToRef(img.imagePath))
            .placeholder(R.drawable.smile).into(viewHolder.imageview_message_image)

    }

    override fun getLayout() = R.layout.list_img_message

    //To not dapat over and over again the old messages
    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        //return super.isSameAs(other)
        if (other !is ImageMsgItem)
            return false
        if (this.img != other.img)
            return false
        return true
    }

    override fun equals(other: Any?): Boolean {
        //return super.equals(other)
        return isSameAs(other as? ImageMsgItem)
    }

    override fun hashCode(): Int {
        var result = img.hashCode()
        result = 31 * result + context.hashCode()
        return result
    }

}


