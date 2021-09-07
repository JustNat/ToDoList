package com.bruce.todolist.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bruce.todolist.databinding.ActivityAddBinding
import com.bruce.todolist.datasource.TaskDataSource
import com.bruce.todolist.extensions.format
import com.bruce.todolist.extensions.text
import com.bruce.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListeners()
    }

    private fun insertListeners() {
        binding.tiDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tiDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tiHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}"
                else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}"
                else timePicker.hour
                binding.tiHour.text = "$hour:$minute"
            }
            timePicker.show(supportFragmentManager, "TIME_PICKER_TAG")
        }
        binding.tiDescription.editText?.setOnClickListener {

        }
        binding.cancelButton.setOnClickListener {
            finish()
        }
        binding.createButton.setOnClickListener {
            val task = Task(
                title = binding.tiTitle.text,
                description = binding.tiDescription.text,
                date = binding.tiDate.text,
                hour = binding.tiHour.text
            )
            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}