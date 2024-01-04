package es.ua.eps.exercice4

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase ?= null

        fun getDatabase(context: Context): AppDatabase
        {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "UserDBRoom",
                )
                    .allowMainThreadQueries()
                    .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)// para fuerza a que la base de datos se cree en un único fichero
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}