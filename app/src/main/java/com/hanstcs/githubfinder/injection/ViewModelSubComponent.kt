package com.hanstcs.githubfinder.injection

import com.hanstcs.githubfinder.FindUserViewModel
import dagger.Subcomponent

@Subcomponent
interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }

    fun findUserViewModel(): FindUserViewModel

}
