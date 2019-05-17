package com.vanillaplacepicker.presentation.autocomplete

import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.PublishRelay
import com.vanillaplacepicker.data.AutocompletePredictionResponse
import com.vanillaplacepicker.data.PlaceDetailsResponse
import com.vanillaplacepicker.domain.common.Resource
import com.vanillaplacepicker.domain.common.SingleLiveEvent
import com.vanillaplacepicker.domain.common.Status
import com.vanillaplacepicker.domain.repository.WebApiClient
import com.vanillaplacepicker.presentation.common.VanillaBaseViewModel
import com.vanillaplacepicker.utils.KeyUtils
import com.vanillaplacepicker.utils.Logger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class VanillaAutocompleteViewModel : VanillaBaseViewModel() {
    val TAG = VanillaAutocompleteViewModel::class.java.simpleName
    val autoCompleteLiveData = MutableLiveData<Resource<AutocompletePredictionResponse>>()
    val showClearButtonLiveData = MutableLiveData<Boolean>()
    val vanillaAddressLiveData = SingleLiveEvent<Resource<PlaceDetailsResponse>>()
    private val autoCompletePublishSubject = PublishRelay.create<String>()
    private var minCharLimit: Int? = null
    private var hashMap = HashMap<String, String>()

    fun configureAutoComplete(hashMap: HashMap<String, String>, minCharLimit: Int) {
        this.hashMap = hashMap
        this.minCharLimit = minCharLimit

        autoCompletePublishSubject
                .debounce(KeyUtils.DEBOUNCE_INTERVAL, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .filter { it.trim().length >= minCharLimit }
                .flatMap {
                    hashMap[KeyUtils.INPUT] = it
                    callAutoCompleteAPI(hashMap)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    onAutoCompleteResultReceived(result)
                }, {
                    autoCompleteLiveData.value = Resource(Status.ERROR, it)
                    Logger.e(TAG, "Failed to get search results $it")
                }).collect()
    }

    private fun onAutoCompleteResultReceived(
            result: AutocompletePredictionResponse
    ) {
        if (result.status == KeyUtils.OK) {
            autoCompleteLiveData.value = Resource(Status.SUCCESS, result)
        } else {
            autoCompleteLiveData.value = Resource(Status.ERROR, result)
        }
    }

    fun onInputStateChanged(query: String, minCharLimit: Int) {
        showClearButtonLiveData.value = query.isNotEmpty() // query.length > 0 with empty check

        if (query.length >= minCharLimit) {
            autoCompleteLiveData.value = Resource(Status.LOADING)
        } else {
            autoCompleteLiveData.value = Resource(Status.ERROR)
        }
        autoCompletePublishSubject.accept(query.trim())
    }

    private fun callAutoCompleteAPI(hashMap: HashMap<String, String>): Observable<AutocompletePredictionResponse> {
        return WebApiClient.webApi.searchAutoCompletePredictions(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchPlaceDetails(placeId: String, apiKey: String) {
        vanillaAddressLiveData.value = Resource(Status.LOADING)
        val hashMap = HashMap<String, String>().apply {
            put(
                    "fields",
                    "address_component,adr_address,alt_id,formatted_address,geometry,icon,id,name,permanently_closed,photo,place_id,plus_code,scope,type,url,utc_offset,vicinity")
            put("key", apiKey)
            put("placeid", placeId)
        }

        WebApiClient.webApi.fetchPlaceDetails(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.status == KeyUtils.OK) {
                        vanillaAddressLiveData.value = Resource(Status.SUCCESS, it)
                    } else {
                        vanillaAddressLiveData.value = Resource(Status.ERROR, it)
                    }
                }, {
                    vanillaAddressLiveData.value = Resource(Status.ERROR, it)
                }).collect()
    }
}