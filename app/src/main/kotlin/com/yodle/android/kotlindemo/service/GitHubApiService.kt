package com.yodle.android.kotlindemo.service

import com.yodle.android.kotlindemo.model.RepositorySearchResults
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface GitHubApiService {

    @GET("search/repositories")
    fun searchRepositories(@Query("q") query: String): Observable<RepositorySearchResults>
}