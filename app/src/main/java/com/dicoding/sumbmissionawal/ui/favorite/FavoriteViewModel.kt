package com.dicoding.sumbmissionawal.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.sumbmissionawal.database.FavoriteUser
import com.dicoding.sumbmissionawal.repository.FavoriteUserRepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading


    fun getAllFavorite(): LiveData<List<FavoriteUser>> {
        return favoriteUserRepository.getAllFavorite()
    }

    fun insert(favUser: FavoriteUser) {
        favoriteUserRepository.insert(favUser)
    }

    fun delete(favUser: FavoriteUser) {
        favoriteUserRepository.delete(favUser)
    }

    fun checkFavoriteUser(username: String, lifecycleOwner: LifecycleOwner) {
        favoriteUserRepository.getFavoriteUserByUsername(username).observe(lifecycleOwner) { favoriteUser ->
            if (favoriteUser != null) {
                _isFavorite.postValue(true)
            } else {
                _isFavorite.postValue(false)
            }
        }
    }
}