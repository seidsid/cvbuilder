package com.example.resumebuilder.models.helper

import androidx.room.TypeConverter
import com.example.resumebuilder.models.ExternalLinksTypesEnum
import java.util.*

class DataConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toExternalLinksTypesEnum(value: Int) = enumValues<ExternalLinksTypesEnum>()[value]

    @TypeConverter
    fun fromExternalLinksTypesEnum(value: ExternalLinksTypesEnum) = value.ordinal
}