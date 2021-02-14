package com.gehad.chat.onlineDataBase

import com.gehad.chat.onlineDataBase.model.Message
import com.gehad.chat.onlineDataBase.model.Room
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class MessagesDao {

    companion object{

        fun addMessage(message:Message ,onCompleteListener: OnCompleteListener<Void>){
            val ref = DataBase.getMessages()
                .document(message.roomId)
            val roomMessage = ref.collection(message.roomName)
            val newMessageDoc = roomMessage.document()
            message.id = newMessageDoc.id
            newMessageDoc.set(message)
                .addOnCompleteListener(onCompleteListener)

        }

        fun listenForRoom(currentRoom: Room? , listener:EventListener<QuerySnapshot>):ListenerRegistration {
           return   DataBase.getMessages()
                .document(currentRoom?.id?:"")
                .collection(currentRoom?.name?:"")
                .orderBy("date",Query.Direction.ASCENDING)
                .limitToLast(100)
                .addSnapshotListener(listener)
        }
    }
}