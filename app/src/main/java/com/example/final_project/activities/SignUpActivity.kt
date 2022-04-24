package com.example.final_project.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.final_project.R
import com.parse.ParseUser


class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        if (ParseUser.getCurrentUser() != null) {
            goToMain()
        }

        findViewById<Button>(R.id.Signup_SignUp).setOnClickListener {
            val username = findViewById<EditText>(R.id.Signup_Username).text.toString()
            val password = findViewById<EditText>(R.id.Signup_Password).text.toString()
            signUpUser(username, password)
            goToMain()
        }

        findViewById<Button>(R.id.Signup_Back).setOnClickListener {
            goToLogin()
        }
    }

    private fun signUpUser(username: String, password: String) {
        // Create the ParseUser
        val user = ParseUser()

        // Set fields for the user to be created
        if (username == "" || password == "") {
            Toast.makeText(this, "Please Enter Username and Password", Toast.LENGTH_SHORT).show()
        } else {
            user.username = username
            user.setPassword(password)

            user.signUpInBackground { e ->
                if (e == null) {
                    // successfully signed in
                    goToMain()
                    Toast.makeText(this, "Successfully Created", Toast.LENGTH_SHORT).show()
                } else {
                    e.printStackTrace()
                    Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    companion object {
        const val TAG = "SIGNUP ACTIVITY"
    }
}