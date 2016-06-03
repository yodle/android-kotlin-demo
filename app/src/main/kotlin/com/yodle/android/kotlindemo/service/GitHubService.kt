package com.yodle.android.kotlindemo.service

import com.yodle.android.kotlindemo.MainApp
import com.yodle.android.kotlindemo.model.Repository
import rx.Observable
import javax.inject.Inject

class GitHubService {

    @Inject lateinit var gitHubApiService: GitHubApiService

    constructor() {
        MainApp.graph.inject(this)
    }

    fun searchRepositories(query: String): Observable<List<Repository>> {
        return gitHubApiService.searchRepositories(query).map { it.items }
    }

}