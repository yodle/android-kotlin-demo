package com.yodle.android.kotlindemo.model

data class RepositorySearchResults(
        val total_count: Long,
        val incomplete_results: Boolean,
        val items: List<Repository>
)