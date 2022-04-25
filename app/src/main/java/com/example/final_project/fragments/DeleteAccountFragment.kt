package com.example.final_project.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.final_project.R
import com.example.final_project.activities.LoginActivity
import com.google.android.material.snackbar.Snackbar
import com.parse.ParseUser
import java.lang.IllegalStateException

class DeleteAccountFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.fragment_delete_account, null))
                .setTitle("Account Removal")
                .setPositiveButton("Delete Account"
                ) { dialogInterface, i ->
                    val user = ParseUser.getCurrentUser()

                    user.deleteInBackground()
                    ParseUser.logOutInBackground()
                    goToLogin()

                }
                .setNegativeButton("Cancel"
                ) { dialogInterface, i ->
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

}