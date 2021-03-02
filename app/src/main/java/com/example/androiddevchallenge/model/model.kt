package com.example.androiddevchallenge.model

import androidx.annotation.DrawableRes
import java.io.Serializable

data class Puppy(var name: String, var varieties: String, var character: String, @DrawableRes var img: Int, var createTime: String): Serializable