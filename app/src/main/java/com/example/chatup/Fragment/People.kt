package com.example.chatup.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatup.AppCOnstants
import com.example.chatup.R
import com.example.chatup.Utils.FirestoreUtil
import com.example.chatup.recyclerView_item.PersonItemUser
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.fragment_people.*

/**
 * A simple [Fragment] subclass.
 */
class People : Fragment() {

    //We ll create a listener such that the user list is automatically always updtaed
    //We add listeners on this instead of getters
    private lateinit var userListener: ListenerRegistration
    private var initRv = true

    //Craete section on Groupie
    private lateinit var peopleSection: Section


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_people, container, false)
        val r = inflater.inflate(R.layout.fragment_people, container, false)

        userListener = FirestoreUtil.addUsersListener(this.activity!!, this::updateRvItems)



        return r
    }

    override fun onDestroyView() {
        super.onDestroyView()
        FirestoreUtil.removeListener(userListener)
        //After th view is destroyed, we need to work on fetching RV again
        initRv = true
    }

    private fun updateRvItems(items: List<Item>) {
        //this is for initial time
        fun init() {
            rv_people_list.apply {
                layoutManager = LinearLayoutManager(this@People.context)
                adapter = GroupAdapter<ViewHolder>().apply {
                    peopleSection = Section(items)
                    //Attach it to the adapter
                    add(peopleSection)
                    setOnItemClickListener(onItemClick)
                }
            }
            initRv = false

        }

        //This is for updtae
        fun update() = peopleSection.update(items)

        if (initRv) {
            //We need to initialize
            init()
        } else {
            update()
        }

    }


    private val onItemClick = OnItemClickListener { item, view ->
        if (item is PersonItemUser) {
//            startActivity<Chat>(
//                AppCOnstants.USER_NAME to item.person.name,
//                AppCOnstants.USER_ID to item.userId
//            )
            val i = Intent(context, Chat::class.java)
            i.putExtra(AppCOnstants.USER_NAME,AppCOnstants.USER_ID)

        }
    }


}
