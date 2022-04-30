package com.example.final_project.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.final_project.R
import com.example.final_project.activities.ProfileActivity
import com.example.final_project.databinding.FragmentProfileBinding
import com.parse.ParseCloud
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.regex.Pattern


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = ParseUser.getCurrentUser()
        binding.ProfileName.setText(user.getString("name"))
        binding.ProfileEmail.setText(user.getString("email"))
        binding.ProfilePhoneNum.setText(user.getString("phonenum"))
        binding.ProfileImage.setOnClickListener {
            Log.i(ProfileActivity.TAG, "-----ProfilePhoto Clicked")
            openGalleryForImage()
        }
        binding.btnDeleteAccount.setOnClickListener {
            Log.i(ProfileActivity.TAG, "-----Delete Account Clicked")
            openDialog()
//            user.deleteInBackground()
//            ParseUser.logOutInBackground()
        }
        binding.ProfileSaveButton.setOnClickListener {
            val name = getView()?.findViewById<EditText>(R.id.Profile_Name)?.text.toString()
            val email = getView()?.findViewById<EditText>(R.id.Profile_Email)?.text.toString()
            val phone = getView()?.findViewById<EditText>(R.id.Profile_PhoneNum)?.text.toString()
            Log.i(ProfileActivity.TAG, "$name/$email/$phone")
            updateUser(name, email, phone)
        }
    }

    private fun updateUser(name: String, email: String, phone: String) {
        // Create the ParseUser
        val user = ParseUser.getCurrentUser()
        Log.i(ProfileActivity.TAG, "" + user)
        // Set fields for the user to be created
        val pattern =
            ("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
        if (name == "" || email == "" || phone == "") {
            Toast.makeText(
                context,
                "Please Enter Name, Email, and Phone Number",
                Toast.LENGTH_SHORT
            ).show()
        } else if (!Pattern.matches(pattern, email)) {
            Toast.makeText(
                context,
                "Please Enter Valid Email address",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val params = HashMap<String, Any?>()
            params["objectId"] = user.objectId
            params["newEmail"] = email
            params["newPhonenum"] = phone
            params["newName"] = name
            Log.i(ProfileActivity.TAG, "ObjectId --> " + user.objectId)
            Log.i(ProfileActivity.TAG, "" + params)
            ParseCloud.callFunctionInBackground(
                "editUserProperty", params
            ) { String: Any, e: ParseException? ->
                if (e == null) {
                    ParseUser.getCurrentUser().fetchInBackground<ParseObject> { user, e -> }
                    Log.i(
                        ProfileActivity.TAG,
                        "User saved: " + user.getString("email")
                                + " " + user.getString("name") + " " + user.getString("phonenum")
                    )
                    Toast.makeText(context, "Successfully Saved!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        getResult.launch(intent)
    }

    private fun openDialog() {
        val dialog = DeleteAccountFragment()
        dialog.show(parentFragmentManager, "Testing")
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val value = it.data?.getStringExtra("input")
            }
        }
}