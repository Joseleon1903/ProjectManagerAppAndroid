package com.task.manager.life.project.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.task.manager.life.project.db.entity.TaskEntity

@Dao
interface TaskDao {

    @Insert
    fun insert(task: TaskEntity)

    @Update
    fun update(vararg task: TaskEntity)

    @Delete
    fun delete(vararg contact: TaskEntity)

    @Query("SELECT * FROM task_tbn ORDER BY ID")
    fun getOrderedTask(): LiveData<List<TaskEntity>>


}