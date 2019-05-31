package com.mindinventory.placepicker.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mindinventory.placepicker.R
import com.vanillaplacepicker.data.VanillaAddress
import com.vanillaplacepicker.extenstion.showView
import com.vanillaplacepicker.presentation.builder.VanillaPlacePicker
import com.vanillaplacepicker.utils.KeyUtils
import com.vanillaplacepicker.utils.PickerLanguage
import com.vanillaplacepicker.utils.VanillaMapType
import com.vanillaplacepicker.utils.VanillaPickerType
import kotlinx.android.synthetic.main.activity_main.*

class SampleActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cardviewPlacePickerSearch.setOnClickListener(this)
        cardviewPlacePickerMap.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                KeyUtils.REQUEST_PLACE_PICKER -> {
                    val vanillaAddress = data.getSerializableExtra(KeyUtils.SELECTED_PLACE)
                    with(vanillaAddress as VanillaAddress) {
                        cardviewSelectedPlace.showView()
                        tvSelectedPlace.text = formattedAddress
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cardviewPlacePickerSearch -> {
                val intent = VanillaPlacePicker.Builder(this)
                    .with(VanillaPickerType.AUTO_COMPLETE)
                    .withLocation(23.057582, 72.534458)
                    .zoneLocale("en_US")
                    .zoneDefaultLocale(true)
                    .setTintColor(R.color.colorPrimaryAmber)
                    .setPickerLanguage(PickerLanguage.ENGLISH)
                    .build()
                startActivityForResult(intent, KeyUtils.REQUEST_PLACE_PICKER)
            }

            R.id.cardviewPlacePickerMap -> {
                val intent = VanillaPlacePicker.Builder(this)
                    .withLocation(23.057582, 72.534458)
                    .with(VanillaPickerType.MAP_WITH_AUTO_COMPLETE)
                    .zoneLocale("en_US")
                    .setMapType(VanillaMapType.MAP_TYPE_SATELLITE)
                    .zoneDefaultLocale(true)
                    .setPickerLanguage(PickerLanguage.ENGLISH)
                    .build()
                startActivityForResult(intent, KeyUtils.REQUEST_PLACE_PICKER)
            }
        }
    }
}
