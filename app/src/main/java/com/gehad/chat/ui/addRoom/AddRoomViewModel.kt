package com.gehad.chat.ui.addRoom

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.gehad.base.BaseViewModel
import com.gehad.chat.onlineDataBase.RoomsDao
import com.gehad.chat.onlineDataBase.model.Room
import com.google.android.gms.tasks.OnCompleteListener

class AddRoomViewModel:BaseViewModel<Navigator>() {

    val name = ObservableField<String>()
    val description = ObservableField<String>()
    val roomNameError = ObservableField<Boolean>(false)
    val roomDescriptionError = ObservableField<Boolean>(false)
    val roomAdded = MutableLiveData<Boolean>()

    fun addRoom() {
        if(validData()){
            showLoading.value=true
            var room=Room()
            room.name=name.get()
            room.description=description.get()

            RoomsDao.addRoom(room, OnCompleteListener {
                showLoading.value=false
                if (it.isSuccessful){
                    roomAdded.value=true
                }else{
                    showMessage.value=it.exception?.localizedMessage
                }
            })
        }
    }

    private fun validData(): Boolean {
        var valid = true
        if (name.get().isNullOrBlank()) {
            valid = false
            roomNameError.set(true)
        } else {
            roomNameError.set(false)
        }
        if (description.get().isNullOrBlank()) {
            valid = false
            roomDescriptionError.set(true)
        } else {
            roomDescriptionError.set(false)
        }
        return valid
    }

}