package com.hanstcs.githubfinder.model

sealed class FindUserViewState {
    object Idle : FindUserViewState()

    object Loading : FindUserViewState()

    data class ShowData(
        val userList: List<UserModel>
    ) : FindUserViewState()
}
