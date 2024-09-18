package com.moody.t_mobile.repository

import com.moody.t_mobile.model.Page
import com.moody.t_mobile.retrofit.TMobileService
import javax.inject.Inject

class TMobileRepository @Inject constructor(private val tMobileService: TMobileService) {

    suspend fun getItemsFeed(): Page {
        return tMobileService.getItemsFeed()
    }
}