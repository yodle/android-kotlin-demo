package com.yodle.android.kotlindemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.yodle.android.kotlindemo.R
import com.yodle.android.kotlindemo.adapter.RepositoryAdapter
import com.yodle.android.kotlindemo.model.Repository
import com.yodle.android.kotlindemo.model.RepositoryOwner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val repositoryAdapter = RepositoryAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainResultsRecycler.adapter = repositoryAdapter
        mainResultsRecycler.layoutManager = LinearLayoutManager(this)

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

        repositoryAdapter.repositories = arrayListOf(repo)
        repositoryAdapter.notifyDataSetChanged()
    }
}
