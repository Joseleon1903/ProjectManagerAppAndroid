package com.task.manager.life.project.ui.task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.task.manager.life.project.R

import kotlinx.android.synthetic.main.activity_add_task.*

class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        setSupportActionBar(toolbar)

    }

}
