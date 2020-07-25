package com.hanstcs.githubfinder.injection.module

import com.hanstcs.githubfinder.repository.GithubService
import com.hanstcs.githubfinder.repository.UserRepository
import com.hanstcs.githubfinder.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun providesUserRepository(githubService: GithubService): UserRepository {
        return UserRepositoryImpl(githubService)
    }
}
