package com.jj.templateproject.networking.domain.fishdata

import com.google.gson.annotations.SerializedName

data class FishSpeciesIllustrationPhoto(
        @SerializedName("alt")
        val alt: String?,
        @SerializedName("src")
        val src: String?,
        @SerializedName("title")
        val title: String?
)