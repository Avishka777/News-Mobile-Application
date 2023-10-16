package com.example.sliit_news.database

class NewsRepository(
    private val db:NewsDatabase
) {

    suspend fun insert(news: News) {
        db.getNewsDao().insert(news)
    }

    suspend fun delete(news: News) {
        db.getNewsDao().delete(news)
    }

    fun getAllNewsItems():List<News> = db.getNewsDao().getAllNewsItems()

}