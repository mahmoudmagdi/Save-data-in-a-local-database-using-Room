package com.khlafawi.todoroom.helpers

import androidx.databinding.InverseMethod

object Converter {
    @InverseMethod("stringToInt")
    @JvmStatic
    fun intToString(
        value: Int,
    ): String {
        return if (value != 0) {
            value.toString()
        } else {
            ""
        }

    }

    @JvmStatic
    fun stringToInt(
        value: String
    ): Int {
        return if (value != "") {
            value.toInt()
        } else {
            0
        }
    }
}