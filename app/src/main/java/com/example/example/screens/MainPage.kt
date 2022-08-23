package com.example.example.screens

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.example.App
import com.example.example.R
import com.example.example.databinding.PageMainBinding
import com.example.example.util.PageContract
import com.example.example.util.alertPayload
import com.example.example.util.extractString
import com.example.example.util.viewBinding
import javax.inject.Inject

class MainPage : AppCompatActivity(R.layout.page_main),
    PageContract<PageMainBinding, MainViewModel> {

    override val binding: PageMainBinding by viewBinding(PageMainBinding::inflate)
    override val vm: MainViewModel by viewModels<MainViewModel.Default> { factory }
    override val pageScope: LifecycleCoroutineScope get() = this.lifecycleScope
    override val pageLifecycle: Lifecycle get() = this.lifecycle

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        App.newsComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding.setupSubscriptions()
    }

    private fun PageMainBinding.setupSubscriptions() {
        vm.error.collectInScope {
            alertPayload(title = "Error", message = it.extractString())
        }
        vm.isLoading.collectInScope(progressBar::isVisible::set)
        vm.newsList.collectInScope { newsList ->
            val adapter = NewsAdapter()
            newsRecyclerView.adapter = adapter
            adapter.submitList(newsList)
        }
    }
}