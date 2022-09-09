# Vanilla Place Picker
[![](https://jitpack.io/v/Mindinventory/VanillaPlacePicker.svg)](https://jitpack.io/#Mindinventory/VanillaPlacePicker) ![](https://img.shields.io/github/license/mindinventory/VanillaPlacePicker)

Vanilla Place Picker provides a UI that displays an interactive map to get the place details and Autocomplete functionality, which displays place predictions based on user search input.

Developers often come across a requirement of adding precise location. So, a place picker which is easy to implement, less time consuming, and simple enough for users to use it is always in demand and here we have a Vanilla Place Picker which developer can add it in quick simple steps.

## Preview
![image](/media/vanillaplacepicker-autocomplete.gif) ![image](/media/vanillaplacepicker-map.gif)

## Key features
* Android 12 support
* Simple implementation for place picker either using Autocomplete, Map or both
* Set your own custom map styles
* Customise map pin icon
* Set default location position
* Use it without location permission
* Choose to show only open businesses or all
* Highly customise attributes
* Multi languages support
* RTL layout support

## Usage
### Dependencies

- **Step 1. Add the JitPack repository in your project build.gradle:**
```bash
allprojects {
	    repositories {
		    ...
		    maven { url 'https://jitpack.io' }
	    }
    }
```
**or**
**If Android studio version is Arctic Fox or higher then add it in your settings.gradle**

```bash
dependencyResolutionManagement {
  		repositories {
       		...
       		maven { url 'https://jitpack.io' }
   		}
   }
``` 
- **Step 2. Add the dependency in your app module build.gradle file**
```bash
        dependencies {
            ...
            implementation 'com.github.Mindinventory:VanillaPlacePicker:X.X.X'
        }
``` 

### Implementation
- **Step 1. Add GOOGLE MAP API KEY meta-data to your AndroidManifest.xml:**

```bash
      <application ... >
        ...
        
        <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="@string/YOUR_GOOGLE_MAPS_API_KEY" />
        
      </application>
``` 

- **Step 2. Add GOOGLE MAP API KEY in your local.properties file with the same variable name as defined below (google.maps_api_key)**
```bash
     google.maps_api_key=PLACE YOUR API KEY HERE
``` 

- **Step 3. Add VanillaPlacePicker Builder in to your activity class:**
```bash

#startActivityForResult is deprecated so better to use registerForActivityResult
       
     var placePickerResultLauncher =
       registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
           if (result.resultCode == Activity.RESULT_OK && result.data != null) {
               val vanillaAddress = VanillaPlacePicker.getPlaceResult(result.data)
           }
       }
       
#Launch caller with Intent
        
     val intent = VanillaPlacePicker.Builder(this)
        .with(PickerType.MAP_WITH_AUTO_COMPLETE) // Select Picker type to enable autocompelte, map or both
        .withLocation(23.057582, 72.534458)
        .setPickerLanguage(PickerLanguage.HINDI) // Apply language to picker
        .setLocationRestriction(LatLng(23.0558088,72.5325067), LatLng(23.0587592,72.5357321)) // Restrict location bounds in map and autocomplete
        .setCountry("IN") // Only for Autocomplete
        .enableShowMapAfterSearchResult(true) // To show the map after selecting the place from place picker only for PickerType.MAP_WITH_AUTO_C
        /*
         * Configuration for Map UI
         */
        .setMapType(MapType.SATELLITE) // Choose map type (Only applicable for map screen) 
        .setMapStyle(R.raw.style_json) // Containing the JSON style declaration for night-mode styling
        .setMapPinDrawable(android.R.drawable.ic_menu_mylocation) // To give custom pin image for map marker
        .build()

     placePickerResultLauncher.launch(intent)
                  
``` 
    
## Requirements

* minSdkVersion >= 19
* Androidx

# LICENSE!

Vanilla Place Picker is [MIT-licensed](/LICENSE).

# Let us know!
We’d be really happy if you send us links to your projects where you use our component. Just send an email to sales@mindinventory.com And do let us know if you have any questions or suggestion regarding our work.
