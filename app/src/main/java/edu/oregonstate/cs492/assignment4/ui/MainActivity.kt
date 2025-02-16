package edu.oregonstate.cs492.assignment4.ui

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import edu.oregonstate.cs492.assignment4.R
import edu.oregonstate.cs492.assignment4.data.SavedCity

/*
 * Often, we'll have sensitive values associated with our code, like API keys, that we'll want to
 * keep out of our git repo, so random GitHub users with permission to view our repo can't see them.
 * The OpenWeather API key is like this.  We can keep our API key out of source control using the
 * technique described below.  Note that values configured in this way can still be seen in the
 * app bundle installed on the user's device, so this isn't a safe way to store values that need
 * to be kept secret at all costs.  This will only keep them off of GitHub.
 *
 * The Gradle scripts for this app are set up to read your API key from a special Gradle file
 * that lives *outside* your project directory.  This file called `gradle.properties`, and it
 * should live in your GRADLE_USER_HOME directory (this will usually be `$HOME/.gradle/` in
 * MacOS/Linux and `$USER_HOME/.gradle/` in Windows).  To store your API key in `gradle.properties`,
 * make sure that file exists in the correct location, and then add the following line:
 *
 *   OPENWEATHER_API_KEY="<put_your_own_OpenWeather_API_key_here>"
 *
 * If your API key is stored in that way, the Gradle build for this app will grab it and write it
 * into the string resources for the app with the resource name "openweather_api_key".  You'll be
 * able to access your key in the app's Kotlin code the same way you'd access any other string
 * resource, e.g. `getString(R.string.openweather_api_key)`.  This is what's done in the code below
 * when the OpenWeather API key is needed.
 *
 * If you don't mind putting your OpenWeather API key on GitHub, then feel free to just hard-code
 * it in the app. 🤷‍
 */

class MainActivity : AppCompatActivity() {
    private val savedCityViewModel: SavedCityViewModel by viewModels()
    private val currentWeatherViewModel: CurrentWeatherViewModel by viewModels()

    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var prefs: SharedPreferences
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        val navController = navHostFragment.navController
        drawerLayout = findViewById(R.id.drawer_layout)
        appBarConfig = AppBarConfiguration(navController.graph, drawerLayout)

        val appBar: MaterialToolbar = findViewById(R.id.top_app_bar)
        setSupportActionBar(appBar)
        setupActionBarWithNavController(navController, appBarConfig)

        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)

        savedCityViewModel.savedCities.observe(this) {savedCities ->
            addCitiesToNavDrawer(savedCities)

            navController.navigate(R.id.navigate_to_current_weather)
        }

        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    // Permission is granted. Continue with location access.
                    getCurrentLocationAndWeather()
                }
                else -> {
                    // Permission is denied. Handle the failure.
                }
            }
        }


        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.new_city -> {
                    val dialogFragment = AddCityDialogFragment()
                    dialogFragment.show(supportFragmentManager, "AddNewCity")
                }
                R.id.current_weather -> {
                    navController.navigate(R.id.navigate_to_current_weather)
                }
                R.id.five_day_forecast -> {
                    navController.navigate(R.id.navigate_to_five_day_forecast)
                }
                R.id.settings -> {
                    navController.navigate(R.id.navigate_to_settings)
                }
                R.id.current_location -> {
                    requestLocationPermission()
                    getCurrentLocationAndWeather()

                    navController.navigate(R.id.navigate_to_current_weather)
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    private fun addCitiesToNavDrawer(cities: List<SavedCity>) {
        val sortedCities = cities.sortedByDescending { it.timeStamp }
        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val subMenu = navView.menu.findItem(R.id.dynamic_menu).subMenu
        subMenu?.clear()

        for (city in sortedCities) {
            subMenu?.add(city.cityName)?.setOnMenuItemClickListener {
                drawerLayout.closeDrawer(GravityCompat.START)

                val editor = prefs.edit()
                editor.putString(getString(R.string.pref_city_key), city.cityName)
                editor.apply()

                val timeStamp = System.currentTimeMillis()
                val updateCity = SavedCity(city.cityName, timeStamp)
                savedCityViewModel.addNewCity(updateCity)
                
                true
            }
        }
    }

    private fun requestLocationPermission() {
        Log.d("MainActivity", "Requesting location permissions")
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun getCurrentLocationAndWeather() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission()
            return
        }

        val cancellationToken = CancellationTokenSource()

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationToken.token).addOnSuccessListener { location: Location? ->
            location?.let {
                Log.d("MainActivity", "Lat ${it.latitude}, Long ${it.longitude}")
//                currentWeatherViewModel.loadWeatherByCoordinates(it.latitude, it.longitude, getString(R.string.openweather_api_key))
                prefs = PreferenceManager.getDefaultSharedPreferences(this)

                val editor = prefs.edit()
                editor.putString(getString(R.string.pref_city_key), "MountainView")
                editor.apply()

            } ?: run {
                Log.d("MainActivity", "Location is null")
            }
        }.addOnFailureListener {
            Log.d("MainActivity", "Failed to fetch location")
        }
    }

}