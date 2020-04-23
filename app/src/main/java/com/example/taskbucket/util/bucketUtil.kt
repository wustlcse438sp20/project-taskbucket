package com.example.taskbucket.util

import java.util.*
import kotlin.collections.ArrayList

data class BucketInstance(val name: String = "", val day: Int? = -1, val week: Int?  = -1, val month: Int? = -1, val year: Int? = -1)

class  bucketUtil(){
    fun getAllBuckets(): ArrayList<BucketInstance>{
        val calendar: Calendar = Calendar.getInstance()
        calendar.get(Calendar.DAY_OF_MONTH)
        val today_bucket = BucketInstance(
            name = "Today",
            day = calendar.get(Calendar.DAY_OF_MONTH),
            week = calendar.get(Calendar.WEEK_OF_MONTH),
            month = calendar.get(Calendar.MONTH),
            year = calendar.get(Calendar.YEAR)
            )

        val this_week_bucket = BucketInstance(
            name = "This Week",
            week = calendar.get(Calendar.WEEK_OF_MONTH),
            month = calendar.get(Calendar.MONTH),
            year = calendar.get(Calendar.YEAR)
        )

        val this_month_bucket = BucketInstance(
            name = "This Month",
            month = calendar.get(Calendar.MONTH),
            year = calendar.get(Calendar.YEAR)
        )

        val this_year_bucket = BucketInstance(
            name = "This Year",
            year = calendar.get(Calendar.YEAR)
        )
        return arrayListOf(today_bucket, this_week_bucket, this_month_bucket, this_year_bucket)

    }
}