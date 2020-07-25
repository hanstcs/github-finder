package com.hanstcs.githubfinder.injection.module

import androidx.lifecycle.ViewModelProvider
import com.hanstcs.githubfinder.factory.ViewModelFactory
import com.hanstcs.githubfinder.injection.ViewModelSubComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [ViewModelSubComponent::class])
class ViewModelModule {
    @Singleton
    @Provides
    internal fun providesViewModelFactory(
        viewModelSubComponent: ViewModelSubComponent.Builder
    ): ViewModelProvider.Factory {
        return ViewModelFactory(viewModelSubComponent.build())
    }
}
