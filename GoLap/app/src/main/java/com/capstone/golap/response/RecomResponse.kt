package com.capstone.golap.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecomResponse(

	@field:SerializedName("Dedicated_GPU_in_GB")
	val dedicatedGPUInGB: Int,

	@field:SerializedName("Storage_Capacity")
	val storageCapacity: Int,

	@field:SerializedName("Processor")
	val processor: String,

	@field:SerializedName("Storage_Type")
	val storageType: Int,

	@field:SerializedName("Touchscreen_Features")
	val touchscreenFeatures: Int,

	@field:SerializedName("Link_References")
	val linkReferences: String,

	@field:SerializedName("Weight_in_Kg")
	val weightInKg: String,

	@field:SerializedName("User_Rating")
	val userRating: Float?,

	@field:SerializedName("isActive")
	val isActive: Boolean,

	@field:SerializedName("GPU")
	val gPU: String,

	@field:SerializedName("Laptop_Name")
	val laptopName: String,

	@field:SerializedName("Laptop_Index")
	val laptopIndex: Int,

	@field:SerializedName("Memory_Type")
	val memoryType: Int,

	@field:SerializedName("Screen_Resolution")
	val screenResolution: Int,

	@field:SerializedName("Laptop_Company")
	val laptopCompany: Int,

	@field:SerializedName("Battery_Lifetime_in_Hrs")
	val batteryLifetimeInHrs: Float?,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("RAM_in_GB")
	val rAMInGB: Int,

	@field:SerializedName("Image_Link")
	val imageLink: String,

	@field:SerializedName("RAM_Type_Tokenized")
	val rAMTypeTokenized: Int,

	@field:SerializedName("OS")
	val oS: Int,

	@field:SerializedName("RAM_Type")
	val rAMType: String,

	@field:SerializedName("GPU_Processor_Tokenized")
	val gPUProcessorTokenized: Int,

	@field:SerializedName("CPU_Rank")
	val cPURank: Int,

	@field:SerializedName("Processor_Brand")
	val processorBrand: Int,

	@field:SerializedName("Screen_Size_in_Inch")
	val screenSizeInInch: Float?,

	@field:SerializedName("GPU_Benchmark_Score")
	val gPUBenchmarkScore: Float?,

	@field:SerializedName("Laptop_Type")
	val laptopType: Int,

	@field:SerializedName("Price_in_Rupee")
	val priceInRupee: Int,

	@field:SerializedName("Refresh_Rate")
	val refreshRate: Int
) :Parcelable
