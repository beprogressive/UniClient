package beprogressive.favorites.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import beprogressive.common.databinding.ItemUserBinding
import beprogressive.common.model.UserItem
import beprogressive.common.ui.UserItemInterface

class FavoritesListAdapter(
    private val userItemCallback: UserItemInterface,
) : ListAdapter<UserItem, FavoritesListAdapter.ViewHolder>(UserItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemUserBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(private var binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userItem: UserItem) {
            binding.userItem = userItem
            binding.isFavorite = userItem.isFavorite
            binding.userItemCallback = userItemCallback
        }
    }
}

class UserItemDiffCallback : DiffUtil.ItemCallback<UserItem>() {
    override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }
}