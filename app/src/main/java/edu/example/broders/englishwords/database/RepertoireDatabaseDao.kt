package edu.example.broders.englishwords.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RepertoireDatabaseDao {

    @Insert
    fun insert(quizz: Quizz)

    @Update
    fun update(quizz: Quizz)

    @Query("SELECT * FROM quizz_table WHERE quizzID = :key")
    fun getQuizz(key: Long): Quizz

    @Query("SELECT * FROM quizz_table ORDER BY quizzID DESC")
    fun getAllQuizzByID(): LiveData<List<Quizz>>

    @Query("SELECT * FROM quizz_table ORDER BY quizz_name DESC")
    fun getAllQuizzByName(): LiveData<List<Quizz>>

    @Query("DELETE  FROM quizz_table")
    fun clearAllQuizz()

    @Insert
    fun insert(word: Word)

    @Update
    fun update(word: Word)

    @Query("SELECT * FROM word_table WHERE wordID = :key")
    fun getWordById(key: Long): Word

    @Query("DELETE  FROM word_table")
    fun clearWord()

    @Query("SELECT * FROM word_table ORDER BY english_version DESC")
    fun getAllWordByEnglishVersion(): LiveData<List<Word>>

    @Query("SELECT * FROM word_table ORDER BY french_version DESC")
    fun getAllWordByFrenchVersion(): LiveData<List<Word>>

    @Query("SELECT * FROM word_table ORDER BY score DESC")
    fun getAllWordByScoreDesc(): LiveData<List<Word>>

    @Query("SELECT * FROM word_table ORDER BY score ASC")
    fun getAllWordByScoreAsc(): LiveData<List<Word>>

}