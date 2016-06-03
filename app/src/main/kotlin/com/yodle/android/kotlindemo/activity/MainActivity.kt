package com.yodle.android.kotlindemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.yodle.android.kotlindemo.MainApp
import com.yodle.android.kotlindemo.R
import com.yodle.android.kotlindemo.adapter.RepositoryAdapter
import com.yodle.android.kotlindemo.model.Repository
import com.yodle.android.kotlindemo.service.GitHubService
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Observer<List<Repository>> {

    @Inject lateinit var gitHubService: GitHubService

    val repositoryAdapter = RepositoryAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainApp.graph.inject(this)

        mainResultsRecycler.adapter = repositoryAdapter
        mainResultsRecycler.layoutManager = LinearLayoutManager(this)

        loadSearchResults("kotlin")
    }

    fun loadSearchResults(query: String) {
        mainResultsSpinner.visibility = View.VISIBLE
        gitHubService.searchRepositories(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this)
    }

    override fun onCompleted() {
    }

    override fun onError(e: Throwable) {
        mainResultsSpinner.visibility = View.GONE
        Log.e("TAG", "Failed to load repositories", e)
    }

    override fun onNext(repositories: List<Repository>) {
        mainResultsSpinner.visibility = View.GONE
        repositoryAdapter.repositories = repositories
        repositoryAdapter.notifyDataSetChanged()
    }
}
