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

        binding.ProfileSaveButton.setOnClickListener { view ->
            Log.i(ProfileActivity.TAG, "시발 버튼 눌렸다고 썅")
            val name = requireView().findViewById<EditText>(R.id.Profile_Name).text.toString()
            val email = requireView().findViewById<EditText>(R.id.Profile_Email).text.toString()
            val phone = requireView().findViewById<EditText>(R.id.Profile_PhoneNum).text.toString()
            //updateUser(name, email, phone)
        }
        binding.tvDeleteAccount.setOnClickListener {
            Toast.makeText(requireContext(), "Needs to confirm a few things...", Toast.LENGTH_SHORT)
                .show()
        }
    }
}