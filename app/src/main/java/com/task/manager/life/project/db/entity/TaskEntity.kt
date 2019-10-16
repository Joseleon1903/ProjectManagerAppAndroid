package com.task.manager.life.project.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_tbn")
data class TaskEntity(
    val name:String,
    val description: String,
    val identifier: Long
){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}