package com.mindinventory.placepicker.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.mindinventory.placepicker.R
import com.mindinventory.placepicker.databinding.ActivitySampleBinding
import com.vanillaplacepicker.extenstion.show
import com.vanillaplacepicker.presentation.builder.VanillaPlacePicker
import com.vanillaplacepicker.utils.MapType
import com.vanillaplacepicker.utils.PickerLanguage
import com.vanillaplacepicker.utils.PickerType

class SampleActivity : AppCompatActivity(), View.OnClickListener {

    private var _binding: ActivitySampleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListener()
    }

    private fun initClickListener() {
        binding.cardviewPlacePickerSearch.setOnClickListener(this)
        binding.cardviewPlacePickerMap.setOnClickListener(this)
    }

    private var placePickerResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val vanillaAddress = VanillaPlacePicker.getPlaceResult(result.data)
                vanillaAddress?.let {
                    binding.cardviewSelectedPlace.show()
                    binding.tvSelectedPlace.text = it.formattedAddress
                }
            }
        }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cardviewPlacePickerSearch -> {
                val intent = VanillaPlacePicker.Builder(this)
                    .with(PickerType.AUTO_COMPLETE)
                    .withLocation(23.0710, 72.5181)
                    .setPickerLanguage(PickerLanguage.ENGLISH)
                    .build()
                placePickerResultLauncher.launch(intent)
            }

            R.id.cardviewPlacePickerMap -> {
                val intent = VanillaPlacePicker.Builder(this)
                    .with(PickerType.MAP_WITH_AUTO_COMPLETE)
                    .setMapType(MapType.SATELLITE)
                    .withLocation(23.0710, 72.5181)
                    .setPickerLanguage(PickerLanguage.ENGLISH)
                    .enableShowMapAfterSearchResult(true)
                    .build()
                placePickerResultLauncher.launch(intent)
            }
        }
    }
}
