package beprogressive.uniclient

import timber.log.Timber

inline fun <reified T> T.log(message: String) {
    Timber.v(message)
}
