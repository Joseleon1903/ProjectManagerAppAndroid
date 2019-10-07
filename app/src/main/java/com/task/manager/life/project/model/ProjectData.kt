package com.task.manager.model

import java.util.*

class ProjectData{

    var name: String= ""
    var description :String =""
    var  active: Boolean= false
    var startDate: Date = Date()
    var endDate: Date= Date()


    constructor(name: String, description :String , active: Boolean, startDate: Date, endDate: Date){
        this.name= name
        this.description =description
        this.active= active
        this.startDate =startDate
        this.endDate = endDate
    }


}