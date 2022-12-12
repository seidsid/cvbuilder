package com.example.resumebuilder.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.io.Serializable

@Entity
data class User(
    var firstName: String,
    var lastName: String,
    var emailAddress: String,
    var phoneNumber: String,
    var imageURL: String,
    var title: String,
    var address: String,
    var bio: String) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}
