package com.example.pwpb

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_layout_utama.*
import kotlinx.android.synthetic.main.bottomsheet_fragment.*

class BottomSheetFragment(isEdit: Boolean = false, name: String = "", desc: String = "", imageUrl: String = "", id:String = "", origin:String = ""): BottomSheetDialogFragment() {
    val modeEdit =  isEdit
    val nameKu = name
    val descKu = desc
    val imageUrlKu = imageUrl
    val idKu = id
    val originKu = origin
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        Log.d("init", mode.toString())
        super.onViewCreated(view, savedInstanceState)
        val db = Firebase.firestore
        val vUrl : TextView = view.findViewById(R.id.iptUrl)
        val vName : TextView = view.findViewById(R.id.iptNama)
        val vDesc : TextView = view.findViewById(R.id.iptDesc)
        val spinner : Spinner = view.findViewById(R.id.spinnerOrigin)

        ArrayAdapter.createFromResource(
            view.context,
            R.array.origin_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        if(modeEdit){
            vUrl.text = imageUrlKu
            vName.text = nameKu
            vDesc.text = descKu
            btnAdd.text = "Edit"
            if(originKu == "Marvel") {
                spinner.setSelection(0)
            } else { spinner.setSelection(1) }
        }



        btnAdd.setOnClickListener{
//            Log.d("fab", "fab ditekan 2")
            val url = vUrl.text
            val name = vName.text
            val desc = vDesc.text
            val origin = spinner.selectedItem.toString()
            Log.d("spinner", origin)
            if(url.isNotBlank() && name.isNotBlank() && desc.isNotBlank()){
                if(modeEdit){
                    val data = hashMapOf(
                        "image_url" to url.toString(),
                        "name" to name.toString(),
                        "desc" to desc.toString(),
                        "origin" to origin
                    )
                    Log.d("update", data.toString())
                    Log.d("update", idKu)
                    db.collection("characters").document(idKu)
                        .set(data)
                        .addOnSuccessListener {
                            Log.d("update", "DocumentSnapshot successfully written!")
                            Toast.makeText(context, "Data Diubah", Toast.LENGTH_LONG).show()
                            vUrl.text = ""
                            vName.text = ""
                            vDesc.text = ""
                            spinner.setSelection(0)
                            this.dismiss()
                        }
                        .addOnFailureListener { e ->
                            Log.w("update", "Error writing document", e)
                            Toast.makeText(context, "Terjadi Error", Toast.LENGTH_LONG).show()
                        }

                } else {
                    val data = hashMapOf(
                        "image_url" to url.toString(),
                        "name" to name.toString(),
                        "desc" to desc.toString(),
                        "origin" to origin
                    )
                    Log.d("fab", data.toString())
                    db.collection("characters")
                        .add(data)
                        .addOnSuccessListener { documentReference ->
                            Log.d(
                                "fab",
                                "DocumentSnapshot written with ID: ${documentReference.id}"
                            )
                            Toast.makeText(context, "Data Ditambahkan", Toast.LENGTH_LONG).show()
                            vUrl.text = ""
                            vName.text = ""
                            vDesc.text = ""
                            spinner.setSelection(0)
                            this.dismiss()
                        }
                        .addOnFailureListener { e ->
                            Log.w("fab", "Error adding document", e)
                            Toast.makeText(context, "Terjadi Error", Toast.LENGTH_LONG).show()

                        }
                }
            }else{
                Toast.makeText(context, "Tolong isi semuanya", Toast.LENGTH_LONG).show()
            }

        }
    }
}