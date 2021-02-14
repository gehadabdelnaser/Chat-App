package com.gehad.chat.ui.addRoom

import android.content.DialogInterface
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gehad.base.BaseActivity
import com.gehad.chat.R
import com.gehad.chat.databinding.ActivityAddRoomBinding

class AddRoomActivity :BaseActivity<ActivityAddRoomBinding,AddRoomViewModel>()
,Navigator{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel
        viewModel.navigator = this
        subscribeToLiveData()
    }

    fun subscribeToLiveData(){
        viewModel.roomAdded.observe(this, Observer {
            if (it){
                showMessage(null,R.string.room_added_succfully,R.string.ok,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                    finish() }
                    ,null,null,false)
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_add_room
    }

    override fun generateViewModel(): AddRoomViewModel {
        return ViewModelProvider(this).get(AddRoomViewModel::class.java)
    }
}