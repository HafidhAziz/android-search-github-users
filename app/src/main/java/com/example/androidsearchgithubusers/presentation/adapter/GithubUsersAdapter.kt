package com.example.androidsearchgithubusers.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidsearchgithubusers.R
import com.example.androidsearchgithubusers.data.Items
import com.example.androidsearchgithubusers.databinding.ItemGithubUserBinding

/**
 * Created by M Hafidh Abdul Aziz on 07/02/21.
 */

class GithubUsersAdapter : ListAdapter<Items, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Items>() {
            override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean =
                oldItem.login == newItem.login

            override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GithubUsersViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_github_user, parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            (holder as GithubUsersViewHolder).bind(item)
        }
    }

    class GithubUsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var binding: ItemGithubUserBinding = DataBindingUtil.bind(itemView)!!

        fun bind(item: Items) {
            binding.name.text = item.login
            binding.score.text = item.score.toString()
            Glide.with(binding.image.context)
                .load(item.avatar_url)
                .into(binding.image)
        }
    }
}