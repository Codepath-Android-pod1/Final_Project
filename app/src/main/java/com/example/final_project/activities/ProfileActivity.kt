package com.example.final_project.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.final_project.R
import com.parse.*


class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)


        findViewById<Button>(R.id.Profile_SaveButton).setOnClickListener {
            val name = findViewById<EditText>(R.id.Profile_Name).text.toString()
            val email = findViewById<EditText>(R.id.Profile_Email).text.toString()
            val phone = findViewById<EditText>(R.id.Profile_PhoneNum).text.toString()
            updateUser(name, email, phone)
        }
    }

    private fun updateUser(name: String, email: String, phone: String) {
        // Create the ParseUser
        val user = ParseUser()

        // Set fields for the user to be created
        if (name == "" || email == "" || phone == "") {
            Toast.makeText(this, "Please Enter Name, Email, and Phone Number", Toast.LENGTH_SHORT).show()
        } else {
            val params = HashMap<String, Any>()
            params.put("email", email)
            params.put("name", name)
            params.put("phonenum", phone)

            ParseCloud.callFunctionInBackground("editUserProperty", params,
                FunctionCallback { string: String?, e: ParseException? ->
                    ParseCloud.useMasterKey()
                    if (e == null) {
                        Toast.makeText(this, "Successfully Created", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Not working", Toast.LENGTH_SHORT).show()
                    }
                })
        }

    }

    companion object {
        const val TAG = "UPDATE ACTIVITY"
    }
}