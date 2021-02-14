package com.gehad.chat.onlineDataBase.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class Message (){
    var id=""
    var content=""
    var senderName=""
    var senderId=""
    var roomName=""
    var roomId=""

    //to sort massage by data in fireStore
    @ServerTimestamp
    var date: Date?=null

}