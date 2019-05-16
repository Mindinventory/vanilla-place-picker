package com.vanillaplacepicker.domain.repository

import com.vanillaplacepicker.data.AutocompletePredictionResponse
import com.vanillaplacepicker.data.PlaceDetailsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WebApi {
    @GET("details/json")
    fun fetchPlaceDetails(@QueryMap param: HashMap<String, String>): Observable<PlaceDetailsResponse>

    @GET("queryautocomplete/json")
    fun searchAutoCompletePredictions(@QueryMap param: HashMap<String, String>): Observable<AutocompletePredictionResponse>
}