package com.deletech.sasakazi.storage
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.deletech.sasakazi.models.auth.Profile
import com.deletech.sasakazi.storage.daos.ProfileDao
@Database(entities = [Profile::class],version = 1,exportSchema = false)
 abstract class SasaKaziDatabase :RoomDatabase() {
     companion object{
         private lateinit var INSTANCE:SasaKaziDatabase
         fun getDatabase(context: Context):SasaKaziDatabase?{
             synchronized(SasaKaziDatabase::class.java){
                 INSTANCE = Room.databaseBuilder(context.applicationContext,
                     SasaKaziDatabase::class.java,"sasaKazi_preferences"
                 )
                     .fallbackToDestructiveMigration()
                     .allowMainThreadQueries()
                     .build()
             }
             return INSTANCE
         }}
    abstract fun profileDao(): ProfileDao
 }
