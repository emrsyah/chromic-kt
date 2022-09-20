package com.example.pwpb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class Profiles : AppCompatActivity() {
    private lateinit var image: ImageView
    private lateinit var emailV: TextView
    val auth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiles)
        setLayout()

        val reset : AppCompatButton = findViewById(R.id.btnReset)
        val logout : AppCompatButton = findViewById(R.id.btnLogout)

        reset.setOnClickListener{
            resetPassword()
        }

        logout.setOnClickListener{
            logout()
        }

    }

    private fun logout(){
        Log.d("clicked", "logout")
        auth.signOut()
        val intent = Intent(this, Login::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun resetPassword(){
        Log.d("clicked", "reset")
        val email: String = intent.getStringExtra("email").toString();
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email Reset Password Dikirim", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun setLayout(){
        image = findViewById(R.id.imgProfile)
        emailV= findViewById(R.id.txtEmail)
        if(intent.hasExtra("image_url") && intent.hasExtra("email")) {
            val imageUrl: String = intent.getStringExtra("image_url").toString();
            val email: String = intent.getStringExtra("email").toString();
            emailV.text = email
            Picasso.get().load(imageUrl).into(image);
        }
    }
}