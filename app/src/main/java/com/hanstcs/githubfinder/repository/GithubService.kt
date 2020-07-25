package com.hanstcs.githubfinder.repository

import com.hanstcs.githubfinder.model.UserSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/users")
    fun findUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<UserSearchResponse>
}
