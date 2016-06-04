package com.yodle.android.kotlindemo.service

import com.yodle.android.kotlindemo.model.Repository
import com.yodle.android.kotlindemo.model.RepositoryReadme
import com.yodle.android.kotlindemo.model.RepositorySearchResults
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface GitHubApiService {

    @GET("search/repositories")
    fun searchRepositories(@Query("q") query: String): Observable<RepositorySearchResults>

    @GET("/repos/{owner}/{repo}")
    fun getRepository(@Path("owner") owner: String, @Path("repo") repository: String): Observable<Repository>

    @GET("/repos/{owner}/{repo}/readme")
    fun getRepositoryReadme(@Path("owner") owner: String, @Path("repo") repository: String): Observable<RepositoryReadme>

}