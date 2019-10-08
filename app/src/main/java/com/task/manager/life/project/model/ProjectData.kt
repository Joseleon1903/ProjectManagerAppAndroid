package com.task.manager.model

class ProjectData{

    var name: String= ""
    var description :String =""
    var  active: Boolean= false
    var startDate: String = ""
    var endDate: String = ""


    constructor(name: String, description :String , active: Boolean, startDate: String, endDate: String){
        this.name= name
        this.description =description
        this.active= active
        this.startDate =startDate
        this.endDate = endDate
    }


}