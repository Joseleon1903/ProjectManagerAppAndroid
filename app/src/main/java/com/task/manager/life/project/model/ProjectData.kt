package com.task.manager.model

class ProjectData{

    var ID : Int? =0
    var name: String= ""
    var description :String =""
    var  active: Boolean= false
    var startDate: String = ""
    var endDate: String = ""


    constructor(ID : Int? , name: String, description :String , active: Boolean, startDate: String, endDate: String){
        this.name= name
        this.description =description
        this.active= active
        this.startDate =startDate
        this.endDate = endDate
        this.ID=ID
    }


}