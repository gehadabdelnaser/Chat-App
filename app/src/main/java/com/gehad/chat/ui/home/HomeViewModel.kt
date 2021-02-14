package com.gehad.chat.ui.home

import androidx.lifecycle.MutableLiveData
import com.gehad.base.BaseViewModel
import com.gehad.chat.onlineDataBase.DataHolder
import com.gehad.chat.onlineDataBase.RoomsDao
import com.gehad.chat.onlineDataBase.model.Room
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel :BaseViewModel<Navigator>(){

    var roomData=MutableLiveData<List<Room>>()
    init {
        getRooms()
    }

    fun getRooms(){
        RoomsDao.getRooms(OnCompleteListener{
            val documents = it.result?.documents
            val roomsList = mutableListOf<Room>()
            for(document in documents!!){
                val room = document.toObject(Room::class.java)
                if(room==null)continue
                roomsList.add(room)
            }

            roomData.value=roomsList
        })
    }

    fun logout(){
        FirebaseAuth.getInstance().signOut()
        DataHolder.authUser=null
        DataHolder.dataBaseUser=null
        navigator?.logout()
    }
}