package com.hanstcs.githubfinder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hanstcs.githubfinder.databinding.ItemUserBinding
import com.hanstcs.githubfinder.model.UserModel

class UserAdapter(private val list: List<UserModel>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private lateinit var binding: ItemUserBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        if (list.isNullOrEmpty())
            return

        binding.data = list[position]

        Glide.with(holder.binding.root.context)
            .load(list[position].avatarUrl)
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(binding.ivAvatar)
    }

    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}