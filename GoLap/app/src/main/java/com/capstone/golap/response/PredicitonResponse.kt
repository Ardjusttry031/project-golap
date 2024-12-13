package com.capstone.golap.response

import com.google.gson.annotations.SerializedName

data class PredictionResponse(
    @SerializedName("message") val message: String,
    @SerializedName("matchedLaptops") val matchedLaptops: List<MatchedLaptopsItem>
)
data class MatchedLaptopsItem(
    @SerializedName("Laptop_Name") val laptopName: String,
    @SerializedName("RAM_in_GB") val ramInGB: Int,
    @SerializedName("Processor") val processor: String,
    @SerializedName("Storage_Capacity") val storageCapacity: Int,
    @SerializedName("Screen_Size_in_Inch") val screenSizeInInch: Double,
    @SerializedName("Weight_in_Kg") val weightInKg: Double,
    @SerializedName("Screen_Resolution") val screenResolution: Int,
    @SerializedName("Battery_Lifetime_in_Hrs") val batteryLifetimeInHrs: Double,
    @SerializedName("Price_in_IDR") val priceInIDR: Long,
    @SerializedName("Image_Link") val imageLink: String
)

