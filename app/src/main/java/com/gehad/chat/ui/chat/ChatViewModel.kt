  package com.gehad.chat.ui.chat

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.gehad.base.BaseViewModel
import com.gehad.chat.onlineDataBase.DataBase
import com.gehad.chat.onlineDataBase.DataHolder
import com.gehad.chat.onlineDataBase.MessagesDao
import com.gehad.chat.onlineDataBase.model.Message
import com.gehad.chat.onlineDataBase.model.Room
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.util.*

class ChatViewModel:BaseViewModel<Navigator>() {

    var currentRoom:Room?=null
    val messageText=ObservableField<String>("")

    fun sendMessage(){
        if (messageText.get().isNullOrBlank()){
            return
        }
        val messageToSend=Message()
        messageToSend.content=messageText.get()?:""
        messageToSend.senderId=DataHolder.dataBaseUser?.id?:""
        messageToSend.senderName=DataHolder.dataBaseUser?.name?:""
        messageToSend.roomId=currentRoom?.id?:""
        messageToSend.roomName=currentRoom?.name?:""
        messageToSend.date= Date()

        MessagesDao.addMessage(messageToSend, OnCompleteListener {
            if (it.isSuccessful){
                messageText.set("")
            }else{
                showMessage.value=it.exception?.localizedMessage
            }
        })
    }

    val messages= mutableListOf<Message>()
    val messageMutableLiveData = MutableLiveData<List<Message>>()
    // get new messages from query
    val roomListener= object :EventListener<QuerySnapshot>{
        override fun onEvent(query: QuerySnapshot?, e: FirebaseFirestoreException?) {
            if(e !=null){
                showMessage.value = e.localizedMessage
                return
            }
            for(doc in query!!.documentChanges){
                when(doc.type){
                    DocumentChange.Type.ADDED->{
                        val message =doc.document.toObject(Message::class.java)
                        messages.add(message)
                    }
                }
            }
            messageMutableLiveData.value = messages
        }

    }
    var listenerRegistration:ListenerRegistration?=null
    fun startRealTimeUpdate(){
        listenerRegistration= MessagesDao.listenForRoom(currentRoom,roomListener)

    }

    //to stop of listener
    override fun onCleared() {
        super.onCleared()
        listenerRegistration?.remove()
    }

}