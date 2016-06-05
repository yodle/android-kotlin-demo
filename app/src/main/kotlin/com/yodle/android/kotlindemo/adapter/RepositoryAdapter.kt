package com.yodle.android.kotlindemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.ocpsoft.pretty.time.PrettyTime
import com.squareup.picasso.Picasso
import com.yodle.android.kotlindemo.R
import com.yodle.android.kotlindemo.activity.RepositoryDetailActivity
import com.yodle.android.kotlindemo.extension.inflateLayout
import com.yodle.android.kotlindemo.model.Repository
import kotlinx.android.synthetic.main.repository_item.view.*
import org.joda.time.DateTime
import java.text.NumberFormat

class RepositoryAdapter(val context: Context) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    var repositories: List<Repository> = arrayListOf()

    override fun getItemCount() = repositories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(context.inflateLayout(R.layout.repository_item, parent, false))
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositories.get(position))
    }

    class RepositoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val context: Context = view.context
        val prettyTime = PrettyTime()

        fun bind(repository: Repository) {
            val updatedTime = prettyTime.format(DateTime(repository.pushed_at).toDate());

            itemView.repositoryItemTitle.text = repository.full_name
            itemView.repositoryItemDescription.text = repository.description
            itemView.repositoryItemLastUpdated.text = "Updated $updatedTime"
            itemView.repositoryItemStarCount.text = NumberFormat.getInstance().format(repository.watchers_count)

            Picasso.with(context).load(repository.owner.avatar_url).into(itemView.repositoryItemImage)

            itemView.repositoryItemRootLayout.setOnClickListener {
                context.startActivity(RepositoryDetailActivity.getIntent(context, repository))
            }
        }
    }
}