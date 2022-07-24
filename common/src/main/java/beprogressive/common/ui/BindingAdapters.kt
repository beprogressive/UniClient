package beprogressive.common.ui

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import beprogressive.common.R
import beprogressive.common.model.UserItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<UserItem>?) {
    items?.let {
        (listView.adapter as? ListAdapter<UserItem, RecyclerView.ViewHolder>)?.submitList(items)
    }
}

@BindingAdapter("bind:favorite")
fun AppCompatImageView.bindFavorite(isFavorite: Boolean) {
    if (isFavorite) {
        setColorFilter(context.getColor(R.color.orange))
    } else {
        clearColorFilter()
    }
}

@BindingAdapter("bind:src")
fun AppCompatImageView.bindSrc(src: String?) {
    Glide
        .with(context)
        .load(src)
        .centerCrop()
        .apply(RequestOptions.circleCropTransform())
        .error(R.drawable.ic_smile)
        .into(this)
}