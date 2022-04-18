package com.example.final_project

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
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
import com.example.final_project.fragments.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.parse.ParseUser
import permissions.dispatcher.*

@RuntimePermissions
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var latLng: LatLng

    private lateinit var drawer: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var fragmentManger: FragmentManager
    private lateinit var fragmentToShow: Fragment
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
            val firstObject = ParseObject("FirstClass")
            firstObject.put("message","Hey ! First message from android. Parse is now connected")
            firstObject.saveInBackground {
                if (it != null){
                    it.localizedMessage?.let { message -> Log.e("MainActivity", message) }
                }else{
                    Log.d("MainActivity","Object saved.")
                }
            }
        */

        fragmentManger = supportFragmentManager

        // Toolbar Init
        toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = " "

        // Drawer
        drawer = findViewById(R.id.draw_main)
        val drawerListener = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(drawerListener)
        drawerListener.syncState()

        // Fragment Selection && Initial Fragment Setup
        navView = findViewById(R.id.main_nav_view)
        navView.setNavigationItemSelectedListener (this)
        if (savedInstanceState == null) {
            fragmentToShow = HomeFragment()
            changeFragment()
            navView.setCheckedItem(R.id.Testing1)
        }

        // Ask permission for coarse location
        getLocationWithPermissionCheck()
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // Misc stuff like Toolbar / Nav bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Logout -> {
                ParseUser.logOut()
                goToLogin()
                Toast.makeText(this, "Testing Complete", Toast.LENGTH_SHORT).show()
            }
            R.id.Feedback -> {
                fragmentToShow = FeedBackFragment()
            }
            R.id.Report -> {
                fragmentToShow = ReportFragment()
            }
        }

        changeFragment()

        return super.onOptionsItemSelected(item)
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
                Log.d(TAG, "Error trying to get last GPS location");
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


    // Changing Location/Intent
    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun onLocationChanged(location: Location) {
        latLng = LatLng(location.latitude, location.longitude)
    }

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.Testing1 -> {
                fragmentToShow = HomeFragment()
            }

            R.id.Testing2 -> {
                fragmentToShow = EditFragment()
            }
            R.id.Testing3 -> {
                fragmentToShow = ChatFragment()
            }
            R.id.Testing4 -> {
                Toast.makeText(this,"Testing1", Toast.LENGTH_SHORT).show()
            }
            R.id.Testing5 -> {
                Toast.makeText(this,"Testing1", Toast.LENGTH_SHORT).show()
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

    private fun changeFragment() {
        if (fragmentToShow != null) {
            fragmentManger
                .beginTransaction()
                .replace(R.id.FragmentContainer, fragmentToShow)
                .commit()
        }
    }
}



