package com.example.taskbucket.data

data class Events(
    val id: Int,
    var title: String,
    var description: String,
    var day: Int,
    var week: Int,
    var month: Int,
    var year: Int,
    var start_time: Int,
    var end_time: Int
)