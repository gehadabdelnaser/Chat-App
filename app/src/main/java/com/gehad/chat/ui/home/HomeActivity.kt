package com.gehad.chat.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.gehad.chat.R
import com.gehad.base.BaseActivity
import com.gehad.chat.Constant
import com.gehad.chat.databinding.ActivityHomeBinding
import com.gehad.chat.onlineDataBase.model.Room
import com.gehad.chat.ui.addRoom.AddRoomActivity
import com.gehad.chat.ui.chat.ChatThreadActivity
import com.gehad.chat.ui.login.LoginActivity


class HomeActivity : BaseActivity<ActivityHomeBinding,HomeViewModel>() ,Navigator{

    private val adapter = RoomsAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm=viewModel
        viewModel.navigator=this

        viewDataBinding.fab.setOnClickListener {
            startActivity(Intent(this,AddRoomActivity::class.java))
        }
        initRecyclerView()

    }

    override fun onStart() {
        super.onStart()
        viewModel.getRooms()
    }

    private fun initRecyclerView(){
        viewDataBinding.content.roomsRecyclerview.adapter=adapter
        adapter.onItemClickListener= object :RoomsAdapter.OnItemClickListener{
            override fun onItemClick(position: Int, item: Room) {
                val intent=Intent(activity,ChatThreadActivity::class.java)
                //send object of room
                intent.putExtra(Constant.EXTRA_ROOM,item)
                startActivity(intent)
            }
        }
        viewModel.roomData.observe(this, {
            adapter.changeData(it)
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun generateViewModel(): HomeViewModel {
        return ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun logout() {
        val intent=Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
