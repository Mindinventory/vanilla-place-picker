# Vanilla Place Picker
[![](https://jitpack.io/v/Mindinventory/VanillaPlacePicker.svg)](https://jitpack.io/#Mindinventory/VanillaPlacePicker)

Vanilla Place Picker provides a UI that displays an interactive map to get the place details and Autocomplete functionality, which displays place predictions based on user search input.

### Preview
![image](/media/vanillaplacepicker-autocomplete.gif) ![image](/media/vanillaplacepicker-map.gif)

### Key features

* Simple implementation for place picker either using Autocomplete, Map or both
* Set your own custom map styles
* Customise map pin icon
* Set default location position
* Use it without location permission
* Choose to show only open businesses or all
* Highly customise attributes
* Multi languages support.
* RTL layout support.

# Usage

* Dependencies

    Step 1. Add the JitPack repository to your build file
    
    Add it in your root build.gradle at the end of repositories:

    ```groovy
	    allprojects {
		    repositories {
			    ...
			    maven { url 'https://jitpack.io' }
		    }
	    }
    ``` 

    Step 2. Add the dependency
    
    Add it in your app module build.gradle:
    
    ```groovy
        dependencies {
            ...
            implementation 'com.github.Mindinventory:VanillaPlacePicker:0.0.4'
        }
    ``` 

* Implementation

    Step 1. Add GOOGLE MAP API KEY to your AndroidManifest.xml:
    
    ```xml
      <application ... >
        ...
        
        <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="@string/YOUR_GOOGLE_MAPS_API_KEY" />
        
      </application>
    ``` 

    Step 2. Add VanillaPlacePicker Builder in to your activity class:
    
    ```kotlin

            val intent = VanillaPlacePicker.Builder(this)
                .withLocation(23.057582, 72.534458)
                .setPickerLanguage(PickerLanguage.HINDI) // Apply language to picker
                .setTintColor(R.color.colorPrimaryAmber) // Apply Tint color to Back, Clear button of AutoComplete UI
                /*
                 * Configuration for AutoComplete UI
                 */
                .setRegion("IN")
                .setLanguage("en")
                .isOpenNow(true) // Returns only those places that are open for business at the time the query is sent.
                ...

                /*
                 * Configuration for Map UI
                 */
                .enableMap() // Enable to select place from map
                .setMapStyle(R.raw.style_json) // containing the JSON style declaration for night-mode styling
                .setMapPinDrawable(android.R.drawable.ic_menu_mylocation) // To give custom pin image for map marker
                ...

                .build()

            startActivityForResult(intent, REQUEST_PLACE_PICKER)

        ...

        //----- override onActivityResult function to get Vanilla Place Picker result.
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (resultCode == Activity.RESULT_OK && data != null) {
                when (requestCode) {
                    REQUEST_PLACE_PICKER -> {
                        val vanillaAddress = data.getSerializableExtra(KeyUtils.SELECTED_PLACE) as VanillaAddress
                        // Do needful with your vanillaAddress
                    }
                }
            }
        }
        
    ``` 
    
### Requirements

* minSdkVersion >= 19
* Androidx

    
    

# LICENSE!

Vanilla Place Picker is [MIT-licensed](/LICENSE).

# Let us know!
We’d be really happy if you send us links to your projects where you use our component. Just send an email to sales@mindinventory.com And do let us know if you have any questions or suggestion regarding our work.
