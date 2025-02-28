package com.dicoding.sumbmissionawal.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.sumbmissionawal.data.response.DetailUserResponse
import com.dicoding.sumbmissionawal.database.FavoriteUser
import com.dicoding.sumbmissionawal.helper.ViewModelFactory
import com.dicoding.sumbmissionawal.ui.favorite.FavoriteViewModel
import com.dicoding.sumbmissionawal.R
import com.dicoding.sumbmissionawal.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel
    private val defaultQueryKey = "DEFAULT_USERNAME"
    private var defaultUsername: String = ""
    private var isDataLoaded = false

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        savedInstanceState?.getString(defaultQueryKey)?.let { savedUsername ->
            defaultUsername = savedUsername
        }

        val username = intent.getStringExtra(EXTRA_USERNAME)


        if (username != null && defaultUsername != username && !isDataLoaded) {
            viewModel.fetchUserDetail(username)
        }


        binding.fabAdd.setOnClickListener {
            val userDetail = viewModel.userDetail.value

            if (userDetail != null) {
                val avatarUrl = userDetail.avatarUrl
                val favoriteUser = FavoriteUser(
                    username = username.orEmpty(),
                    avatarUrl = avatarUrl
                )

                if (favoriteViewModel.isFavorite.value == true) {
                    favoriteViewModel.delete(favoriteUser)
                    Toast.makeText(this, getString(R.string.deleteFav), Toast.LENGTH_SHORT).show()
                } else {
                    favoriteViewModel.insert(favoriteUser)
                    Toast.makeText(this, getString(R.string.addFav), Toast.LENGTH_SHORT).show()
                }
            }
        }

        favoriteViewModel.checkFavoriteUser(username.orEmpty(), this)

        favoriteViewModel.isFavorite.observe(this) { isFavorite ->
            if (isFavorite) {
                binding.fabAdd.setImageResource(R.drawable.ic_favorited)
            } else {
                binding.fabAdd.setImageResource(R.drawable.ic_favorite)
            }
        }


        val sectionPagerAdapter = SectionsPagerAdapter(this)
        sectionPagerAdapter.username = username ?: ""
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.userDetail.observe(this) { userDetail ->
                showUserDetail(userDetail)
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun showUserDetail(userDetail: DetailUserResponse) {
        Picasso.get().load(userDetail.avatarUrl).into(binding.imageView)
        binding.tvUsername.text = userDetail.login
        binding.tvNama.text = userDetail.name
        binding.tvJumlahFollowers.text = getString(R.string.followers, userDetail.followers)
        binding.tvJumlahFollowing.text = getString(R.string.following, userDetail.following)
        isDataLoaded = true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}

