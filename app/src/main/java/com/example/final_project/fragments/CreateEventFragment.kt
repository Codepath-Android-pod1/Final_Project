package com.example.final_project.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.final_project.R
import com.example.final_project.activities.MainActivity
import com.example.final_project.models.ParseEvent
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.parse.ParseGeoPoint
import com.parse.ParseUser
import java.text.SimpleDateFormat
import java.util.*

class CreateEventFragment : Fragment() {
    private lateinit var pb: ProgressBar
    private lateinit var eventLoc: ParseGeoPoint
    private lateinit var autocompleteFragment: AutocompleteSupportFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pb = view.findViewById<View>(R.id.pbLoading) as ProgressBar
        val etDate = view.findViewById<EditText>(R.id.etPEDate)
        val etTime = view.findViewById<EditText>(R.id.etTime)
        val etLocation = view.findViewById<EditText>(R.id.etAddress)

        etDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val mDateSetListener =
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val date = "${month + 1}/$dayOfMonth/$year"
                    etDate.setText(date)
                }

            DatePickerDialog(requireContext(), mDateSetListener, year, month, day).show()
        }

        etTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                etTime.setText(SimpleDateFormat("HH:mm", Locale.ENGLISH).format(cal.time))
            }
            TimePickerDialog(
                requireContext(),
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        view.findViewById<Button>(R.id.btnCreate).setOnClickListener {
            createEvent(view)
        }

        // Initialize the AutocompleteSupportFragment
        autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.fragment_autocomplete) as AutocompleteSupportFragment

        // Specify the types of place data to return
        autocompleteFragment.setPlaceFields(listOf(Place.Field.NAME, Place.Field.LAT_LNG))

        // Set up a PlaceSelectionListener to handle the response
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.i(TAG, "Place: ${place.name}, ${place.latLng}")
                eventLoc = ParseGeoPoint(place.latLng.latitude, place.latLng.latitude)
            }

            override fun onError(status: Status) {
                Log.e(TAG, "An error occurred: $status")
            }
        })

        etLocation.setOnClickListener {
            (activity as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.composeView, autocompleteFragment)
                .commit()
        }
    }

    private fun createEvent(view: View) {
        val title = view.findViewById<EditText>(R.id.etTitle).text.toString()
        val description = view.findViewById<EditText>(R.id.etDescription).text.toString()
        val date = view.findViewById<EditText>(R.id.etPEDate).text.toString()
        val time = view.findViewById<EditText>(R.id.etTime).text.toString()
        val dateTime = "$date $time"
        val user = ParseUser.getCurrentUser()

        if (date == "" || time == "") {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val formatter = SimpleDateFormat("M/dd/yyyy HH:mm", Locale.ENGLISH)
        val dateObject: Date = formatter.parse(dateTime) as Date

        hideKeyboard(view.findViewById(R.id.composeView))
        pb.visibility = ProgressBar.VISIBLE

        val event = ParseEvent()
        event.setTitle(title)
        event.setDescription(description)
        event.setDate(dateObject)
        event.setLocation(eventLoc)
        event.setUser(user)
        event.saveInBackground { e ->
            if (e != null) {
                Log.e(MainActivity.TAG, "Error while saving post")
                e.printStackTrace()
                Toast.makeText(
                    requireContext(),
                    "Post unsuccessful. Ensure all field are filled in",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Log.i(MainActivity.TAG, "Successfully saved post")
                Toast.makeText(requireContext(), "Post successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), ParseEventFragment::class.java))
            }
            pb.visibility = ProgressBar.INVISIBLE
        }
    }

    private fun hideKeyboard(v: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.applicationWindowToken, 0)
    }

    companion object {
        const val TAG = "CreateEventFragment"
    }
}