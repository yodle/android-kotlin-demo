package com.yodle.android.kotlindemo.service

import com.yodle.android.kotlindemo.MainApp
import com.yodle.android.kotlindemo.model.Repository
import rx.Observable
import java.util.*
import javax.inject.Inject

class GitHubService {

    @Inject lateinit var gitHubApiService: GitHubApiService

    constructor() {
        MainApp.graph.inject(this)
    }

    fun searchRepositories(query: String): Observable<List<Repository>> {
        if (query.isBlank()) {
            return Observable.just(ArrayList())
        } else {
            return gitHubApiService.searchRepositories(query).map { it.items }
        }
    }

    fun getRepository(owner: String, repository: String) = gitHubApiService.getRepository(owner, repository)

    fun getRepositoryReadme(owner: String, repository: String) = gitHubApiService.getRepositoryReadme(owner, repository)
}