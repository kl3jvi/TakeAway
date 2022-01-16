package com.example.takeaway.data

import com.example.takeaway.data.api.RestaurantFeedResponse
import com.google.gson.GsonBuilder
import java.io.InputStream

object RestaurantFeedJsonParser {

    fun getSampleRestaurantList(): RestaurantFeedResponse {
        val conferenceDataStream: InputStream = this.javaClass.classLoader!!
            .getResource("restaurant_list.json").openStream()
        val gson = GsonBuilder().create()
        val jsonReader = com.google.gson.stream.JsonReader(conferenceDataStream.reader())

        return gson.fromJson(jsonReader, RestaurantFeedResponse::class.java)
    }
}