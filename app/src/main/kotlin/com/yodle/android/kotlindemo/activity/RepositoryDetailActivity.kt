package com.yodle.android.kotlindemo.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.yodle.android.kotlindemo.MainApp
import com.yodle.android.kotlindemo.R
import com.yodle.android.kotlindemo.model.Repository
import com.yodle.android.kotlindemo.service.GitHubService
import kotlinx.android.synthetic.main.activity_repository_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import rx.Observer
import timber.log.Timber
import javax.inject.Inject

class RepositoryDetailActivity : BaseActivity(), Observer<Repository> {

    companion object {
        val OWNER_KEY = "owner_key"
        val REPOSITORY_KEY = "repository_key"

        @JvmStatic fun getIntent(context: Context, repository: Repository): Intent {
            val intent = Intent(context, RepositoryDetailActivity::class.java)
            intent.putExtra(OWNER_KEY, repository.owner.login)
            intent.putExtra(REPOSITORY_KEY, repository.name)
            return intent
        }
    }

    @Inject lateinit var gitHubService: GitHubService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_detail)
        MainApp.graph.inject(this)

        val owner = intent.getStringExtra(OWNER_KEY)
        val repository = intent.getStringExtra(REPOSITORY_KEY)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "$owner/$repository"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        repositoryDetailSpinner.visibility = View.VISIBLE
        subscribe(gitHubService.getRepository(owner, repository), this)
    }

    override fun onCompleted() {
    }

    override fun onError(e: Throwable) {
        Timber.e(e, "Failed to load repository details")
        repositoryDetailSpinner.visibility = View.GONE
    }

    override fun onNext(repository: Repository) {
        repositoryDetailSpinner.visibility = View.GONE
    }

}