package com.example.chatup.recyclerView_item

import android.content.Context
import com.example.chatup.R
import com.example.chatup.Utils.StorageUtil
import com.example.chatup.glide_module.GlideApp
import com.example.chatup.model.User
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.list_person_layout.*

//Use groupie extensions of Item
class PersonItemUser(val person: User, val userId: String, private val context: Context) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        //We bind the var person to the layout
        //We are able to write these with android experimental=true
        viewHolder.textView_name.text = person.name

        viewHolder.textView_bio.text = person.bio
        if (person.profilePicture != null) {
            GlideApp.with(context).load(StorageUtil.pathToRef(person.profilePicture))
                .placeholder(R.drawable.smile).into(viewHolder.imageView_profile_picture)
        }
    }

    //Attaches the layout, which is to be rendered
    override fun getLayout() = R.layout.list_person_layout
    //This communicates with the cardview layput of the list of users
    //This class keeps track of the user, and loads the data in the adapter


}