package com.iotta.tempproject.ui.main.view

import android.os.Bundle
import android.view.View
import com.iotta.tempproject.utils.Data
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.iotta.tempproject.mvvm.R
import com.iotta.tempproject.ui.main.adapter.MainAdapter
import com.iotta.tempproject.ui.main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        setupInput()
    }

    private fun setupView() {
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupInput() {
        mainViewModel.users.observe(this, Observer {
            when (it.status) {
                Data.DataStatus.Succeeded -> {
                    it.data?.let { users ->
                        progressBar.visibility = View.GONE
                        adapter.setData(users)
                        adapter.notifyDataSetChanged()
                        recyclerView.visibility = View.VISIBLE
                    }
                }
                Data.DataStatus.Loading -> {
                    errorMsg.isGone = true
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Data.DataStatus.Failed -> {
                    progressBar.visibility = View.GONE
                    errorMsg.text = it.message
                    errorMsg.isGone = false
                }
            }
        })
    }

}
