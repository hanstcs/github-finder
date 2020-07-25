package com.hanstcs.githubfinder.injection

import android.app.Activity
import android.app.Application
import com.hanstcs.githubfinder.MainActivity
import com.hanstcs.githubfinder.injection.module.AppModule
import com.hanstcs.githubfinder.injection.module.RepositoryModule
import com.hanstcs.githubfinder.injection.module.RetrofitModule
import com.hanstcs.githubfinder.injection.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        RetrofitModule::class,
        RepositoryModule::class
    ]
)

@Singleton
interface AppComponent {
    fun getApplication(): Application

    fun doInjection(activity: MainActivity)
}
