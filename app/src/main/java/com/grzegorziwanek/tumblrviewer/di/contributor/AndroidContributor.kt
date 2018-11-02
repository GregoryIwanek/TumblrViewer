package com.grzegorziwanek.tumblrviewer.di.contributor

import com.grzegorziwanek.tumblrviewer.ui.home.HomeFragment
import com.grzegorziwanek.tumblrviewer.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidContributor {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment
}