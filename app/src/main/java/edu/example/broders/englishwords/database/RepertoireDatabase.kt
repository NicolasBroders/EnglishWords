package edu.example.broders.englishwords.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Quizz::class, Word::class], version = 1, exportSchema = false)
abstract class RepertoireDatabase : RoomDatabase() {

    abstract val repertoireDatabaseDao: RepertoireDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: RepertoireDatabase? = null

        fun getInstance(context: Context): RepertoireDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            RepertoireDatabase::class.java,
                            "repertoire_database"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                }

                return instance
            }
        }
    }
}