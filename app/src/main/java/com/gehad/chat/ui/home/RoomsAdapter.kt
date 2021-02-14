package com.gehad.chat.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gehad.chat.R
import com.gehad.chat.databinding.ItemRoomBinding
import com.gehad.chat.onlineDataBase.model.Room

class RoomsAdapter():RecyclerView.Adapter<RoomsAdapter.ViewHolder>() {

    var rooms=listOf<Room>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DataBindingUtil
            .inflate<ItemRoomBinding>(LayoutInflater.from(parent.context),
            R.layout.item_room,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rooms.get(position))
        if(onItemClickListener != null){
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(position,rooms.get(position))
            }
        }
    }

    fun changeData(newRooms:List<Room>){
        rooms=newRooms
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int =rooms.size

    var onItemClickListener:OnItemClickListener?=null
    interface OnItemClickListener{
        fun onItemClick(position: Int,item: Room)
    }


    class ViewHolder(val binding:ItemRoomBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item: Room){
            binding.item=item
            binding.executePendingBindings()
        }
    }
}