package com.yodle.android.kotlindemo.activity

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v7.graphics.Palette
import com.squareup.picasso.Picasso
import com.yodle.android.kotlindemo.MainApp
import com.yodle.android.kotlindemo.R
import com.yodle.android.kotlindemo.extension.*
import com.yodle.android.kotlindemo.model.Repository
import com.yodle.android.kotlindemo.model.RepositoryReadme
import com.yodle.android.kotlindemo.service.GitHubService
import kotlinx.android.synthetic.main.activity_repository_detail.*
import rx.Observer
import timber.log.Timber
import javax.inject.Inject

class RepositoryDetailActivity : BaseActivity(), Observer<RepositoryReadme> {

    companion object {
        val OWNER_KEY = "owner_key"
        val REPOSITORY_KEY = "repository_key"
        val IMAGE_URL_KEY = "image_url_key"
        val REPOSITORY_URL_KEY = "repository_url_key"

        @JvmStatic fun getIntent(context: Context, repository: Repository): Intent {
            val intent = Intent(context, RepositoryDetailActivity::class.java)
            intent.putExtra(OWNER_KEY, repository.owner.login)
            intent.putExtra(REPOSITORY_KEY, repository.name)
            intent.putExtra(IMAGE_URL_KEY, repository.owner.avatar_url)
            intent.putExtra(REPOSITORY_URL_KEY, repository.html_url)
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
        val imageUrl = intent.getStringExtra(IMAGE_URL_KEY)
        val repositoryUrl = intent.getStringExtra(REPOSITORY_URL_KEY)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "$owner/$repository"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadRepositoryDetails(owner, repository)
        loadRepositoryImage(imageUrl)

        repositoryDetailFab.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(repositoryUrl))) }

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
        repositoryDetailSpinner.show()
        subscribe(gitHubService.getRepositoryReadme(owner, repository), this)
    }

    fun loadRepositoryImage(imageUrl: String) {
        Picasso.with(this).load(imageUrl).into(
                repositoryDetailImage,
                onSuccess = { setToolbarColorFromImage() },
                onError = { Timber.e("Failed to load image") }
        )
    }

    fun setToolbarColorFromImage() {
        val bitmap = (repositoryDetailImage.drawable as BitmapDrawable).bitmap

        Palette.from(bitmap).generate {
            val swatch = it.mutedSwatch ?: it.vibrantSwatch ?: it.lightMutedSwatch ?: it.lightVibrantSwatch
            if (swatch != null) {
                val backgroundColor = swatch.rgb
                val darkBackgroundColor = swatch.rgb
                val titleTextColor = swatch.titleTextColor

                toolbar.setTitleTextColor(titleTextColor)
                toolbarLayout.setContentScrimColor(backgroundColor)
                toolbarLayout.setBackgroundColor(backgroundColor)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = darkBackgroundColor
                }
            }
        }
    }
}