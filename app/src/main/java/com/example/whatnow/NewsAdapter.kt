package com.example.whatnow

import android.app.Activity
import android.content.Intent
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.whatnow.databinding.NewsListItemBinding

class NewsAdapter(val activity: Activity, val articles: ArrayList<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(val binding: NewsListItemBinding) : ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsListItemBinding.inflate(activity.layoutInflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.newsTitle.text = articles[position].title
        Glide
            .with(activity)
            .load(articles[position].urlToImage)
            .error(R.drawable.broken_image)
            .into(holder.binding.newsImage)

        val url = articles[position].url

        holder.binding.card.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, url.toUri())
            activity.startActivity(i)
        }

        holder.binding.shareFab.setOnClickListener {
            ShareCompat
                .IntentBuilder(activity)
                .setType("text/plain")
                .setChooserTitle("share article with:")
                .setText(url)
                .startChooser()
        }

    }


}