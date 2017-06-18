package com.fitaleks.heroesthesaurus.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity


/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun FragmentActivity.addFragmentToActivity(fragment: Fragment, frameId: Int) {
    supportFragmentManager.beginTransaction()
            .add(frameId, fragment)
            .commit()
}
