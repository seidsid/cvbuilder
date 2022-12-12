package com.example.resumebuilder.models

import androidx.room.*

@Dao
interface DAO {
    @Insert
    fun addUser(user: User)

    @Transaction
    @Query("SELECT * FROM User")
    fun getAllUsers() : List<UserWithAllData>

    @Transaction
    @Query("SELECT * FROM User where emailAddress = :email")
    suspend fun getUserByEmail( email:String) : UserWithAllData


    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Insert
    fun addExperience(experience: Experience)

    @Query("SELECT * FROM Experience Order By `from` desc")
    fun getAllExperiences() : List<Experience>

    @Query("SELECT * FROM EXPERIENCE WHERE id = :id LIMIT 1")
    fun getExperienceById(id: Int) : Experience

    @Update
    fun updateExperience(experience: Experience)

    @Delete
    fun deleteExperience(experience: Experience)

    @Insert
    fun addEducation(education: Education)

    @Query("SELECT * FROM Education Order By `from` desc")
    fun getAllEducations() : List<Education>

    @Query("SELECT * FROM EDUCATION WHERE id = :id LIMIT 1")
    fun getEducationById(id: Int) : Education

    @Update
    fun updateEducation(education: Education)

    @Delete
    fun deleteEducation(education: Education)

    @Insert
    fun addExternalLink(externalLinks: ExternalLinks)

    @Query("SELECT * FROM ExternalLinks")
    fun getAllExternalLinks() : List<ExternalLinks>

    @Update
    fun updateExternalLink(externalLinks: ExternalLinks)

    @Delete
    fun deleteExternalLink(externalLinks: ExternalLinks)
}