package com.yodle.android.kotlindemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yodle.android.kotlindemo.R
import com.yodle.android.kotlindemo.model.Repository
import kotlinx.android.synthetic.main.repository_item.view.*

class RepositoryAdapter(val context: Context) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    var repositories: List<Repository> = arrayListOf()

    override fun getItemCount() = repositories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.repository_item, parent, false);
        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositories.get(position))
    }

    class RepositoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(repository: Repository) {
            itemView.repositoryItemTitle.text = repository.name
        }
    }
}