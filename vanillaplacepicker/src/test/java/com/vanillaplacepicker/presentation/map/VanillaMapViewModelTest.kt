package com.vanillaplacepicker.presentation.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import com.vanillaplacepicker.utils.SharedPrefs
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class VanillaMapViewModelTest {

    private val latitude = 23.058759689331055
    private val longitude = 72.53572845458984
    private val latLng = LatLng(latitude, longitude)

    //output value
    private val successLatLng = latLng

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    //mock dependencies
    private val sharedPrefs = mock<SharedPrefs>()
    private val vanillaMapViewModel by lazy { VanillaMapViewModel(sharedPrefs) }
    private var vanillaMapObserver = mock<Observer<LatLng>>()

    @Captor
    var argumentCaptor: ArgumentCaptor<LatLng>? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun fetchSavedLocationSuccess() {
        stubFetchSavedLocationSuccess()
        vanillaMapViewModel.latLngLiveData.observeForever(vanillaMapObserver)
        vanillaMapViewModel.fetchSavedLocation()
        argumentCaptor?.apply {
            Mockito.verify(vanillaMapObserver, times(1)).onChanged(capture())
            MatcherAssert.assertThat(
                value,
                CoreMatchers.`is`(successLatLng)
            )
        }
    }

    //stubbing
    private fun stubFetchSavedLocationSuccess() {
        whenever(sharedPrefs.deviceLatitude).thenReturn(latitude.toFloat())
        whenever(sharedPrefs.deviceLongitude).thenReturn(longitude.toFloat())
    }
}