package com.example.pwpb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class secondPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_page)
        val nama = intent.getStringExtra("nama")
        val paket = intent.getStringExtra("paket")
        val tujuan = intent.getStringExtra("tujuan")
        val asuransi = intent.getStringExtra("asuransi")

//        val txtNama : TextView = findViewById(R.id.txtNama)
//        val txtPaket : TextView = findViewById(R.id.txtPaket)
//        val txtTujuan : TextView = findViewById(R.id.txtTujuan)
//        val txtAsuransi : TextView = findViewById(R.id.txtAsuransi)
//
//        txtNama.text = nama
//        txtPaket.text = paket.toString()
//        txtTujuan.text = tujuan
//        txtAsuransi.text = asuransi


    }
}