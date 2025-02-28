package com.dicoding.sumbmissionawal.ui.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.sumbmissionawal.helper.ViewModelFactorySettings
import com.dicoding.sumbmissionawal.R
import com.google.android.material.switchmaterial.SwitchMaterial

class SwitchThemeActivity : AppCompatActivity() {

    private lateinit var switchTheme: SwitchMaterial
    private lateinit var setViewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_theme)

        switchTheme = findViewById(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(application.dataStore)
        setViewModel = ViewModelProvider(this, ViewModelFactorySettings(pref)).get(
            SettingViewModel::class.java)

        setViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            setViewModel.saveThemeSetting(isChecked)
        }
    }
}