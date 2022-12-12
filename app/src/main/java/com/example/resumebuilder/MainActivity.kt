package com.example.resumebuilder

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.resumebuilder.models.CVDataBase
import com.example.resumebuilder.models.User
import com.example.resumebuilder.models.UserWithAllData
import com.example.resumebuilder.models.helper.ExperienceEducationDTO
import com.google.android.material.tabs.TabLayoutMediator
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    var userWithAllData: UserWithAllData?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MyViewAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tLayout, viewPager) {tab, position ->
            when(position) {
                0 -> {
                    tab.text = "About me"
                }
                1 -> {
                    tab.text = "Experience & Education"
                }
                2 -> {
                    tab.text = "External Links"
                }
                3 -> {
                    tab.text = "Generate pdf"
                }
            }
        }.attach()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == RESULT_OK) {
            Toast.makeText(this, "experience.companyName", Toast.LENGTH_LONG).show()
            var adapter = findViewById<RecyclerView>(R.id.recycleView).adapter as RecyclerViewAdapter
            adapter.experienceEducationDTOs.clear()
            var dao = CVDataBase(this).getDao()
            var userWithAllData = dao.getAllUsers()[0]
            for (experience in userWithAllData.experiences) {

                adapter.experienceEducationDTOs.add(ExperienceEducationDTO.fromExperience(experience))
            }
            for(education in userWithAllData.educations)
                adapter.experienceEducationDTOs.add(ExperienceEducationDTO.fromEducation(education))

            adapter.notifyDataSetChanged()
        }
    }
}