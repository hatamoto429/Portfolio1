package com.example.portfolio1.viewModel

import android.graphics.Picture
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.portfolio1.databinding.ItemPersonLayoutBinding


class UserListAdapter : ListAdapter<User, UserListAdapter.Holder>(DiffCallback()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val binding = ItemPersonLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return Holder(binding)

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentItem = getItem(position)
        holder.onBind(currentItem)
    }

    inner class Holder(val binding: ItemPersonLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(currentItem: User) {
            binding.run {
                personName.text = currentItem.userFirstname
                personName.text = currentItem.userLastname
                personPhoneNumber.text = currentItem.userTelephone

            }
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            //IDENTIFIER NUMBER
            return oldItem.userTelephone == newItem.userTelephone
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }

}

data class User(
    val userFirstname : String,
    val userLastname : String,
    val userImage : String,
    val userBirthday : String,
    val userTelephone : String
)
