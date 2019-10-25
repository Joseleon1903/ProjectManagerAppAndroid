package com.task.manager.life.project.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.task.manager.life.project.R
import com.task.manager.life.project.db.entity.TaskEntity
import com.task.manager.life.project.utils.InjectiorUtils
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_gallery, container, false)

        val factory = InjectiorUtils.providerTaskViewModelFactory(this@GalleryFragment.requireContext())
        galleryViewModel = ViewModelProvider(this, factory).get(TaskViewModel::class.java)

        val observer = Observer<List<TaskEntity>> { task ->
            if (task != null) {
                var text = ""
                for (contact in task) {
                    text += contact.name + " " + contact.description + " - " + contact.identifier+ "\n"
                }
                text_gallery.text = text
            }
        }
        galleryViewModel.contacts.observe(this, observer)

        val botton = root.findViewById<Button>(R.id.addContact_button)
        botton.setOnClickListener {
            addTask()
        }

        return root
    }

    fun addTask(){
        val textName = phone_editText.text.toString()
        val description = lastName_editText.text.toString()
        val identifier = identifier_editText.text.toString().toLong()
        val task = TaskEntity(textName, description, identifier )
        galleryViewModel.saveContact(task)

    }


}