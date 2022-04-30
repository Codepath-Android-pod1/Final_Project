package com.example.final_project.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.example.final_project.R
import com.example.final_project.activities.LoginActivity
import com.example.final_project.models.ParseEvent
import com.parse.ParseQuery
import com.parse.ParseUser


class DeleteAccountFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.fragment_delete_account, null))
                .setTitle("Account Removal")
                .setPositiveButton(
                    "Delete Account"
                ) { _, _ ->
                    val user = ParseUser.getCurrentUser()
                    val query = ParseQuery.getQuery(ParseEvent::class.java)
                    query.whereEqualTo("user", user)
                    query.findInBackground { events, e ->
                        if (e != null) {
                            Log.e(ParseEventFragment.TAG, "Error fetching posts")
                        } else {
                            events.forEach { event ->
                                event.deleteInBackground()
                            }
                        }
                    }
                    user.deleteInBackground()
                    ParseUser.logOutInBackground()
                    goToLogin()
                }
                .setNegativeButton(
                    "Cancel"
                ) { _, _ ->
                    // Stop | Leave
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun goToLogin() {
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    companion object {
        const val TAG = "DeleteAccount"
    }

}