package com.yodle.android.kotlindemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.yodle.android.kotlindemo.MainApp
import com.yodle.android.kotlindemo.R
import com.yodle.android.kotlindemo.adapter.RepositoryAdapter
import com.yodle.android.kotlindemo.service.GitHubService
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var gitHubService: GitHubService

    val repositoryAdapter = RepositoryAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainApp.graph.inject(this)

        mainResultsRecycler.adapter = repositoryAdapter
        mainResultsRecycler.layoutManager = LinearLayoutManager(this)

        repositoryAdapter.repositories = gitHubService.searchRepositories()
        repositoryAdapter.notifyDataSetChanged()
    }
}
