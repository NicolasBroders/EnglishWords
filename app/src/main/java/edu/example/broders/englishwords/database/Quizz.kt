package edu.example.broders.englishwords.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizz_table")
data class Quizz(

        @PrimaryKey(autoGenerate = true)
        var quizzID : Long = 0L,

        @ColumnInfo(name = "quizz_name")
        var quizzName : String = ""
)