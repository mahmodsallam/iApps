package com.task.iApps.data.api

import com.task.iApps.model.ResponseModel
import retrofit2.http.GET

interface ItemsApi {
    companion object {
        const val HTTPS_API_GITHUB_URL =
            "https://api.flickr.com/services/feeds/"
    }
    @GET("photos_public.gne?format=json&tags=cat&nojsoncallback=1")
    suspend fun getItemsList(): ResponseModel
}