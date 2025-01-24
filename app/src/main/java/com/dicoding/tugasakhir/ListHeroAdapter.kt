package com.dicoding.tugasakhir

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ListHeroAdapter(private val listHero: ArrayList<Hero>) : RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_hero, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, photo, tempatloc, jamOperasi, hargaMakan) = listHero[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvName.text = name
        holder.tvLokasi.text = tempatloc
        holder.tvOperasional.text = jamOperasi
        holder.tvHarga.text = hargaMakan
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, detail::class.java)
            intentDetail.putExtra("key_hero", listHero[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listHero.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvLokasi: TextView = itemView.findViewById(R.id.tv_item_tempatloc)
        val tvOperasional: TextView = itemView.findViewById(R.id.tv_item_jamOperasi)
        val tvHarga: TextView = itemView.findViewById(R.id.tv_item_hargaMakan)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Hero)
    }

}
