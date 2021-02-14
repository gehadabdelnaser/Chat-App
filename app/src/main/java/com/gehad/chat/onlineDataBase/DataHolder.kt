package com.gehad.chat.onlineDataBase

import com.gehad.chat.onlineDataBase.model.User
import com.google.firebase.auth.FirebaseUser

class DataHolder {
    companion object{
        var dataBaseUser:User?=null
        var authUser:FirebaseUser?=null
    }
}