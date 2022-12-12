package com.example.resumebuilder.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.resumebuilder.models.helper.DataConverter
import java.io.Serializable
import java.util.*

@Entity
@TypeConverters(DataConverter::class)
data class Experience(
    var companyName: String,
    var location: String,
    var title: String,
    var from: Date,
    var to:Date? = null,
    var isCurrentWorking: Boolean,
    var duties: String?,
    var userId: Int) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
