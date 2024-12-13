package com.capstone.golap.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class DetailResponse(
	@SerializedName("Dedicated_GPU_in_GB") val dedicatedGPUInGB: Int,
	@SerializedName("Storage_Capacity") val storageCapacity: Int,
	@SerializedName("Processor") val processor: String,
	@SerializedName("Storage_Type") val storageType: Int,
	@SerializedName("Touchscreen_Features") val touchscreenFeatures: Int,
	@SerializedName("Link_References") val linkReferences: String,
	@SerializedName("Weight_in_Kg") val weightInKg: String,
	@SerializedName("User_Rating") val userRating: @RawValue Any,
	@SerializedName("isActive") val isActive: Boolean,
	@SerializedName("GPU") val gPU: String,
	@SerializedName("Laptop_Name") val laptopName: String,
	@SerializedName("Laptop_Index") val laptopIndex: Int,
	@SerializedName("Memory_Type") val memoryType: Int,
	@SerializedName("Screen_Resolution") val screenResolution: Int,
	@SerializedName("Laptop_Company") val laptopCompany: Int,
	@SerializedName("Battery_Lifetime_in_Hrs") val batteryLifetimeInHrs: Int,
	@SerializedName("id") val id: String,
	@SerializedName("RAM_in_GB") val rAMInGB: Int,
	@SerializedName("Image_Link") val imageLink: String,
	@SerializedName("RAM_Type_Tokenized") val rAMTypeTokenized: Int,
	@SerializedName("OS") val oS: Int,
	@SerializedName("RAM_Type") val rAMType: String,
	@SerializedName("GPU_Processor_Tokenized") val gPUProcessorTokenized: Int,
	@SerializedName("CPU_Rank") val cPURank: Int,
	@SerializedName("Processor_Brand") val processorBrand: Int,
	@SerializedName("Screen_Size_in_Inch") val screenSizeInInch: Int,
	@SerializedName("GPU_Benchmark_Score") val gPUBenchmarkScore: @RawValue Any,
	@SerializedName("Laptop_Type") val laptopType: Int,
	@SerializedName("Price_in_Rupee") val priceInRupee: Int,
	@SerializedName("Refresh_Rate") val refreshRate: Int
) : Parcelable
