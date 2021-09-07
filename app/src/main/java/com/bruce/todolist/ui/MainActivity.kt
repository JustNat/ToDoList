package com.bruce.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.bruce.todolist.databinding.ActivityMainBinding
import com.bruce.todolist.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    private val result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data: Intent? = it.data
            operationsActivityResult()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter

        insertListeners()
    }

    private fun insertListeners() {
        binding.fabAdd.setOnClickListener {
            binding.recyclerView.adapter= adapter
            adapter.submitList(TaskDataSource.getList())
            val intent = Intent(this, AddTaskActivity::class.java)
            result.launch(intent)
            }
        }

    private fun operationsActivityResult() {
        binding.recyclerView.adapter = adapter
        adapter.submitList(TaskDataSource.getList())
    }
    }


