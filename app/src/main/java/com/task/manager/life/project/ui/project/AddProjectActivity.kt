package com.task.manager.life.project.ui.project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.task.manager.life.project.R

import kotlinx.android.synthetic.main.activity_add_project.*

class AddProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)
        setSupportActionBar(toolbar)
    }

}
