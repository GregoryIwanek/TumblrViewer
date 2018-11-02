package com.grzegorziwanek.tumblrviewer.di.component

import com.grzegorziwanek.tumblrviewer.app.App
import com.grzegorziwanek.tumblrviewer.di.contributor.AndroidContributor
import com.grzegorziwanek.tumblrviewer.di.module.ContextModule
import com.grzegorziwanek.tumblrviewer.di.module.NetworkModule
import com.grzegorziwanek.tumblrviewer.di.module.RetrofitModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component( modules = [
    ContextModule::class,
    NetworkModule::class,
    RetrofitModule::class,
    AndroidSupportInjectionModule::class,
    AndroidContributor::class])
interface MainComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>() {
        abstract fun contextModule(contextModule: ContextModule): Builder
    }
}