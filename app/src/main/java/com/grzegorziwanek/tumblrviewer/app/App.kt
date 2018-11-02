package com.grzegorziwanek.tumblrviewer.app

import com.grzegorziwanek.tumblrviewer.di.component.DaggerMainComponent
import com.grzegorziwanek.tumblrviewer.di.module.ContextModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerMainComponent.builder()
            .contextModule(ContextModule(this))
            .create(this)
}