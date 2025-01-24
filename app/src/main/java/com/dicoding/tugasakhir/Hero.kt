package com.dicoding.tugasakhir

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hero(
    val name: String,
    val photo: Int,
    val lokasi: String,
    val operasional: String,
    val harga: String,
    val description: String,
    val shareloc: String
    ) : Parcelable