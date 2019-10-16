package com.task.manager.life.project.db

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.manager.life.project.db.entity.TaskEntity

class TaskRepository (context: Context) {

    private val taskDao: TaskDao? = TaskDataBase.getInstance(context)?.taskDao()

    fun insert(contact: TaskEntity) {
        if (taskDao != null) InsertAsyncTask(taskDao).execute(contact)
    }

    fun getContacts(): LiveData<List<TaskEntity>> {
        return taskDao?.getOrderedTask() ?: MutableLiveData<List<TaskEntity>>()
    }

    private class InsertAsyncTask(private val contactDao: TaskDao) :
        AsyncTask<TaskEntity, Void, Void>() {
        override fun doInBackground(vararg contacts: TaskEntity?): Void? {
            for (contact in contacts) {
                if (contact != null) contactDao.insert(contact)
            }
            return null
        }
    }
}