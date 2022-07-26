package beprogressive.uniclient.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import beprogressive.common.databinding.ItemUserBinding
import beprogressive.common.model.UserItem
import beprogressive.common.ui.UserItemInterface


class UserListAdapter(
    val userItemCallback: UserItemInterface,
) : ListAdapter<UserItem, UserListAdapter.ViewHolder>(UserItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemUserBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty())
            when (val payload = payloads[0]) {
                is UserItemDiffCallback.Companion.Payloads.FAVORITE -> holder.onBindFavorite(payload.isFavorite)
            }
        else
            super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(private var binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBindFavorite(isFavorite: Boolean) {
            binding.userItem?.isFavorite = isFavorite
            binding.isFavorite = isFavorite
        }

        fun bind(userItem: UserItem) {
            binding.userItem = userItem
            binding.isFavorite = userItem.isFavorite
            binding.userItemCallback = userItemCallback
            binding.executePendingBindings()
        }
    }
}

class UserItemDiffCallback : DiffUtil.ItemCallback<UserItem>() {
    companion object {
        sealed class Payloads {
            data class FAVORITE(val isFavorite: Boolean) : Payloads()
        }
    }

    override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.userId == newItem.userId && oldItem.apiKey == newItem.apiKey
    }

    override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.isFavorite == newItem.isFavorite
    }

    override fun getChangePayload(oldItem: UserItem, newItem: UserItem): Payloads? {
        return if (oldItem.isFavorite != newItem.isFavorite)
            Payloads.FAVORITE(newItem.isFavorite)
        else null
    }
}