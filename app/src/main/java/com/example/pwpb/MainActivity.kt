package com.example.pwpb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn: Button = findViewById(R.id.button)
        val btnNext: Button = findViewById(R.id.btnNext)
        val input: TextView = findViewById(R.id.txtInput)
        val radio: RadioGroup = findViewById(R.id.radioGroup)
        val hasil: TextView = findViewById((R.id.txtHasil))

        val sp: Spinner = findViewById(R.id.spinner)
        var list = arrayOf("Bandung", "Jakarta", "Konoha", "Wakanda")
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
        sp.setAdapter(aa)

        val sw: Switch = findViewById(R.id.switch1)

        btn.setOnClickListener{
            var nama = input.text.toString()
            var paket = radio.checkedRadioButtonId
            var tujuan = sp.selectedItem.toString()
            var asuransi = ""
            if(sw.isChecked){
                asuransi = "Berasuransi"
            }
            hasil.text = "${nama} ${paket} ${tujuan} ${asuransi}"
        }

        btnNext.setOnClickListener {
            val nama = input.text.toString()
            var paket = radio.checkedRadioButtonId
            var tujuan = sp.selectedItem.toString()
            var asuransi = ""
            if(sw.isChecked){
                asuransi = "Berasuransi"
            }
            val intent = Intent(this, secondPage::class.java)
            intent.putExtra("nama", nama)
            intent.putExtra("paket", paket)
            intent.putExtra("tujuan", tujuan)
            intent.putExtra("asuransi", asuransi)
            startActivity(intent)
        }

    }
}