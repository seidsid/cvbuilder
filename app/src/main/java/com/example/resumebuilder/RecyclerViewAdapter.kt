package com.example.resumebuilder

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.resumebuilder.models.CVDataBase
import com.example.resumebuilder.models.helper.ExperienceEducationDTO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DateFormat


class RecyclerViewAdapter(var experienceEducationDTOs: ArrayList<ExperienceEducationDTO>) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.work_experience_or_education_per_rv,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtTitle.text = experienceEducationDTOs[position].title
        holder.txtComUnvName.text = experienceEducationDTOs[position].expUniName
        holder.txtComUnvLocation.text = experienceEducationDTOs[position].location
        holder.txtFrom.text = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(experienceEducationDTOs[position].from)
        if(experienceEducationDTOs[position].isCurrent)
            holder.txtTo.text = "STILL"
        else
            holder.txtTo.text = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(experienceEducationDTOs[position].to)
        if(experienceEducationDTOs[position].isExperience)
            holder.txtDuties.text = experienceEducationDTOs[position].duteis
        else
            holder.txtDuties.isVisible = false
        var x = holder.bindingAdapter

        holder.bind(experienceEducationDTOs[position], this, position)
    }

    override fun getItemCount() = experienceEducationDTOs.size

    class MyViewHolder(var itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        var txtComUnvName: TextView = itemView.findViewById(R.id.txtComUnvName)
        var txtComUnvLocation: TextView = itemView.findViewById(R.id.txtComUnvLocation)
        var txtFrom: TextView = itemView.findViewById(R.id.txtFrom)
        var txtTo: TextView = itemView.findViewById(R.id.txtTo)
        var txtDuties: TextView = itemView.findViewById(R.id.txtDuties)
        var btnDelete: Button = itemView.findViewById(R.id.btnDelete)
        var btnEdit: Button = itemView.findViewById(R.id.btnEdit)


        fun bind(experienceEducationDTO: ExperienceEducationDTO, recyclerViewAdapter: RecyclerViewAdapter, position: Int){

            btnDelete.setOnClickListener {
                var builder = AlertDialog.Builder(itemView.context)
                builder.setTitle("Delete item")
                builder.setMessage("Are you sure you want to delete this?")
                builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                    var dao = CVDataBase(it.context).getDao()

                    if (experienceEducationDTO.isExperience) {
                        val experience = dao.getExperienceById(experienceEducationDTO.id)
                        dao.deleteExperience(experience)
                    } else {
                        val education = dao.getEducationById(experienceEducationDTO.id)
                        dao.deleteEducation(education)
                    }
                    recyclerViewAdapter.experienceEducationDTOs.removeAt(position)
                    recyclerViewAdapter.notifyItemRemoved(position)
                    recyclerViewAdapter.notifyItemRangeChanged(position, recyclerViewAdapter.itemCount);
                    Toast.makeText(
                        itemView.context,
                        "item deleted successfully",
                        Toast.LENGTH_LONG
                    ).show()
                })

                builder.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.cancel()
                })
                builder.show()

            }

            btnEdit.setOnClickListener {
                var intent = Intent(itemView.context, AddExperienceEducation::class.java)
                intent.putExtra("ExperienceEducationDTO", experienceEducationDTO)
                var item = itemView.context  as MainActivity
                item.startActivityForResult(intent,1)
            }

//            itemView.setOnClickListener {
////                var intent = Intent(itemView.context, ItemDetails::class.java)
////                intent.putExtra("product",prodcut)
////                itemView.context.startActivity(intent)
//            }
        }
    }


}