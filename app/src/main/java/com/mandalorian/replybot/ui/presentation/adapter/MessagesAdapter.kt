package com.mandalorian.replybot.ui.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mandalorian.replybot.databinding.ItemLayoutMessageBinding
import com.mandalorian.replybot.model.Message

class MessagesAdapter(private var items: MutableList<Message>) :
    RecyclerView.Adapter<MessagesAdapter.ItemMessageHolder>() {

    class ItemMessageHolder(val binding: ItemLayoutMessageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMessageHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutMessageBinding.inflate(layoutInflater, parent, false)
        return ItemMessageHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemMessageHolder, position: Int) {
        TODO("Not yet implemented")
    }
}