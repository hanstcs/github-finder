package com.hanstcs.githubfinder

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hanstcs.githubfinder.databinding.ActivityMainBinding
import com.hanstcs.githubfinder.model.FindUserViewState
import com.hanstcs.githubfinder.model.UserModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FindUserViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectThisActivity()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(FindUserViewModel::class.java)

        viewModel.findUsers("h")
        viewModel.getViewStateLiveData().observe(
            this,
            Observer { state ->
                when (state) {
                    is FindUserViewState.ShowData -> {
                        initializeAdapter(state.userList)
                        showLoading(false)
                    }
                    is FindUserViewState.Loading -> showLoading(true)
                    is FindUserViewState.Idle -> showLoading(false)
                }
            }
        )
    }

    private fun showLoading(show: Boolean) {
        binding.loadingView.visibility = when (show) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    private fun injectThisActivity() {
        val githubFinderApplication = application as GithubFinderApplication
        githubFinderApplication.getAppComponent().doInjection(this)
    }

    private fun initializeAdapter(userList: List<UserModel>) {
        adapter = UserAdapter(userList)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter
    }
}
