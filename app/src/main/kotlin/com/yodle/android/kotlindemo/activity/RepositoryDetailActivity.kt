package com.yodle.android.kotlindemo.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import com.yodle.android.kotlindemo.MainApp
import com.yodle.android.kotlindemo.R
import com.yodle.android.kotlindemo.extension.*
import com.yodle.android.kotlindemo.model.Repository
import com.yodle.android.kotlindemo.model.RepositoryParcel
import com.yodle.android.kotlindemo.model.RepositoryReadme
import com.yodle.android.kotlindemo.service.GitHubService
import kotlinx.android.synthetic.main.activity_repository_detail.*
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class RepositoryDetailActivity : BaseActivity(), Observer<RepositoryReadme> {

    companion object {
        val REPOSITORY_KEY = "repository_key"

        @JvmStatic fun getIntent(context: Context, repository: Repository): Intent {
            val intent = Intent(context, RepositoryDetailActivity::class.java)
            intent.putExtra(REPOSITORY_KEY, RepositoryParcel(repository))
            return intent
        }
    }

    @Inject lateinit var gitHubService: GitHubService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_detail)
        MainApp.graph.inject(this)

        val repository = intent.getParcelableExtra<RepositoryParcel>(REPOSITORY_KEY).data

        setSupportActionBar(toolbar)
        supportActionBar?.title = "${repository.full_name}"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadRepositoryDetails(repository.owner.login, repository.name)
        loadRepositoryImage(repository.owner.avatar_url)

        repositoryDetailFab.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(repository.html_url))) }

        repositoryDetailWebView.setProgressChangedListener { progress -> repositoryDetailSpinner.showIf(progress < 100) }
    }

    override fun onCompleted() {
    }

    override fun onError(e: Throwable) {
        Timber.e(e, "Failed to load repository readme")
        repositoryDetailSpinner.hide()
    }

    override fun onNext(readme: RepositoryReadme) {
        repositoryDetailWebView.loadUrl(readme.html_url)
    }

    fun loadRepositoryDetails(owner: String, repository: String) {
        gitHubService.getRepositoryReadme(owner, repository)
                .doOnSubscribe { repositoryDetailSpinner.show() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this)
    }

    fun loadRepositoryImage(imageUrl: String) {
        repositoryDetailImage.loadUrl(imageUrl) {
            onSuccess { setToolbarColorFromImage() }
            onError { Timber.e("Failed to load image") }
        }
    }

    fun setToolbarColorFromImage() {
        repositoryDetailImage.generatePalette gen@ {
            val swatch = it.mutedSwatch ?: it.vibrantSwatch ?: it.lightMutedSwatch ?: it.lightVibrantSwatch ?: return@gen
            val backgroundColor = swatch.rgb
            val titleTextColor = swatch.titleTextColor

            toolbar.setTitleTextColor(titleTextColor)
            toolbarLayout.setBackgroundColor(backgroundColor)
            toolbarLayout.setContentScrimColor(backgroundColor)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = backgroundColor
            }
        }
    }
}