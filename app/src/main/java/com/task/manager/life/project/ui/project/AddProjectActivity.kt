package com.task.manager.life.project.ui.project

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.task.manager.life.project.R
import com.task.manager.life.project.db.DataBaseManager
import com.task.manager.life.project.ui.MainActivity
import com.task.manager.model.ProjectData
import kotlinx.android.synthetic.main.activity_add_project.*
import kotlinx.android.synthetic.main.content_add_project.*

class AddProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)
        setSupportActionBar(toolbar)

        if(intent!!.extras != null){
            val idProjectData:Int? = intent!!.extras!!.getInt("projectId")
            val name = intent!!.extras!!.getString("projectName")
            val description = intent!!.extras!!.getString("projectDescription")
            val status = intent!!.extras!!.getBoolean("projectStatus")
            val starDate = intent!!.extras!!.getString("projectStartDate")
            val enDate = intent!!.extras!!.getString("projectEndDate")
            val project = ProjectData(idProjectData, name, description, status, starDate, enDate )
            setViewData(project)
        }

        button3.setOnClickListener{

            if(intent!!.extras != null){
                updateProject(intent!!.extras!!.getInt("projectId"))
            }else{
             addProject()
            }
        }

    }


    private fun addProject(){
        val values = ContentValues()
        val database = DataBaseManager(this@AddProjectActivity)

        val project = getViewData()

        values.put("NAME",project.name )
        values.put("DESCRIPTION",project.name )
        values.put("STATUS",project.name )
        values.put("START_DATE",project.name )
        values.put("END_DATE",project.name )

        val Id =database.insert(values)

        if(Id > 0){
            Toast.makeText(this, "Project creation Successifull", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java )
            startActivity(intent)
        }else{
            Toast.makeText(this, "Project creation fail", Toast.LENGTH_LONG).show()
        }
    }

    fun getViewData(): ProjectData{

        val name : String = textProjectNameInput.text.toString().replace("\n", " ")
        val description : String = textProjectDescriptionInput.text.toString().trim().replace("\n", " ")
        val status : String =switch1.text.toString()
        val strDate : String =editText2.text.toString()
        val endDate: String =editText3.text.toString()
        val active : Boolean = if (status == "ACTIVE") true else false

        return  ProjectData(null, name, description, active, strDate, endDate )

    }

    fun setViewData(project : ProjectData){
        textProjectNameInput.setText(project.name)
        textProjectDescriptionInput.setText(project.description)
        switch1.setText(project.active.toString())
        editText2.setText(project.startDate)
        editText3.setText(project.endDate)
    }

    private fun updateProject(id : Int) {

        val values = ContentValues()
        val database = DataBaseManager(this@AddProjectActivity)

        val project = getViewData()

//        values.put("ID", id)
        values.put("NAME", project.name)
        values.put("DESCRIPTION", project.description)
        values.put("STATUS", project.active)
        values.put("START_DATE", project.startDate)
        values.put("END_DATE", project.endDate)

        val selectionArgs = arrayOf(id.toString())
        val Id = database.update(values, "ID=?", selectionArgs)

        if (Id > 0) {
            Toast.makeText(this, "Project update Successfully", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Project update fail", Toast.LENGTH_LONG).show()
        }
    }


}
