package com.capstone.golap.response

import com.google.gson.annotations.SerializedName

data class LaptopResponse(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String
)
