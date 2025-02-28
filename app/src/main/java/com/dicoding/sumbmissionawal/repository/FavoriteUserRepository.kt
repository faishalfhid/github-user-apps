package com.dicoding.sumbmissionawal.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.sumbmissionawal.database.FavoriteUser
import com.dicoding.sumbmissionawal.database.FavoriteUserDatabase
import com.dicoding.sumbmissionawal.database.NoteDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mNotesDao: NoteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        mNotesDao = db.noteDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteUser>> = mNotesDao.getAllFavorite()

    fun insert(favUser: FavoriteUser) {
        executorService.execute { mNotesDao.insert(favUser) }
    }

    fun delete(favUser: FavoriteUser) {
        executorService.execute { mNotesDao.delete(favUser) }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        return mNotesDao.getFavoriteUserByUsername(username)
    }
}
