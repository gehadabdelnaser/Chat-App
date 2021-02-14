package com.gehad.chat.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.gehad.base.BaseViewModel
import com.gehad.chat.onlineDataBase.DataHolder
import com.gehad.chat.onlineDataBase.UserDao
import com.gehad.chat.onlineDataBase.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel :BaseViewModel<Navigator>() {

    val email=ObservableField<String>()
    val password=MutableLiveData<String>()
    val emailError=ObservableField<Boolean>(false)
    val passwordError=ObservableField<Boolean>(false)

    val authUser=MutableLiveData<FirebaseUser>()
    private var auth:FirebaseAuth

    init {
        auth= FirebaseAuth.getInstance()
        // make auto login
        if(auth.currentUser != null){
          //  authUser.value=auth.currentUser
            UserDao.getUser(auth.currentUser?.uid?:"",
                OnCompleteListener{
                    if (it.isSuccessful) {
                        authUser.value = auth.currentUser
                        val dataBaseUser = it.result?.toObject(User::class.java)
                        DataHolder.dataBaseUser = dataBaseUser
                        DataHolder.authUser = auth.currentUser

                    }else{
                        showMessage.value= it.exception?.localizedMessage
                    }
                })
        }
    }

    fun login(){
        if(isValidData()){
            showLoading.value=true
            //sign in user
            auth.signInWithEmailAndPassword(email.get()?:"",password.value?:"")
                .addOnCompleteListener{
                    showLoading.value=false
                    if (it.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        UserDao.getUser(auth.currentUser?.uid?:"",
                        OnCompleteListener{
                            if (it.isSuccessful) {
                                val dataBaseUser = it.result?.toObject(User::class.java)
                                DataHolder.dataBaseUser = dataBaseUser
                                DataHolder.authUser = auth.currentUser
                                authUser.value = auth.currentUser
                            }else{
                                showMessage.value= it.exception?.localizedMessage
                            }
                        })
                    } else {
                        // If sign in fails, display a message to the user.
                        showMessage.value= it.exception?.localizedMessage
                    }
                }
        }
    }

    fun isValidData():Boolean{
        var isValid=true
        if(email.get().isNullOrBlank()) {
            //show error
            emailError.set(true)
            isValid=false
        } else{
            emailError.set(false)
        }
        if(password.value.isNullOrEmpty()) {
            //show error
            passwordError.set(true)
            isValid=false
        }else{
            passwordError.set(false)
        }
        return isValid
    }

    fun register(){
        navigator?.openRegister()
        
    }
}