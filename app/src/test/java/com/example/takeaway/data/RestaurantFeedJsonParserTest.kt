package com.example.takeaway.data

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RestaurantFeedJsonParserTest {

    @Test
    fun testGetSampleRestaurantList() {
        val restaurantFeed = RestaurantFeedJsonParser.getSampleRestaurantList().restaurants

        assertThat(restaurantFeed.size).isEqualTo(19)
        assertThat(restaurantFeed[0].favorite).isFalse()
        assertThat(restaurantFeed[0].status).isEqualTo("open")
        assertThat(restaurantFeed[0].sortingValues.distance).isEqualTo(1190)
        assertThat(restaurantFeed[18].status).isEqualTo("open")
    }
}