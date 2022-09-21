package com.example.pwpb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        val user = auth.currentUser

        if(user != null){
            Log.d("login", "udah login sebelumnya")
            val intent = Intent(this, layout_utama::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }


        val nama : TextView = findViewById(R.id.inputNama)
        val pass : TextView = findViewById(R.id.inputPass)
        val btn: Button = findViewById(R.id.btnLg)
        val forgot: TextView = findViewById(R.id.forgot)
        val ipt = nama.text
        val ps = pass.text

        val namaConst = "admin"
        val passConst = "adminpass"

        forgot.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }

        btn.setOnClickListener{
            Log.d("tag", namaConst)
            Log.d("tag", passConst)
            Log.d("tag", ipt.toString())
            Log.d("tag", ps.toString())

            auth.signInWithEmailAndPassword(ipt.toString(), ps.toString())
                .addOnCompleteListener(this) { task ->
                    if(task.isSuccessful){
                        Toast.makeText(this, "Berhasil Masuk", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, layout_utama::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Username/Password Salah", Toast.LENGTH_LONG).show()
                    }
                }


        }

    }
}