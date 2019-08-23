package com.vanillaplacepicker.utils

import android.content.Context
import android.content.Intent
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.vanillaplacepicker.presentation.builder.VanillaConfig

object AutoCompleteUtils {
    private val fields = listOf(
        Place.Field.ADDRESS,
        Place.Field.ADDRESS_COMPONENTS,
        Place.Field.ID,
        Place.Field.LAT_LNG,
        Place.Field.NAME,
        Place.Field.OPENING_HOURS,
        Place.Field.PHONE_NUMBER,
        Place.Field.PHOTO_METADATAS,
        Place.Field.PLUS_CODE,
        Place.Field.PRICE_LEVEL,
        Place.Field.RATING,
        Place.Field.TYPES,
        Place.Field.USER_RATINGS_TOTAL,
        Place.Field.UTC_OFFSET,
        Place.Field.VIEWPORT,
        Place.Field.WEBSITE_URI
    )

    fun getAutoCompleteIntent(context: Context, vanillaConfig: VanillaConfig): Intent {

        if (!Places.isInitialized())
            Places.initialize(context.applicationContext, vanillaConfig.apiKey)

        val autocomplete = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.FULLSCREEN,
            fields
        )

        if (vanillaConfig.country.isNullOrBlank()) {
            autocomplete.setCountry(vanillaConfig.country)
        }
        vanillaConfig.zoneRect?.let {
            autocomplete.setLocationRestriction(
                RectangularBounds.newInstance(
                    it.lowerLeft,
                    it.upperRight
                )
            )
        }
        return autocomplete.build(context)
    }
}