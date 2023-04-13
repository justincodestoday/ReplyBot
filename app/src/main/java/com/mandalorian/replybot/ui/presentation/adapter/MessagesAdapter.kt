package com.mandalorian.replybot.ui.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mandalorian.replybot.databinding.ItemLayoutMessageBinding
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.utils.Utils.update

class MessagesAdapter(
    private var messages: List<Message>,
    val onClick: (message: Message) -> Unit
) :
    RecyclerView.Adapter<MessagesAdapter.ItemMessageHolder>() {

    class ItemMessageHolder(val binding: ItemLayoutMessageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMessageHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutMessageBinding.inflate(layoutInflater, parent, false)
        return ItemMessageHolder(binding)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: ItemMessageHolder, position: Int) {
        val message = messages[position]
        holder.binding.run {
            tvTitle.text = message.title
            tvReceive.text = message.sendMsg
            tvReply.text = message.replyMsg

            cvMessage.setOnClickListener {
                onClick(message)
            }
        }
    }

    fun setMessage(messages: List<Message>) {
        val oldProducts = this.messages
        this.messages = messages
//        notifyDataSetChanged()
        if (oldProducts.isEmpty()) {
            update(emptyList(), messages) { task, task2 ->
                task.id == task2.id
            }
        } else {
            update(oldProducts, messages) { task, task2 ->
                task.id == task2.id
            }
        }
    }
}