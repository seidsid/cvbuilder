package com.example.resumebuilder.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.resumebuilder.models.helper.DataConverter

@Entity
@TypeConverters(DataConverter::class)
data class ExternalLinks(
    var userId: Int,
    var externalLinkType: ExternalLinksTypesEnum,
    var URL: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
