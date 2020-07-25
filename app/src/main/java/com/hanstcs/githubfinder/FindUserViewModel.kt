package com.hanstcs.githubfinder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hanstcs.githubfinder.model.FindUserViewState
import com.hanstcs.githubfinder.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FindUserViewModel @Inject constructor(
    private val repo: UserRepository
) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val viewStateLiveData = MutableLiveData<FindUserViewState>(FindUserViewState.Idle)
    private val warningMessage = MutableLiveData<Int>()

    fun findUsers(query: String) {
        viewStateLiveData.value = FindUserViewState.Loading
        disposable.add(
            repo.findUsers(query, 50)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewStateLiveData.postValue(FindUserViewState.ShowData(it))
                }, {
                    warningMessage.postValue(R.string.something_wrong)
                })
        )
    }
}
