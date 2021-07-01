package com.example.mobileexercise.models

import java.io.Serializable

data class SizeModel (
        val sizes: Sizes,
        val stat: String
        ): Serializable {
                data class Sizes (
                        val canblog: Int,
                        val canprint: Int,
                        val candownload: Int,
                        val size: List<Size>
                ): Serializable {
                        data class Size (
                                val label: String,
                                val width: Int,
                                val height: Int,
                                val source: String,
                                val url: String,
                                val media: String
                        ): Serializable
                }
        }