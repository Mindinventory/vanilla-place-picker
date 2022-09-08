package com.mindinventory.placepicker.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mindinventory.placepicker.R
import com.vanillaplacepicker.extenstion.show
import com.vanillaplacepicker.presentation.builder.VanillaPlacePicker
import com.vanillaplacepicker.utils.KeyUtils
import com.vanillaplacepicker.utils.MapType
import com.vanillaplacepicker.utils.PickerLanguage
import com.vanillaplacepicker.utils.PickerType
import kotlinx.android.synthetic.main.activity_main.*

class SampleActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cardviewPlacePickerSearch.setOnClickListener(this)
        cardviewPlacePickerMap.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cardviewPlacePickerSearch -> {
                val intent = VanillaPlacePicker.Builder(this)
                    .with(PickerType.AUTO_COMPLETE)
                    .withLocation(23.0710, 72.5181)
                    .setPickerLanguage(PickerLanguage.ENGLISH)
                    .build()
                startActivityForResult(intent, KeyUtils.REQUEST_PLACE_PICKER)
            }

            R.id.cardviewPlacePickerMap -> {
                val intent = VanillaPlacePicker.Builder(this)
                    .with(PickerType.MAP_WITH_AUTO_COMPLETE)
                    .setMapType(MapType.SATELLITE)
                    .withLocation(23.0710, 72.5181)
                    .setPickerLanguage(PickerLanguage.ENGLISH)
                    .enableShowMapAfterSearchResult(true)
                    .build()
                startActivityForResult(intent, KeyUtils.REQUEST_PLACE_PICKER)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                KeyUtils.REQUEST_PLACE_PICKER -> {
                    val vanillaAddress = VanillaPlacePicker.onActivityResult(data)
                    vanillaAddress?.let {
                        cardviewSelectedPlace.show()
                        tvSelectedPlace.text = it.formattedAddress
                    }
                }
            }
        }
    }
}
