package com.capstone.golap.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.golap.R
import com.capstone.golap.databinding.ItemLaptopBinding
import com.capstone.golap.response.RecomResponse
import java.text.NumberFormat
import java.util.Locale

class DashboardAdapter : ListAdapter<RecomResponse, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    inner class RecomResponseViewHolder(private val binding: ItemLaptopBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recom: RecomResponse) {
            Glide.with(binding.root.context)
                .load(recom.imageLink)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(binding.laptopImage)

            binding.laptopName.text = recom.laptopName
            val formattedPrice = NumberFormat.getNumberInstance(Locale("in", "ID")).format(recom.priceInRupee)
            binding.laptopPrice.text = "Rp $formattedPrice"

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_LAPTOP_DETAIL, recom)
                }
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemLaptopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecomResponseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is RecomResponseViewHolder) {
            holder.bind(item)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecomResponse>() {
            override fun areItemsTheSame(oldItem: RecomResponse, newItem: RecomResponse): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: RecomResponse, newItem: RecomResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}
