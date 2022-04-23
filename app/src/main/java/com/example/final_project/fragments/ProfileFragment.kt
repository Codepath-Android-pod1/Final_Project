package com.example.final_project.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.final_project.R
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
        binding.tvLogout.setOnClickListener{
            AlertDialog.Builder(requireContext())
                .setTitle("Confirm logout")
                .setMessage("Are you sure you want to logout or?")
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setPositiveButton(android.R.string.yes, )
                { _, _ ->
                    requireActivity().finish()
                }
                .setNegativeButton(android.R.string.no, null)
                .show()
        }
        binding.tvDeleteAccount.setOnClickListener{
            Toast.makeText(requireContext(), "Needs to confirm a few things...", Toast.LENGTH_SHORT)
                .show()
        }
    }
}