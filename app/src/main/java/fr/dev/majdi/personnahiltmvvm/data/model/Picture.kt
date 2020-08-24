package fr.dev.majdi.personna.model

import java.io.Serializable

/**
 *Created by Majdi RABEH on 24/08/2020
 *Email = "m.rabeh.majdi@gmail.com")
 */
data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
) : Serializable