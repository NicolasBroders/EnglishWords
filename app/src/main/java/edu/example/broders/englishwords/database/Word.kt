package edu.example.broders.englishwords.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Word(

        @PrimaryKey(autoGenerate = true)
        var wordID : Long = 0L,

        @ColumnInfo(name = "french_version")
        val frenchVersion : String = "",

        @ColumnInfo(name = "english_version")
        var englishVersion : String = "",

        @ColumnInfo(name = "score")
        var score : Int = 0,

        @ColumnInfo(name = "id_quizz")
        var idQuizz: String = ""
)