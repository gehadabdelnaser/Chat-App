package com.gehad.chat.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gehad.chat.R
import com.gehad.chat.databinding.IncomingMessageBinding
import com.gehad.chat.databinding.OutgoingMessageBinding
import com.gehad.chat.onlineDataBase.DataHolder
import com.gehad.chat.onlineDataBase.model.Message

class ChatAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var messages= listOf<Message>()

    val INCOMING_MESSAGE_TYPE=1
    val OUTGOING_MESSAGE_TYPE=2

    override fun getItemViewType(position: Int): Int {
        val massage=messages.get(position)
        if (massage.senderId.equals(DataHolder.dataBaseUser?.id)){
            return OUTGOING_MESSAGE_TYPE
        }
        return INCOMING_MESSAGE_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == INCOMING_MESSAGE_TYPE){
            val view=DataBindingUtil.inflate<IncomingMessageBinding>(
                LayoutInflater.from(parent.context), R.layout.incoming_message,
                parent,false)
            return IncomingMessageViewHolder(view)
        }else{
            val view=DataBindingUtil.inflate<OutgoingMessageBinding>(
                LayoutInflater.from(parent.context),R.layout.outgoing_message,
                parent,false)
            return OutgoingMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType=getItemViewType(position)
        if (viewType==INCOMING_MESSAGE_TYPE){
            holder as IncomingMessageViewHolder
            holder.bind(messages[position])
        }else{
            holder as OutgoingMessageViewHolder
            holder.bind(messages[position])
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun changeData(messages: List<Message>) {
        this.messages=messages
        notifyDataSetChanged()
    }


    class IncomingMessageViewHolder(val binding:IncomingMessageBinding)
        :RecyclerView.ViewHolder(binding.root){

        fun bind(item:Message){
            binding.message=item
            binding.executePendingBindings()
        }
    }

    class OutgoingMessageViewHolder(val binding:OutgoingMessageBinding)
        :RecyclerView.ViewHolder(binding.root){

        fun bind(item:Message){
            binding.message=item
            binding.executePendingBindings()
        }
    }
}