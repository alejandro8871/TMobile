package com.moody.t_mobile.retrofit

import com.moody.t_mobile.model.Page
import retrofit2.http.GET

interface TMobileService {

    //fetch items to fill feed
    @GET("test/home")
    suspend fun getItemsFeed(): Page
}