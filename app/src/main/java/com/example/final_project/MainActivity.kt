package com.example.final_project

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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.final_project.fragments.ChatFragment
import com.example.final_project.fragments.EditFragment
import com.example.final_project.fragments.HomeFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.ParseUser
import permissions.dispatcher.*

@RuntimePermissions
class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var latLng: LatLng

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

        // Fragments / Bottom Navigation Bar
        val fragmentManager: FragmentManager = supportFragmentManager
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener { item ->
            var fragmentToshow: Fragment? = null
            when (item.itemId) {
                R.id.action_Home -> {
                    fragmentToshow = HomeFragment()
                }
                R.id.action_Edit -> {
                    fragmentToshow = EditFragment()
                }
                R.id.action_Chat -> {
                    fragmentToshow = ChatFragment()
                }
            }
            if (fragmentToshow != null) {
                fragmentManager
                    .beginTransaction()
                    .replace(R.id.FragmentContainer, fragmentToshow)
                    .commit()
            }
            true
        }
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.action_Home

        // Generalize Tool bar (We could move it to individual Fragment and have specialize toolbar
        // Actually we might have to us e Toolbar for each Fragments
        val toolbar = findViewById<Toolbar>(R.id.Main_Toolbar)
        setSupportActionBar(findViewById(R.id.Main_Toolbar))
        toolbar.setNavigationIcon(R.drawable.ic_launcher_background)
        toolbar.setNavigationOnClickListener {
            Toast.makeText(this, "Please work", Toast.LENGTH_SHORT).show()
        }

        // Ask permission for coarse location
        getLocationWithPermissionCheck()
    }

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
            R.id.Profile -> {}
        }

        return super.onOptionsItemSelected(item)
    }

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
}