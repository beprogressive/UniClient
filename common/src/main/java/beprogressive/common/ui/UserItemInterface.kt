package beprogressive.common.ui

import beprogressive.common.model.UserItem

interface UserItemInterface {

    fun onUserItemClick(item: UserItem)
    fun onUserItemFavorite(item: UserItem)
}