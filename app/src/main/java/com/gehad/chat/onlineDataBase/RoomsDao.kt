package com.gehad.chat.onlineDataBase

import com.gehad.chat.onlineDataBase.model.Room
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.QuerySnapshot

class RoomsDao {
    companion object{

        fun addRoom(room:Room,onCompleteListener: OnCompleteListener<Void>){
            //make new document by create new id
            val roomDoc=DataBase.getRooms()
                .document()
            // id of new room get the same id of new document
            room.id=roomDoc.id
            roomDoc.set(room)
                .addOnCompleteListener(onCompleteListener)
        }

        fun getRooms(onCompleteListener: OnCompleteListener<QuerySnapshot>){
            DataBase.getRooms()
                .get() // get all collections of room
                .addOnCompleteListener(onCompleteListener)
        }
    }
}