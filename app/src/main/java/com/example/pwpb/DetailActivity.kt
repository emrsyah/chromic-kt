package com.example.pwpb;

import android.content.DialogInterface
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.squareup.picasso.Picasso;

public class DetailActivity : AppCompatActivity() {
    private val TAG: String = "DetailActivity";
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        Log.d("activity", "aktifitas")
        getIncomingIntent()

        val trash: ImageView = findViewById(R.id.trash)
        val edit: ImageView = findViewById(R.id.edit)

        trash.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Apakah anda yakin menghapus data?")
                .setCancelable(true)
                .setPositiveButton("Konfirmasi", DialogInterface.OnClickListener { dialog, i -> run {
                    deleteHandler()
                    finish()
                } })
                .setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, i ->  dialog.cancel()})
            val alert = dialogBuilder.create()
            alert.setTitle("Konfirmasi Penghapusan")
            alert.show()
        }

        edit.setOnClickListener {
            editHandler()
        }
    }

    private fun deleteHandler(){
        val id = intent.getStringExtra("id").toString()
        db.collection("characters").document(id)
            .delete()
            .addOnSuccessListener { Log.d("hapus 3", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w("hapus gagal", "Error deleting document", e) }
    }

    private fun editHandler(){
        val imageUrl: String = intent.getStringExtra("image_url").toString();
        val name: String = intent.getStringExtra("name").toString();
        val desc: String = intent.getStringExtra("desc").toString();
        val id: String = intent.getStringExtra("id").toString();
        val origin: String = intent.getStringExtra("origin").toString()
        val bottomSheetFragment = BottomSheetFragment(true, name, desc, imageUrl, id, origin)
        bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
    }

    private fun getIncomingIntent(){
        Log.d(TAG, "getIncoming intent");
        if(intent.hasExtra("image_url") && intent.hasExtra("name") && intent.hasExtra("desc")){
            Log.d(TAG, "found intent extra");
            val imageUrl: String = intent.getStringExtra("image_url").toString();
            val name: String = intent.getStringExtra("name").toString();
            val desc: String = intent.getStringExtra("desc").toString();
            val origin: String = intent.getStringExtra("origin").toString()
            setImage(imageUrl, name, desc, origin);
        } else{
            Log.d(TAG, "doesnt found any intent extra");
        }
    }

    private fun setImage(imageUrl: String, nameP: String, desc: String, origin: String){
        val newUrl = imageUrl.replace("medium", "fantastic");
//        Log.d(TAG, newUrl);
        val name: TextView = findViewById(R.id.name);
        name.text = nameP;

        val img: ImageView = findViewById(R.id.image);
        Picasso.get().load(newUrl).into(img);

        val descKu: TextView = findViewById(R.id.desc1);
        descKu.text = desc;

        val originV : AppCompatButton =  findViewById(R.id.detOrigin)
        originV.text = origin

        if(origin == "DC"){
            originV.backgroundTintList = this.resources.getColorStateList(R.color.pallete);
        } else{
            originV.backgroundTintList = this.resources.getColorStateList(R.color.redcolor);
        }
    }




//    private void

}
