package com.example.final_project.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.final_project.R
import com.parse.ParseException
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (ParseUser.getCurrentUser() != null) {
            goToMainActivity()
        }

        findViewById<Button>(R.id.LoginActivity_Login).setOnClickListener {
            val username = findViewById<EditText>(R.id.LoginActivity_Username).text.toString()
            val password = findViewById<EditText>(R.id.LoginActivity_Password).text.toString()
            this.loginUser(username, password)
        }

        findViewById<Button>(R.id.LoginActivity_SignUp).setOnClickListener {
            gotToSignUp()
        }
    }

    private fun loginUser(username: String, password: String) {
        ParseUser.logInInBackground(
            username, password, ({
                    user: ParseUser?, e: ParseException? ->
                Log.i("Login", user.toString())
                if (user != null) {
                    goToMainActivity()
                } else {
                    e?.printStackTrace()
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                }
            })
        )
    }

    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun gotToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}