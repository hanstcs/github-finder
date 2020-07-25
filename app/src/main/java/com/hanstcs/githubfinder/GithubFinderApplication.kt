package com.hanstcs.githubfinder

import android.app.Application
import com.hanstcs.githubfinder.injection.AppComponent
import com.hanstcs.githubfinder.injection.DaggerAppComponent
import com.hanstcs.githubfinder.injection.module.AppModule

class GithubFinderApplication : Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}
