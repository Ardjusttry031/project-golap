package com.capstone.golap.ui.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.golap.R
import com.capstone.golap.databinding.ItemLaptopBinding
import com.capstone.golap.response.DetailResponse
import com.capstone.golap.ui.detail.DetailActivity

class LaptopAdapter(
    private var laptops: List<DetailResponse>,
    private val onItemClick: (DetailResponse) -> Unit
) : RecyclerView.Adapter<LaptopAdapter.LaptopViewHolder>() {

    fun updateLaptops(newLaptops: List<DetailResponse>) {
        laptops = newLaptops
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaptopViewHolder {
        val binding = ItemLaptopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LaptopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaptopViewHolder, position: Int) {
        val laptop = laptops[position]
        holder.bind(laptop)
    }

    override fun getItemCount(): Int = laptops.size

    inner class LaptopViewHolder(private val binding: ItemLaptopBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(laptop: DetailResponse) {
            binding.laptopName.text = laptop.laptopName
            binding.laptopPrice.text = "₹${laptop.priceInRupee}"
            Glide.with(binding.root.context)
                .load(laptop.imageLink)
                .into(binding.laptopImage)

            binding.root.setOnClickListener {
                onItemClick(laptop)
            }
        }
    }
}

