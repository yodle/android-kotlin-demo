package com.yodle.android.kotlindemo.model

import nz.bradcampbell.paperparcel.PaperParcel

@PaperParcel
data class RepositoryOwner(
        val login: String,
        val id: Long,
        val avatar_url: String,
        val url: String,
        val type: String
)