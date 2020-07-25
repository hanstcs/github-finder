package com.hanstcs.githubfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hanstcs.githubfinder.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FindUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectThisActivity()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(FindUserViewModel::class.java)

        viewModel.findUsers("hanstcs")
    }

    private fun injectThisActivity() {
        val githubFinderApplication = application as GithubFinderApplication
        githubFinderApplication.getAppComponent().doInjection(this)
    }
}
