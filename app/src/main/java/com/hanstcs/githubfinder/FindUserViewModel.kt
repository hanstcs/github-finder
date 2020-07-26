package com.hanstcs.githubfinder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hanstcs.githubfinder.model.FindUserViewState
import com.hanstcs.githubfinder.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FindUserViewModel @Inject constructor(
    private val repo: UserRepository
) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val viewStateLiveData = MutableLiveData<FindUserViewState>(FindUserViewState.Idle)
    private val warningMessage = MutableLiveData<Int>()
    private lateinit var searchViewPublisher: PublishSubject<String>

    fun getViewStateLiveData(): LiveData<FindUserViewState> {
        return viewStateLiveData
    }

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

    fun startObservingSearchView() {
        searchViewPublisher = PublishSubject.create()
        disposable.add(searchViewPublisher
            .debounce(500, TimeUnit.MILLISECONDS)
            .filter { it.isNotEmpty() }
            .distinctUntilChanged()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                findUsers(it)
            })
    }

    fun onSearchTextChanged(query: String) {
        searchViewPublisher.onNext(query)
    }

    fun onQuerySubmit(query: String) {
        if (query.isNotEmpty())
            findUsers(query)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
        searchViewPublisher.onComplete()
    }
}
