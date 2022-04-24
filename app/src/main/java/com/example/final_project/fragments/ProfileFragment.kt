package com.example.final_project.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.final_project.R
import com.example.final_project.activities.ProfileActivity
import com.example.final_project.databinding.FragmentProfileBinding
import com.parse.*


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

        binding.ProfileSaveButton.setOnClickListener {
            Log.i(ProfileActivity.TAG, "Tlqkf 버튼 눌렸다고")
            val name = getView()?.findViewById<EditText>(R.id.Profile_Name)?.text.toString()
            val email = getView()?.findViewById<EditText>(R.id.Profile_Email)?.text.toString()
            val phone = getView()?.findViewById<EditText>(R.id.Profile_PhoneNum)?.text.toString()
            Log.i(ProfileActivity.TAG, ""+name + "/" + email+"/" + phone)
            updateUser(name, email, phone)
        }

        binding.tvDeleteAccount.setOnClickListener{
            Toast.makeText(requireContext(), "Needs to confirm a few things...", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun updateUser(name: String, email: String, phone: String) {
        // Create the ParseUser
        val user = ParseUser.getCurrentUser()
        Log.i(ProfileActivity.TAG, ""+user)
        // Set fields for the user to be created
        if (name == "" || email == "" || phone == "") {
            Toast.makeText(context, "Please Enter Name, Email, and Phone Number", Toast.LENGTH_SHORT).show()
        } else {
            val params = HashMap<String, Any?>()
            params.put("objectId", user.getObjectId())
            params.put("newEmail", email)
            params.put("newPhonenum", phone)
            params.put("newName", name)
            Log.i(ProfileActivity.TAG, "ObjectId --> "+user.getObjectId())
            Log.i(ProfileActivity.TAG, ""+params)
            ParseCloud.callFunctionInBackground("editUserProperty", params,
                FunctionCallback { String: Object, e: ParseException? ->
                    if (e == null) {
                        Log.i(ProfileActivity.TAG, "It is null and it passed")
                    } else {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                } )
        }

    }
}