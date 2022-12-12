package com.example.resumebuilder.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

class UserWithAllData (
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId")
    var experiences : List<Experience>,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId")
    var educations : List<Education>,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId")
    var externalLinks: List<ExternalLinks>
    )