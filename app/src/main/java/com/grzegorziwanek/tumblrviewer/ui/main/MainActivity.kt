package com.grzegorziwanek.tumblrviewer.ui.main

import android.os.Bundle
import com.grzegorziwanek.tumblrviewer.R
import com.grzegorziwanek.tumblrviewer.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}