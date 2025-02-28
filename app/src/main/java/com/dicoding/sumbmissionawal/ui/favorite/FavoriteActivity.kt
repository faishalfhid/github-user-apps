package com.dicoding.sumbmissionawal.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.sumbmissionawal.data.response.ItemsItem
import com.dicoding.sumbmissionawal.helper.ViewModelFactory
import com.dicoding.sumbmissionawal.ui.main.ProfileAdapter
import com.dicoding.sumbmissionawal.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private lateinit var profileAdapter: ProfileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        showLoading(true)

        favoriteViewModel.getAllFavorite().observe(this) { favoriteUsers ->
            val items = favoriteUsers.map { favoriteUser ->
                ItemsItem(
                    login = favoriteUser.username,
                    avatarUrl = favoriteUser.avatarUrl
                )
            }
            profileAdapter.setData(items)

            binding.tvNoData.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE

            showLoading(false)
        }
    }

    private fun setupRecyclerView() {
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        profileAdapter = ProfileAdapter()
        binding.rvFavorite.adapter = profileAdapter
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}