package com.yodle.android.kotlindemo.service

import com.yodle.android.kotlindemo.MainApp
import com.yodle.android.kotlindemo.model.Repository
import com.yodle.android.kotlindemo.model.RepositoryOwner

class GitHubService(mainApp: MainApp) {

    fun searchRepositories(): List<Repository> {
        val repo = Repository(
                id = 1,
                name = "test",
                full_name = "testing",
                owner = RepositoryOwner(
                        id = 2,
                        login = "login",
                        avatar_url = "kjdsadsa",
                        url = "djsakdsa",
                        type = "Org"
                ),
                description = "desc",
                url = "dsds",
                created_at = "created",
                updated_at = "updated",
                pushed_at = "pushed",
                homepage = "dsds",
                stargazers_count = 1,
                watchers_count = 2,
                watchers = "5222",
                score = 2.3
        )
        return arrayListOf(repo)
    }

}