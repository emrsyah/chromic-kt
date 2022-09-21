package com.example.pwpb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPassword : AppCompatActivity() {
    val auth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val btnBack: TextView = findViewById(R.id.btnBackLogin)
        val btnReset: AppCompatButton = findViewById(R.id.btnResetPass)
        val emailV: TextView = findViewById(R.id.inputEmail)
        val email = emailV.text

        btnBack.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

        btnReset.setOnClickListener {
            if(email.isNotBlank()) {
                auth.sendPasswordResetEmail(email.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Email Reset Password Dikirim", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
            } else{
                Toast.makeText(this, "Isi Email Terlebih Dahulu", Toast.LENGTH_LONG)
                    .show()
            }
        }


    }
}