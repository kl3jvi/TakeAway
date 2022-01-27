package com.example.takeaway.util

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.takeaway.data.model.RestaurantFeed
import com.example.takeaway.data.model.SortingValues

object TestData {
    private val sortingValues1 = SortingValues(1.0, 3.0, 3.5, 10, 30.0, 20, 500, 100)
    private val sortingValues2 = SortingValues(3.0, 5.0, 5.5, 30, 50.0, 40, 700, 300)
    private val sortingValues3 = SortingValues(2.0, 4.0, 4.5, 20, 40.0, 30, 600, 200)

    val restaurant1 = RestaurantFeed(1, "foo", "open", sortingValues1, false)
    val restaurant2 = RestaurantFeed(2, "bar", "order ahead", sortingValues2, true)
    val restaurant3 = RestaurantFeed(3, "desc", "closed", sortingValues3, true)
    val restaurant4 = RestaurantFeed(4, "foo foo", "open", sortingValues1, true)
    val restaurant5 = RestaurantFeed(5, "desc desc", "closed", sortingValues2, false)

    val restaurantList = listOf(restaurant1, restaurant2, restaurant3, restaurant4, restaurant5)

    val sortByStatusQuery = SimpleSQLiteQuery(
        "SELECT * FROM restaurantFeed ORDER BY " +
                "CASE WHEN favorite THEN status = 'open' END DESC, " +
                "CASE WHEN favorite THEN status = 'order ahead' END DESC, " +
                "CASE WHEN favorite THEN status = 'closed' END DESC, " +
                "CASE WHEN favorite=0 THEN status = 'open' END DESC, " +
                "CASE WHEN favorite=0 THEN status = 'order ahead' END DESC, " +
                "CASE WHEN favorite=0 THEN status = 'closed' END DESC"
    )

    fun sortByFilterQuery(filter: String) =
        SimpleSQLiteQuery(
            "SELECT * FROM restaurantFeed ORDER BY " +
                    "CASE WHEN favorite THEN $filter END DESC, " +
                    "CASE WHEN favorite=0 THEN $filter END DESC"
        )

}