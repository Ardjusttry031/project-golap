package com.capstone.golap.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.golap.R
import com.capstone.golap.databinding.ActivityDetailBinding
import com.capstone.golap.response.DetailResponse
import java.text.NumberFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the laptop detail from the intent
        val laptopDetail = intent.getParcelableExtra<DetailResponse>(EXTRA_LAPTOP_DETAIL)
        laptopDetail?.let { displayLaptopDetails(it) }
    }

    private fun displayLaptopDetails(laptop: DetailResponse) {
        // Load the laptop image
        Glide.with(this)
            .load(laptop.imageLink)
            .placeholder(R.drawable.ic_placeholder)
            .into(binding.laptopImage)

        // Set the laptop name and price
        binding.laptopName.text = laptop.laptopName
        binding.priceInRupee.text = "Rp ${NumberFormat.getNumberInstance(Locale("in", "ID")).format(laptop.priceInRupee)}"

        // Set the laptop specifications
        binding.processor.text = "Processor: ${laptop.processor}"
        binding.ramInGb.text = "RAM: ${laptop.rAMInGB} GB (${laptop.rAMType})"
        binding.dedicatedGpuInGb.text = "Dedicated GPU: ${laptop.dedicatedGPUInGB} GB"
        binding.storageCapacity.text = "Storage Capacity: ${laptop.storageCapacity} GB"
        binding.storageType.text = "Storage Type: ${laptop.storageType}"
        binding.touchscreenFeatures.text = "Touchscreen Features: ${laptop.touchscreenFeatures}"
        binding.linkReferences.text = "Link References: ${laptop.linkReferences}"
        binding.weightInKg.text = "Weight: ${laptop.weightInKg} kg"
        binding.userRating.text = "User Rating: ${laptop.userRating}"
        binding.isActive.text = "Is Active: ${laptop.isActive}"
        binding.gpu.text = "GPU: ${laptop.gPU}"
        binding.laptopIndex.text = "Laptop Index: ${laptop.laptopIndex}"
        binding.memoryType.text = "Memory Type: ${laptop.memoryType}"
        binding.screenResolution.text = "Screen Resolution: ${laptop.screenResolution}"
        binding.laptopCompany.text = "Laptop Company: ${laptop.laptopCompany}"
        binding.batteryLifetimeInHrs.text = "Battery Lifetime: ${laptop.batteryLifetimeInHrs} hrs"
        binding.screenSizeInInch.text = "Screen Size: ${laptop.screenSizeInInch} inch"
        binding.laptopType.text = "Laptop Type: ${laptop.laptopType}"
        binding.refreshRate.text = "Refresh Rate: ${laptop.refreshRate} Hz"
    }

    companion object {
        const val EXTRA_LAPTOP_DETAIL = "extra_laptop_detail"
    }
}
