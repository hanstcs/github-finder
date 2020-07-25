package com.hanstcs.githubfinder.factory

import android.util.ArrayMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hanstcs.githubfinder.FindUserViewModel
import com.hanstcs.githubfinder.injection.ViewModelSubComponent
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import java.util.concurrent.Callable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory : ViewModelProvider.Factory {
    private var creators: ArrayMap<Class<out ViewModel>,
            @JvmSuppressWildcards Callable<ViewModel>> = ArrayMap()

    @Suppress("ConvertSecondaryConstructorToPrimary", "ReplacePutWithAssignment")
    @Inject
    constructor(subComponent: ViewModelSubComponent) {
        creators.put(FindUserViewModel::class.java, Callable { subComponent.findUserViewModel() })
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.asIterable().firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("Unknown model class $modelClass")

        try {
            return creator.call() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
