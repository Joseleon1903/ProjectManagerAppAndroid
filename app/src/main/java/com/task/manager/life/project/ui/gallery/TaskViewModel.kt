package com.task.manager.life.project.ui.gallery

import androidx.lifecycle.ViewModel
import com.task.manager.life.project.db.TaskRepository
import com.task.manager.life.project.db.entity.TaskEntity

class TaskViewModel(private val taskRepository: TaskRepository): ViewModel() {

    val contacts = taskRepository.getContacts()

    fun saveContact(contact: TaskEntity) {
        taskRepository.insert(contact)
    }



}