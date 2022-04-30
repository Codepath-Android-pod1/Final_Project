package com.example.final_project.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.final_project.BuildConfig
import com.example.final_project.R
import com.example.final_project.fragments.*
import com.example.final_project.models.TMApi
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.libraries.places.api.Places
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.parse.ParseGeoPoint
import com.parse.ParseUser
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    EasyPermissions.PermissionCallbacks {
    // Toolbar/Navbar Related stuff
    private lateinit var drawer: DrawerLayout
    private lateinit var fragmentManger: FragmentManager
    private var fragmentToShow: Fragment? = null
    private lateinit var navView: NavigationView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentManger = supportFragmentManager

        Places.initialize(this, BuildConfig.MAPS_API_KEY)
        fusedLocationClient = getFusedLocationProviderClient(this)

        // Toolbar Init
        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.title = " "

        // Drawer
        drawer = findViewById(R.id.draw_main)
        val drawerListener = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(drawerListener)
        drawerListener.syncState()

        // Fragment Selection && Initial Fragment Setup
        navView = findViewById(R.id.main_nav_view)
        navView.setNavigationItemSelectedListener(this)
        if (savedInstanceState == null) {
            fragmentToShow = HomeFragment()
            changeFragment()
            navView.setCheckedItem(R.id.Home)
        }

        // Floating Action Button && set onClick
        this.findViewById<FloatingActionButton>(R.id.Main_FloatingButton).setOnClickListener {
            fragmentToShow = CreateEventFragment()
            //Change to Edit Fragment
            navView.setCheckedItem(R.id.Compose)
            changeFragment()
        }

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(TMApi::class.java)

        if (hasLocationPermission()) {
            queryLocation()
        } else {
            requestLocationPermissions()
        }

        toolbar.setNavigationOnClickListener {
            val user = ParseUser.getCurrentUser()
            drawer.openDrawer(GravityCompat.START)
            Log.i(ProfileActivity.TAG, "--------------------Drawer Button PUSHED")
            if (user.getString("name") == null) {
                findViewById<TextView>(R.id.profileName).text = "@Username"
                findViewById<TextView>(R.id.profileEmail).text = "User Email"
            } else {
                findViewById<TextView>(R.id.profileName).text = "@${user.getString("name")}"
                findViewById<TextView>(R.id.profileEmail).text = user.getString("email")
            }
        }
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(FINE_COARSE_LOCATION_CODE)
    private fun queryLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val currLocation = ParseGeoPoint(location.latitude, location.longitude)
                    val currUser = ParseUser.getCurrentUser()
                    currUser.put("Location", currLocation)
                    currUser.save()
                    geoHash = GeoFireUtils.getGeoHashForLocation(
                        GeoLocation(location.latitude, location.longitude),
                        9
                    )
                    Log.i(TAG, "Current geoHash: $geoHash")
                }
            }
    }

    // Misc stuff like Toolbar / Nav bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Search -> {
                //fragmentToShow = SearchFragment()
                fragmentToShow = WorkInProgressFragment()
            }
            R.id.Feedback -> {
                fragmentToShow = FeedBackFragment()
                navView.setCheckedItem(R.id.nav_Feedback)
            }
            R.id.Report -> {
                fragmentToShow = ReportFragment()
                navView.setCheckedItem(R.id.nav_Bugs)
            }
            R.id.Setting -> {
                fragmentToShow = SettingFragment()
                navView.setCheckedItem(R.id.Preference)
            }
        }

        changeFragment()
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Home -> {
                fragmentToShow = HomeFragment()
            }

            R.id.Compose -> {
                fragmentToShow = CreateEventFragment()
            }
            R.id.Friends -> {
                fragmentToShow = WorkInProgressFragment()
            }
            R.id.profile_user -> {
                fragmentToShow = ProfileFragment()
            }
            R.id.Preference -> {
                fragmentToShow = SettingFragment()
            }
            R.id.nav_Feedback -> {
                fragmentToShow = FeedBackFragment()
            }
            R.id.nav_Bugs -> {
                fragmentToShow = ReportFragment()
            }
            R.id.nav_Logout -> {
                ParseUser.logOut()
                goToLogin()
            }
        }

        changeFragment()
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun requestLocationPermissions() {
//        EasyPermissions.requestPermissions(
//            this,
//            getString(R.string.permission_location_rationale),
//            COARSE_LOCATION_CODE,
//            Manifest.permission.ACCESS_COARSE_LOCATION
//        )
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.permission_location_rationale),
            FINE_LOCATION_CODE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private fun hasLocationPermission() =
        EasyPermissions.hasPermissions(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Log.d(TAG, "onPermissionsDenied: $requestCode :${perms.size}")

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(this).build().show()
        } else {
            requestLocationPermissions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {}

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun changeFragment() {
        if (fragmentToShow != null) {
            fragmentManger
                .beginTransaction()
                .replace(R.id.FragmentContainer, fragmentToShow!!)
                .commit()
        }

        if (fragmentToShow is HomeFragment) {
            this.findViewById<FloatingActionButton>(R.id.Main_FloatingButton).show()
        } else {
            this.findViewById<FloatingActionButton>(R.id.Main_FloatingButton).hide()
        }
    }

    companion object {
        const val FINE_LOCATION_CODE = 123
        const val COARSE_LOCATION_CODE = 456
        const val FINE_COARSE_LOCATION_CODE = 789
        const val TAG = "MainActivity"
        const val BASE_URL = "https://app.ticketmaster.com/discovery/v2/"
        lateinit var apiService: TMApi
        var geoHash: String = ""
    }
}

