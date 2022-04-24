package com.example.final_project.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.final_project.R
import com.example.final_project.fragments.*
import com.example.final_project.models.TMApi
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.parse.ParseUser
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import permissions.dispatcher.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@RuntimePermissions
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // Toolbar/Navbar Related stuff
    private lateinit var drawer: DrawerLayout
    private lateinit var fragmentManger: FragmentManager
    private var fragmentToShow: Fragment? = null
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getLocationWithPermissionCheck()
        fragmentManger = supportFragmentManager

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
            navView.setCheckedItem(R.id.Testing1)
        }

        // Floating Action Button && Set it's onclick thing


        this.findViewById<FloatingActionButton>(R.id.Main_FloatingButton).setOnClickListener {
            fragmentToShow = EditFragment()
            //Change to Edit Fragment
            navView.setCheckedItem(R.id.Testing2)
            changeFragment()
        }

        // Ask permission for coarse location


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
    }

    // Misc stuff like Toolbar / Nav bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Search -> {
                fragmentToShow = SearchFragment()
            }
            R.id.Logout -> {
                ParseUser.logOut()
                goToLogin()
            }
            R.id.Feedback -> {
                fragmentToShow = FeedBackFragment()
            }
            R.id.Report -> {
                fragmentToShow = ReportFragment()
            }
            R.id.Setting -> {
                fragmentToShow = SettingFragment()
            }
        }

        changeFragment()
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Testing1 -> {
                fragmentToShow = HomeFragment()
            }

            R.id.Testing2 -> {
                fragmentToShow = EditFragment()
            }
            R.id.Testing3 -> {
                fragmentToShow = ChatFragment()
            }
            R.id.profile_user -> {
                fragmentToShow = ProfileFragment()
            }
            R.id.Testing5 -> {
                Toast.makeText(this, "Testing1", Toast.LENGTH_SHORT).show()
            }
            R.id.Logout -> {
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

    // Permission set up
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated function
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    onLocationChanged(location)
                }
            }
            .addOnFailureListener { e: Exception ->
                Log.d(TAG, "Error trying to get last GPS location")
                e.printStackTrace()
            }
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun onLocationDenied() {
        Toast.makeText(this, R.string.permission_location_denied, Toast.LENGTH_SHORT).show()
        // TODO Disable search by location
    }

    @OnShowRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun showRationaleForLocation(request: PermissionRequest) {
        showRationaleDialog(R.string.permission_location_rationale, request)
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun onLocationNeverAskAgain() {
        Toast.makeText(this, R.string.permission_location_never_ask_again, Toast.LENGTH_SHORT)
            .show()
    }

    private fun showRationaleDialog(@StringRes messageResId: Int, request: PermissionRequest) {
        AlertDialog.Builder(this)
            .setPositiveButton(R.string.button_allow) { _, _ -> request.proceed() }
            .setNegativeButton(R.string.button_deny) { _, _ -> request.cancel() }
            .setCancelable(false)
            .setMessage(messageResId)
            .show()
    }

    private fun onLocationChanged(location: Location) {
        currLocation = location
        geoHash = GeoFireUtils.getGeoHashForLocation(
            GeoLocation(location.latitude, location.longitude),
            9
        )
        Log.i(TAG, "Current geoHash: $geoHash")
    }

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
        const val TAG = "MainActivity"
        const val BASE_URL = "https://app.ticketmaster.com/discovery/v2/"
        lateinit var apiService: TMApi
        var geoHash: String = ""
        lateinit var currLocation: Location
    }
}

