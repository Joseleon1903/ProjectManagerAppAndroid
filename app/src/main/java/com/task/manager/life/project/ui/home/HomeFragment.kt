package com.task.manager.life.project.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.task.manager.life.project.R
import com.task.manager.model.ProjectData
import kotlinx.android.synthetic.main.fragment_home.*
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

        setUpListaTest()

        adapter = ProjectAdapter(this.context, listProject)

        val viewListado: ListView = root.findViewById(R.id.viewListado)

        viewListado.adapter = adapter



        return root
    }


    private fun setUpListaTest(){
        val project = ProjectData("test", "description", true, Date(), Date())
        listProject.add(project)
    }

    class ProjectAdapter(contexto: Context?, var listaProject: ArrayList<ProjectData>) : BaseAdapter(){

        var context : Context? = contexto

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            val project = listaProject[p0]

            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val miVista = inflater.inflate(R.layout.molde_project, null)
            miVista.textProjectName.setText(project.name)
            if (!project.active)  miVista.textProjectStatus.setText("INACTIVE") else miVista.textProjectStatus.setText("ACTIVE")
            miVista.textPercentage.setText("0 %")
            return miVista

        }

        override fun getCount(): Int {
           return  listaProject.size
        }

        override fun getItem(p0: Int): Any {
            return listaProject[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }


    }


}