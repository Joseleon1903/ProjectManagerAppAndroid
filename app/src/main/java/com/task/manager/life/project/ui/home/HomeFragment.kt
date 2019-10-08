package com.task.manager.life.project.ui.home

import android.content.Context
import android.os.Bundle
import android.util.EventLogTags
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.task.manager.life.project.R
import com.task.manager.life.project.db.DataBaseManager
import com.task.manager.model.ProjectData
import kotlinx.android.synthetic.main.molde_project.view.*
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    var listProject = ArrayList<ProjectData>()
    var adapter: ProjectAdapter? =null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)

//        homeViewModel.text.observe(this, Observer {
//            textView.text = it
//        })

        loadProject()

        adapter = ProjectAdapter(this.context, listProject)

        val viewListado: ListView = root.findViewById(R.id.viewListado)

        viewListado.adapter = adapter

        return root
    }


    class ProjectAdapter(context: Context?, var listProject: ArrayList<ProjectData>) : BaseAdapter(){

        var context : Context? = context

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            val project = listProject[p0]

            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            var miVista = inflater.inflate(R.layout.molde_project, null)
            miVista.textProjectName.setText(project.name)
            if (!project.active)  miVista.textProjectStatus.setText("INACTIVE") else miVista.textProjectStatus.setText("ACTIVE")
            miVista.textPercentage.setText("0 %")
            return miVista
        }

        override fun getCount(): Int {
           return  listProject.size
        }

        override fun getItem(p0: Int): Any {
            return listProject[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

    }

    private fun loadProject(){
        listProject.clear()
        var database = DataBaseManager(this.context!!)
        var columnas = arrayOf("ID", "NAME", "DESCRIPTION", "STATUS", "START_DATE", "END_DATE")
        val cursor = database.query(columnas, null, null,null)

        if(cursor.moveToFirst()) {
            do {
                val ID  = cursor.getInt(cursor.getColumnIndex("ID"))
                val name = cursor.getString(cursor.getColumnIndex("NAME"))
                val description = cursor.getString(cursor.getColumnIndex("DESCRIPTION"))
                val status = cursor.getInt(cursor.getColumnIndex("STATUS")) > 0
                val startDate = cursor.getString(cursor.getColumnIndex("START_DATE"))
                val endDate = cursor.getString(cursor.getColumnIndex("END_DATE"))

                val proj = ProjectData(name,description, status, startDate, endDate )
                listProject.add(proj)

            } while (cursor.moveToNext())

        }

    }


}