package com.hanstcs.githubfinder.repository

import com.hanstcs.githubfinder.model.UserModel
import com.hanstcs.githubfinder.model.UserSearchResponse

class UserMapper {
    companion object {
        fun toUserList(userSearchResponse: UserSearchResponse) : List<UserModel> {
            return userSearchResponse.items.map {
                item -> UserModel(item.login, item.avatarUrl)
            }
        }
    }
}
