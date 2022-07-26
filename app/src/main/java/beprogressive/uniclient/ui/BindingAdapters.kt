package beprogressive.uniclient.ui

import android.view.View
import androidx.databinding.BindingAdapter
import beprogressive.uniclient.data.ClientUser

@BindingAdapter("bind:authVisibility")
fun View.authVisibility(clientUser: ClientUser?) {
   visibility = if (clientUser == null || clientUser.userName == "")
      View.VISIBLE
   else
      View.GONE
}

@BindingAdapter("bind:profileVisibility")
fun View.profileVisibility(clientUser: ClientUser?) {
   visibility = if (clientUser == null || clientUser.userName == "")
      View.GONE
   else
      View.VISIBLE
}