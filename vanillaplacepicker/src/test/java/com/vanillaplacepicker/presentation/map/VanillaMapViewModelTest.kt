package com.vanillaplacepicker.presentation.map

import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import com.vanillaplacepicker.presentation.common.TestRulesListener
import com.vanillaplacepicker.utils.SharedPrefs
import io.kotlintest.extensions.TestListener
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import org.mockito.Mockito

class VanillaMapViewModelTest : BehaviorSpec() {

    private val latitude = 23.058759689331055
    private val longitude = 72.53572845458984
    private val latLng = LatLng(latitude, longitude)

    //output value
    private val successLatLng = latLng

    //mock dependencies
    private val sharedPrefs = mock<SharedPrefs>()
    private val vanillaMapViewModel by lazy { VanillaMapViewModel(sharedPrefs) }
    private var vanillaMapObserver = mock<Observer<LatLng>>()

    override fun listeners(): List<TestListener> = listOf(TestRulesListener)

    init {
        fetchSavedLocationSuccess()
    }

    private fun fetchSavedLocationSuccess() {
        Given("Fetch saved location") {
            When("LatLng Available") {
                stubFetchSavedLocationSuccess()
                vanillaMapViewModel.latLngLiveData.observeForever(vanillaMapObserver)
                vanillaMapViewModel.fetchSavedLocation()
                Then("Fetch saved location success") {
                    argumentCaptor<LatLng>().apply {
                        Mockito.verify(vanillaMapObserver, times(1)).onChanged(capture())
                        LatLng(firstValue.latitude, firstValue.longitude) shouldBe successLatLng
                    }
                }
            }
        }
    }

    //stubbing
    private fun stubFetchSavedLocationSuccess() {
        whenever(sharedPrefs.deviceLatitude).thenReturn(latitude.toFloat())
        whenever(sharedPrefs.deviceLongitude).thenReturn(longitude.toFloat())
    }
}