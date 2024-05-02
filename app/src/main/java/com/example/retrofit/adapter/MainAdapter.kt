package com.example.retrofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.retrofit.databinding.RvMainBinding
import com.example.retrofit.model.User

class MainAdapter:Adapter<MainAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding: RvMainBinding) : ViewHolder(binding.root) {

    }
    val diffCallback=object :DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem==newItem
        }

    }
    private val differ=AsyncListDiffer(this,diffCallback)
    var users:List<User>
        get()=differ.currentList
        set(value) {differ.submitList(value)}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RvMainBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            val user=users[position]
            tvBody.text=user.body
            tvId.text=user.userId.toString()
            tvId.text=user.id.toString()
            tvTitle.text=user.title
        }
    }
}