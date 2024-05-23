package com.dicoding.picodiploma.storyapp

import com.dicoding.picodiploma.storyapp.data.response.ListStoryItem

object DataDummy {

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..10) {
            val quote = ListStoryItem(
                "url photo $i",
                "author + $i",
                "quote $i",
                "author + $i",
                0.2,
                "quote $i",
                0.8,
            )
            items.add(quote)
        }
        return items
    }
}