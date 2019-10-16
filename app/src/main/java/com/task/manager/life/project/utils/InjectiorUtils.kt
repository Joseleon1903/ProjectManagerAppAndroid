package com.task.manager.life.project.utils

import android.content.Context
import com.task.manager.life.project.db.TaskRepository
import com.task.manager.life.project.ui.gallery.TaskViewModelFactory

object InjectiorUtils {


    fun providerTaskViewModelFactory(context: Context):TaskViewModelFactory{
        val taskRepository =TaskRepository(context)
        return TaskViewModelFactory(taskRepository)
    }

}