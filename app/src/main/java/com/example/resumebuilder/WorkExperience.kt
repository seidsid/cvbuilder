package com.example.resumebuilder

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.resumebuilder.models.CVDataBase
import com.example.resumebuilder.models.helper.ExperienceEducationDTO
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class WorkExperience : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_work_experience, container, false)
        var experienceEducationDTOs: ArrayList<ExperienceEducationDTO> = ArrayList()
        
        launch {
            context?.let {
                var dao = CVDataBase(it).getDao()
                var userWithAllData = dao.getAllUsers()[0]
                for (experience in userWithAllData.experiences) {
                    //Toast.makeText(view.context, experience.companyName, Toast.LENGTH_LONG).show()
                    experienceEducationDTOs.add(ExperienceEducationDTO.fromExperience(experience))
                }
                for(education in userWithAllData.educations)
                    experienceEducationDTOs.add(ExperienceEducationDTO.fromEducation(education))
                var adapter = RecyclerViewAdapter(experienceEducationDTOs)
                val recyclerView = view.findViewById<RecyclerView>(R.id.recycleView)
                recyclerView.layoutManager = LinearLayoutManager(view.context)
                recyclerView.adapter = adapter
            }
        }
        //Toast.makeText(this.context,experienceEducationDTOs.size.toString(),Toast.LENGTH_LONG).show()

        var button = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        button.setOnClickListener {
            var intent = Intent(view.context, AddExperienceEducation::class.java)
            startActivityForResult(intent, 1)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
//            Toast.makeText(this.context, "experience.companyName", Toast.LENGTH_LONG).show()
//            var adapter = view?.findViewById<RecyclerView>(R.id.recycleView)!!.adapter as RecyclerViewAdapter
//            adapter.experienceEducationDTOs.clear()
//            var dao = this.context?.let { CVDataBase(it).getDao() }
//            var userWithAllData = dao!!.getAllUsers()[0]
//            for (experience in userWithAllData.experiences) {
//
//                adapter.experienceEducationDTOs.add(ExperienceEducationDTO.fromExperience(experience))
//            }
//            for(education in userWithAllData.educations)
//                adapter.experienceEducationDTOs.add(ExperienceEducationDTO.fromEducation(education))
//
//            adapter.notifyDataSetChanged()
//        }
    }
}