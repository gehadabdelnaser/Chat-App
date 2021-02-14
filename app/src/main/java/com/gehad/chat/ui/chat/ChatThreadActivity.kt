package com.gehad.chat.ui.chat

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gehad.base.BaseActivity
import com.gehad.chat.Constant
import com.gehad.chat.R
import com.gehad.chat.databinding.ActivityChatThreadBinding
import com.gehad.chat.onlineDataBase.model.Room

class ChatThreadActivity : BaseActivity<ActivityChatThreadBinding,ChatViewModel>(), Navigator{

    val adapter=ChatAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.currentRoom = intent.getSerializableExtra(Constant.EXTRA_ROOM) as Room
        viewModel.navigator=this
        viewDataBinding.vm=viewModel

        viewModel.startRealTimeUpdate()
        viewDataBinding.messagesRecyclerView.adapter = adapter
        (viewDataBinding.messagesRecyclerView.layoutManager as LinearLayoutManager).stackFromEnd = true
        viewModel.messageMutableLiveData.observe(this, Observer {
            adapter.changeData(it)
        })


    }

    override fun getLayoutId(): Int {
        return R.layout.activity_chat_thread
    }

    override fun generateViewModel(): ChatViewModel {
        return ViewModelProvider(this).get(ChatViewModel::class.java)
    }
}