package com.yodle.android.kotlindemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ocpsoft.pretty.time.PrettyTime
import com.yodle.android.kotlindemo.BR
import com.yodle.android.kotlindemo.activity.RepositoryDetailActivity
import com.yodle.android.kotlindemo.databinding.RepositoryItemBinding
import com.yodle.android.kotlindemo.extension.format
import com.yodle.android.kotlindemo.extension.loadUrl
import com.yodle.android.kotlindemo.model.Repository
import kotlinx.android.synthetic.main.repository_item.view.repositoryItemImage
import kotlinx.android.synthetic.main.repository_item.view.repositoryItemRootLayout
import java.util.ArrayList

class RepositoryAdapter(val context: Context) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    var repositories: List<Repository> = ArrayList()

    override fun getItemCount() = repositories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val layoutInflator: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return RepositoryViewHolder(RepositoryItemBinding.inflate(layoutInflator, parent, false))
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) = holder.bind(repositories.get(position))

    fun loadRepositories(repositories: List<Repository>) {
        this.repositories = repositories
        notifyDataSetChanged()
    }

    class RepositoryViewHolder(val binding: RepositoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val context: Context = binding.root.context

        fun bind(repository: Repository) {
            binding.setVariable(BR.repo, repository)
            binding.setVariable(BR.pushedDate, PrettyTime().format(repository.pushed_at))
            itemView.repositoryItemImage.loadUrl(repository.owner.avatar_url)
            itemView.repositoryItemRootLayout.setOnClickListener { context.startActivity(RepositoryDetailActivity.getIntent(context, repository)) }
        }
    }
}