package com.example.resumebuilder

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.example.resumebuilder.models.CVDataBase
import com.example.resumebuilder.models.helper.ExperienceEducationDTO
import kotlinx.android.synthetic.main.activity_add_experience_education.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddExperienceEducation : AppCompatActivity() {
    lateinit var checkBox: CheckBox
    lateinit var checkBoxIsExper: CheckBox
    lateinit var experienceEducationDTO: ExperienceEducationDTO
    var isEdit = false
    var isExper = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_experience_education)

        checkBox = findViewById<CheckBox>(R.id.checkBoxIsCurrent)

        checkBox.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { compoundButton, b ->
                if(compoundButton.isChecked) {
                    textView10.isVisible = false
                    txtTo.isVisible = false
                }
                else {
                    textView10.isVisible = true
                    txtTo.isVisible = true
                }
            }
        )

        checkBoxIsExper = findViewById(R.id.isExperience)
        checkBoxIsExper.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { compoundButton, b ->
                isExper = compoundButton.isChecked
            }
        )
        txtFrom.text = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(Calendar.getInstance().time)
        txtTo.text = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(Calendar.getInstance().time)
        checkBox.isChecked = true
        if(intent.hasExtra("ExperienceEducationDTO")) {
            isEdit = true
            experienceEducationDTO = intent.getSerializableExtra("ExperienceEducationDTO") as ExperienceEducationDTO
            populateData(experienceEducationDTO)
        }
    }

    fun populateData(experienceEducationDTO: ExperienceEducationDTO) {
        txtComUnvName.setText(experienceEducationDTO.expUniName)
        txtComUnvLocation.setText(experienceEducationDTO.location)
        txtTitle.setText(experienceEducationDTO.title)
        txtFrom.text = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(experienceEducationDTO.from)
        if(!experienceEducationDTO.isCurrent)
            txtTo.text = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(experienceEducationDTO.to)
        checkBox.isChecked = experienceEducationDTO.isCurrent
        checkBoxIsExper.isChecked = experienceEducationDTO.isExperience
    }

    fun onClickSave(view: android.view.View) {
        if(isEdit) {
            experienceEducationDTO.title = txtTitle.text.toString()
            experienceEducationDTO.expUniName = txtComUnvName.text.toString()
            experienceEducationDTO.location = txtComUnvLocation.text.toString()
            experienceEducationDTO.from = SimpleDateFormat("dd/MM/yyyy").parse(txtFrom.text.toString())
            if (!checkBox.isChecked)
                experienceEducationDTO.to = SimpleDateFormat("dd/MM/yyyy").parse(txtTo.text.toString())

            var dao = CVDataBase(this).getDao()

            if(experienceEducationDTO.isExperience) {
                dao.updateExperience(ExperienceEducationDTO.toExperience(experienceEducationDTO))
            }
            else {
                dao.updateEducation(ExperienceEducationDTO.toEducation(experienceEducationDTO))
            }
        }
        else {
            var data = ExperienceEducationDTO(
                checkBoxIsExper.isChecked,
                -1,
                txtComUnvName.text.toString(),
                txtTitle.text.toString(),
                txtComUnvLocation.text.toString(),
                "",
                SimpleDateFormat("dd/MM/yyyy").parse(txtFrom.text.toString()),
                SimpleDateFormat("dd/MM/yyyy").parse(txtTo.text.toString()),
                checkBox.isChecked,
                1)

            var dao = CVDataBase(this).getDao()
            if(checkBoxIsExper.isChecked)
                dao.addExperience(ExperienceEducationDTO.toExperience(data))
            else
                dao.addEducation(ExperienceEducationDTO.toEducation(data))
        }

        setResult(RESULT_OK)
        finish()
    }

    fun onClickCancel(view: android.view.View) {
        setResult(RESULT_CANCELED)
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun onDateTextClick(view: android.view.View) {
        if(isEdit) {
            var datePicker: DatePickerDialog =
            when(view.id)
            {
                R.id.txtFrom -> DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->  },
                    experienceEducationDTO.from.year,
                    experienceEducationDTO.from.month,
                    experienceEducationDTO.from.day)

                R.id.txtTo -> DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->  },
                    experienceEducationDTO.to!!.year,
                    experienceEducationDTO.to!!.month,
                    experienceEducationDTO.to!!.day)
                else -> DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->  },
                    Calendar.getInstance().time.year,
                    Calendar.getInstance().time.month,
                    Calendar.getInstance().time.day)
            }
            datePicker.setOnDateSetListener { datePicker, i, i2, i3 ->
                findViewById<TextView>(view.id).text = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(
                    Date(datePicker.year, datePicker.month, datePicker.dayOfMonth)
                )
            }
            datePicker.show()
        }
        else {
            var datePicker: DatePickerDialog =  DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                    findViewById<TextView>(view.id).text = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(
                        Date(datePicker.year, datePicker.month, datePicker.dayOfMonth)
                    )
                },
                Calendar.getInstance().time.year,
                Calendar.getInstance().time.month,
                Calendar.getInstance().time.day)
            datePicker.show()
        }
    }
}