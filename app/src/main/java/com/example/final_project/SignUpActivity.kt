package com.example.final_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class SignUpActivity : AppCompatActivity() {

     override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_sign_up)

        findViewById<Button>(R.id.Signup_SignUp).setOnClickListener {

        }

        findViewById<Button>(R.id.Signup_Back).setOnClickListener {

        }
     }

    private fun goToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    companion object {
        const val TAG = "SIGNUP ACTIVITY"
    }
}