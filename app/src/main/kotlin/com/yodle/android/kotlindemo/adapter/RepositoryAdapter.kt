package com.yodle.android.kotlindemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.ocpsoft.pretty.time.PrettyTime
import com.yodle.android.kotlindemo.R
import com.yodle.android.kotlindemo.activity.RepositoryDetailActivity
import com.yodle.android.kotlindemo.extension.format
import com.yodle.android.kotlindemo.extension.formatted
import com.yodle.android.kotlindemo.extension.inflateLayout
import com.yodle.android.kotlindemo.extension.loadUrl
import com.yodle.android.kotlindemo.model.Repository
import kotlinx.android.synthetic.main.repository_item.view.*
import java.util.*

class RepositoryAdapter(val context: Context) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    var repositories: List<Repository> = ArrayList()

    override fun getItemCount() = repositories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RepositoryViewHolder(context.inflateLayout(R.layout.repository_item, parent))

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) = holder.bind(repositories.get(position))

    fun loadRepositories(repositories: List<Repository>) {
        this.repositories = repositories
        notifyDataSetChanged()
    }

    class RepositoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val context: Context = view.context
        val prettyTime = PrettyTime()

        fun bind(repository: Repository) {
            itemView.repositoryItemTitle.text = repository.full_name
            itemView.repositoryItemDescription.text = repository.description
            itemView.repositoryItemLastUpdated.text = "Updated ${prettyTime.format(repository.pushed_at)}"
            itemView.repositoryItemStarCount.text = repository.watchers_count.formatted()
            itemView.repositoryItemImage.loadUrl(repository.owner.avatar_url)
            itemView.repositoryItemRootLayout.setOnClickListener { context.startActivity(RepositoryDetailActivity.getIntent(context, repository)) }
        }
    }
}