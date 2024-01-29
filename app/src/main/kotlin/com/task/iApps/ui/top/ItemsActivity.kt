package com.task.iApps.ui.top

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.flow_mvvm_sample.R
import com.example.flow_mvvm_sample.databinding.ActivityTopBinding
import com.task.iApps.model.ResponseModel
import com.task.iApps.util.extensions.bind
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class ItemsActivity : AppCompatActivity() {
    private val viewModel: ItemsViewModel by viewModel()

    private lateinit var binding: ActivityTopBinding
    private lateinit var adapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        bindViewModel()
        binding.error.setOnClickRetryButton {
            viewModel.retry()
        }
    }

    private fun setupView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_top)

    }

    private fun bindViewModel() {
        bind(viewModel.isLoading) {
            binding.isLoading = it
        }
        bind(viewModel.isFail) {
            binding.isFail = it
        }
        bind(viewModel.data) {
            adapter = ItemsAdapter(this::showDetail, it)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager =
                GridLayoutManager(this, this.resources.getInteger(R.integer.span_count))
            adapter.setList(it)
        }
    }

    private fun showDetail(item: ResponseModel.Item) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
        startActivity(browserIntent)
    }
}