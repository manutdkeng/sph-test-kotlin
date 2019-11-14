package com.zack.sphtest

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zack.sphtest.databinding.ActivityMainBinding
import com.zack.sphtest.viewmodel.DataUsageViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: DataUsageViewModel
    private lateinit var binding: ActivityMainBinding

    private var adapter: DataUsageRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val viewModelFactory =
            SphApplication.instance.getAppComponent().getDataUsageViewModelFactory()
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DataUsageViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setObserver()
        setListener()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getDataUsageList()
    }

    private fun setObserver() {
        viewModel.data.observe(this, Observer { records ->
            binding.swipeContainer.isRefreshing = false
            if (adapter == null) {
                adapter = DataUsageRecyclerViewAdapter(records)
                binding.dataUsageList.adapter = adapter
            } else {
                adapter?.updateData(records)
            }
        })

        viewModel.error.observe(this, Observer {
            binding.swipeContainer.isRefreshing = false
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.loadingState.observe(this, Observer {
            binding.loadingLayout.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    private fun setListener() {
        binding.swipeContainer.setOnRefreshListener {
            viewModel.refreshData()
        }
    }
}
