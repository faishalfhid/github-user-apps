package com.dicoding.tugasakhir

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class detail : AppCompatActivity() {

    companion object {
        const val key_hero = "key_hero"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val dataHero = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Hero>(key_hero, Hero::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Hero>(key_hero)
        }

        if (dataHero!=null){
            val imgDetailPhoto: ImageView = findViewById(R.id.img_item_photo)
            val tvDetailName: TextView = findViewById(R.id.tv_item_name)
            val tvDetailDescription:TextView = findViewById(R.id.tv_item_description)
            val tvLokasiDescription: TextView = findViewById(R.id.tv_item_location)
            val tvOperasional: TextView = findViewById(R.id.tv_item_operasional)
            val tvHarga: TextView = findViewById(R.id.tv_item_harga)
            val tvShareloc: Button = findViewById(R.id.tv_buttonloc)

            imgDetailPhoto.setImageResource(dataHero.photo)
            tvDetailName.text = dataHero.name
            tvDetailDescription.text = dataHero.description
            tvLokasiDescription.text = dataHero.lokasi
            tvOperasional.text = dataHero.operasional
            tvHarga.text = dataHero.harga
            tvShareloc.setOnClickListener{
                val intentMaps = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, dataHero.shareloc)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(intentMaps, "Bagikan melalui"))

            }
        }



    }
}