package com.vanillaplacepicker.domain.repository

import com.vanillaplacepicker.data.SearchAddressResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WebApi {

    @GET("textsearch/json")
    fun searchPlace(@QueryMap param: HashMap<String, String>): Observable<SearchAddressResponse>
}