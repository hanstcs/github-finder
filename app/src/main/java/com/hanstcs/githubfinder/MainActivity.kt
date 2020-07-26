package com.hanstcs.githubfinder

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        viewModel.getViewStateLiveData().observe(
            this,
            Observer { state ->
                when (state) {
                    is FindUserViewState.ShowData -> {
                        showUserList(state.userList)
                        showLoading(false)
                    }
                    is FindUserViewState.Loading -> showLoading(true)
                    is FindUserViewState.Idle -> {
                        showLoading(false)

                    }
                }
            }
        )
        setupSearch()
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

    private fun showUserList(userList: List<UserModel>) {
        binding.tvNoUserFound.visibility = if (userList.isEmpty()) View.VISIBLE else View.GONE
        adapter = UserAdapter(userList)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter
    }

    private fun setupSearch() {
        viewModel.startObservingSearchView()
        binding.btnSubmit.setOnClickListener {
            viewModel.onQuerySubmit(
                binding.etSearchUser.text.toString()
            )
        }
        binding.etSearchUser.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.onSearchTextChanged(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}
