package com.gehad.chat.ui.register

import com.gehad.base.BaseViewModel
import androidx.databinding.ObservableField
import com.gehad.chat.onlineDataBase.DataHolder
import com.gehad.chat.onlineDataBase.UserDao
import com.gehad.chat.onlineDataBase.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


class RegisterViewModel :BaseViewModel<Navigator>(){

    val name=ObservableField<String>()
    val email=ObservableField<String>()
    val password=ObservableField<String>()
    val phone=ObservableField<String>()
    val emailError=ObservableField<Boolean>(false)
    val nameError=ObservableField<Boolean>(false)
    val phoneError=ObservableField<Boolean>(false)
    val passwordError=ObservableField<Boolean>(false)

    private var auth:FirebaseAuth

    init {
        auth= FirebaseAuth.getInstance()

    }
    fun register(){
        if(isValidData()) {
            //register user in firebase
            showLoading.value = true
            auth.createUserWithEmailAndPassword(email.get() ?: "", password.get() ?: "")
                .addOnCompleteListener {
                    showLoading.value = false
                    if (it.isSuccessful) {
                        val newUser = User()
                        newUser.id = it.result?.user?.uid ?: ""
                        newUser.name = name.get() ?: ""
                        newUser.email = email.get() ?: ""
                        newUser.phone = phone.get() ?: ""

                        UserDao.addUser(newUser, OnCompleteListener {
                            if (it.isSuccessful) {
                                DataHolder.dataBaseUser = newUser
                                DataHolder.authUser = auth.currentUser
                                navigator?.openHomeActivity()
                            } else {
                                showMessage.value = "failed to register user...tey again later" +
                                        it.exception?.localizedMessage
                            }
                        })
                    } else {
                        showMessage.value = it.exception?.localizedMessage
                    }
                }
        }
    }
    fun isValidData():Boolean{
        var isValid=true
        if(name.get().isNullOrEmpty()) {
            //show error
            nameError.set(true)
            isValid=false
        } else{
            nameError.set(false)
        }
        if(phone.get().isNullOrEmpty()) {
            //show error
            phoneError.set(true)
            isValid=false
        } else{
            phoneError.set(false)
        }
        if(email.get().isNullOrEmpty()) {
            //show error
            emailError.set(true)
            isValid=false
        } else{
            emailError.set(false)
        }
        if(password.get().isNullOrEmpty() || password.get()?.length?:0<6) {
            //show error
            passwordError.set(true)
            isValid=false
        }else{
            passwordError.set(false)
        }
        return isValid
    }
}