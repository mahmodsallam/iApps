package com.task.iApps.model


import com.squareup.moshi.Json

data class ResponseModel(
    @Json(name = "description")
    var description: String?,
    @Json(name = "generator")
    var generator: String?,
    @Json(name = "items")
    var items: List<Item?>?,
    @Json(name = "link")
    var link: String?,
    @Json(name = "modified")
    var modified: String?,
    @Json(name = "title")
    var title: String?
) {

    data class Item(
        @Json(name = "author")
        var author: String?,
        @Json(name = "author_id")
        var authorId: String?,
        @Json(name = "date_taken")
        var dateTaken: String?,
        @Json(name = "description")
        var description: String?,
        @Json(name = "link")
        var link: String?,
        @Json(name = "media")
        var media: Media?,
        @Json(name = "published")
        var published: String?,
        @Json(name = "tags")
        var tags: String?,
        @Json(name = "title")
        var title: String?
    ) {

        data class Media(
            @Json(name = "m")
            var m: String?
        )
    }
}