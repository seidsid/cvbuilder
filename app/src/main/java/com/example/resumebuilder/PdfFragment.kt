package com.example.resumebuilder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.resumebuilder.models.CVDataBase
import com.example.resumebuilder.models.UserWithAllData
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class PdfFragment : Fragment() {
    private val STORAGE_CODE:Int=100
    var userWithAllData: UserWithAllData?=null;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val db = this.context?.let { CVDataBase.invoke(it) };


        val userDAO = db?.getDao()

        GlobalScope.launch {

            userWithAllData = userDAO?.getUserByEmail("MAbdelzaher@miu.edu")


        }

        var view = inflater.inflate(R.layout.fragment_pdf, container, false)
        var btnToPdf = view.findViewById<ImageButton>(R.id.pdf)
        btnToPdf?.setOnClickListener {
            Toast.makeText(this.context, "sds", Toast.LENGTH_LONG).show()
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(view.context,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, STORAGE_CODE)
                } else {
                    savePdf()
                }
            } else {
                savePdf()
            }
        }
        return view
    }

    private fun savePdf() {
        val mDoc= Document()
        val fileName= SimpleDateFormat("yyyymmdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
        val filePath= Environment.getExternalStorageDirectory().toString()+"/" + fileName+".pdf"
        try {
            PdfWriter.getInstance(mDoc, FileOutputStream(filePath))
            mDoc.open()
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("                                                           "+userWithAllData?.user?.firstName+" "+userWithAllData?.user?.lastName))
            mDoc.add(Paragraph("                                             "+userWithAllData?.user?.emailAddress +"   " +userWithAllData?.user?.phoneNumber))
            mDoc.add(Paragraph("                                                           "+userWithAllData?.user?.title))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("Bio"))
            mDoc.add(Paragraph("--------------------------------------------------------------------------------------------------------------------------------"))
            mDoc.add(Paragraph(userWithAllData?.user?.bio))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("EDUCATION"))
            mDoc.add(Paragraph("--------------------------------------------------------------------------------------------------------------------------------"))
            for (edu in userWithAllData!!.educations){

                mDoc.add(Paragraph("                                                   "+edu.schoolName+","+edu.location))
                // mDoc.add(Paragraph("                      "+edu.location))
                mDoc.add(Paragraph("                                                   "+edu.title))

            }
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("EXPERIENCE"))
            mDoc.add(Paragraph("--------------------------------------------------------------------------------------------------------------------------------"))
            for (exp in userWithAllData!!.experiences){

                mDoc.add(Paragraph("                                                   "+exp.companyName+""+exp.location))
                mDoc.add(Paragraph("                                                   "+exp.title))
                mDoc.add(Paragraph("                                                   "+exp.from.toString()+""+ exp.to.toString()))


            }

            mDoc.close()
//            val file = File(filePath)
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.setDataAndType(Uri.fromFile(file), "application/pdf")
//            startActivity(intent)
////            if (intent.resolveActivityInfo(packageManager, 0) != null) {
////
////            } else {
////                Toast.makeText(this,"Error", Toast.LENGTH_LONG).show()
////                // if you reach this place, it means there is no any file
////                // explorer app installed on your device
////            }
            Toast.makeText(this.context, "$fileName.pdf\n is saved to\n $filePath", Toast.LENGTH_LONG).show()

        }
        catch (e: Exception) {
            Toast.makeText(this.context, e.message, Toast.LENGTH_SHORT).show()
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            STORAGE_CODE->{
                if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    savePdf()
                }
                else{
                    Toast.makeText(this.context,"permission denied....!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}