package com.mandalorian.replybot.ui.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mandalorian.replybot.R
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
            tvIncoming.text = message.incomingMsg
            tvOutgoing.text = message.replyMsg

            if (!message.isActivated) {
                status.setColorFilter(ContextCompat.getColor(root.context, R.color.yellow_700))
            }

            cvMessage.setOnClickListener {
                onClick(message)
            }
        }
    }

    fun setMessage(messages: List<Message>) {
        val oldProducts = this.messages
        this.messages = messages
        update(oldProducts, messages) { task, task2 ->
            task.id == task2.id
        }
    }
}