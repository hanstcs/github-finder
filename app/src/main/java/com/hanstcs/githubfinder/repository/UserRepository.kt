package com.hanstcs.githubfinder.repository

import com.hanstcs.githubfinder.model.UserModel
import io.reactivex.Single

interface UserRepository {
    fun findUsers(searchQuery: String, itemPerPage: Int): Single<List<UserModel>>
}

class UserRepositoryImpl (
    private val githubService: GithubService
) : UserRepository {
    override fun findUsers(searchQuery: String, itemPerPage: Int): Single<List<UserModel>> {
        return githubService.findUsers(searchQuery, 1, 50)
            .map {
                UserMapper.toUserList(it)
            }
    }
}
