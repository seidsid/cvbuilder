package com.example.resumebuilder.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*
import java.util.concurrent.Executors


@Database(
    entities = [User::class, Experience::class, Education::class, ExternalLinks::class],
    version = 1
)
abstract class CVDataBase() : RoomDatabase() {
    abstract fun getDao(): DAO

    companion object {
        @Volatile
        private var instance: CVDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDataBase(context).also {
                instance = it
            }
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CVDataBase::class.java,
            "CVDataBase")
            .addCallback(object : Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newSingleThreadScheduledExecutor().execute(Runnable {
                        instance?.getDao()?.addUser(User("Mohamed","Abdelzaher","MAbdelzaher@miu.edu","641-819-1159","","Software Engineer","1000 N 4th street","More than four years experience as a software engineer, worked with java, kotlin, spring boot, spring MVC, hibernate and many other backend technologies."))
                        instance?.getDao()?.addExperience(Experience("Zen3 InfoSolutions","Redmond - WA", "Software Engineer", Date(2020,11,9),Date(2021,4,15),false,"DDDDD", 1))
                        instance?.getDao()?.addExperience(Experience("MIU university","Fairfield - IA", "Software Engineer", Date(2021,4,16),null,true,"DUTIES", 1))
                        instance?.getDao()?.addEducation(Education("Port Said University","Egypt","Bachelor in Engineering",Date(2006,9,1), Date(2011,5,30),true,1))
                        instance?.getDao()?.addExternalLink(ExternalLinks(1,ExternalLinksTypesEnum.LinkedIn, "https://www.linkedin.com/in/mohamed-abdelzaher/"))
                        instance?.getDao()?.addExternalLink(ExternalLinks(1,ExternalLinksTypesEnum.GitHub, "https://github.com/MEssam64/"))
                    })
                }
            })
            .allowMainThreadQueries()
            .build()
    }
}