package com.gehad.chat.onlineDataBase

import com.gehad.chat.onlineDataBase.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.DocumentSnapshot

class UserDao {
    companion object{
        fun addUser(user:User , onCompleteListener: OnCompleteListener<Void>){

            //use OnCompleteListener to know the document insertion or not
            //make new document by the same id of authentication user
            val userDoc = DataBase.getUsers().document(user.id?:"")
            userDoc.set(user)
                .addOnCompleteListener(onCompleteListener)
        }

        fun getUser(userId:String ,
                    onCompleteListener: OnCompleteListener<DocumentSnapshot>){

            DataBase.getUsers()
                .document(userId)
                .get()
                .addOnCompleteListener(onCompleteListener)
        }
    }
}