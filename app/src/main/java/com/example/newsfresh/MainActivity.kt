package com.example.newsfresh

import android.net.Uri
import NewsItemClicked
import NewsListAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter
    private var mCategory: String = "general"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter
    }

    private fun fetchData() {
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/$mCategory/in.json"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }

                mAdapter.updateNews(newsArray)
            },
            {

            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }

    fun scienceCatagory(view: View) {
        mCategory = "science"
        fetchData()
    }
    fun TechCatagory(view: View) {
        mCategory = "technology"
        fetchData()
    }
    fun SportsCatagory(view: View) {
        mCategory = "sports"
        fetchData()
    }
    fun HealthCatagory(view: View) {
        mCategory= "health"
        fetchData()
    }
    fun bussinessCatagory(view: View) {
        mCategory= "business"
        fetchData()
    }
    fun generalCatagory(view: View) {
        mCategory = "general"
        fetchData()
    }

}
