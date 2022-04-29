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
    //This ProfileActivity legit do nothing. It is just there to leave it because I am scared
    //what will happen after I delete it.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        findViewById<Button>(R.id.Profile_SaveButton).setOnClickListener {
            Toast.makeText(this, "At least button is clicked", Toast.LENGTH_SHORT).show()
            Log.i(TAG, "시발 버튼 눌렸다고 썅")
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
            Toast.makeText(this, "Please Enter Name, Email, and Phone Number", Toast.LENGTH_SHORT)
                .show()
        } else if (!(email.endsWith(".org") || email.endsWith(".com") ||
                    email.endsWith(".net") || email.endsWith(".gov") ||
                    email.endsWith(".edu")) && !email.contains("@")
        ) {
            Toast.makeText(this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT)
        } else {
            val params = HashMap<String, Any>()
            params["email"] = email
            params["name"] = name
            params["phonenum"] = phone

            ParseCloud.callFunctionInBackground("editUserProperty", params,
                FunctionCallback { obj: ParseObject, e: ParseException? ->
                    if (e == null) {
                        Log.d(
                            "result",
                            obj["email"].toString() + obj["name"].toString() + obj["phonenum"].toString() + " "
                        )
                    } else {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                } as FunctionCallback<ParseObject>)
        }

    }


    companion object {
        const val TAG = "UPDATE ACTIVITY"
    }
}