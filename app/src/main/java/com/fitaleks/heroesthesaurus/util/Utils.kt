package com.fitaleks.heroesthesaurus.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by alexanderkulikovskiy on 15.01.16.
 */
fun Context.isConnected() : Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnectedOrConnecting
}
