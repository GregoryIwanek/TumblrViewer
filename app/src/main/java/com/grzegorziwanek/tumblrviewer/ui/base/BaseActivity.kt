package com.grzegorziwanek.tumblrviewer.ui.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.grzegorziwanek.tumblrviewer.R

abstract class BaseActivity : AppCompatActivity() {

    fun changeFragmentWithStack(fragment: Fragment, tag: String) =
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.fragment_container, fragment, tag)
            .addToBackStack(tag)
            .commit()

    fun changeFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.fragment_container, fragment)
            .commit()
}