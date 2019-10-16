package com.task.manager.life.project.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.manager.life.project.db.TaskRepository

class TaskViewModelFactory (private val taskRepository: TaskRepository): ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TaskViewModel(taskRepository) as T
    }

}