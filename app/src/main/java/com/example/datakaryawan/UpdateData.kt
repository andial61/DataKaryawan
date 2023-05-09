package com.example.datakaryawan

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.man
import kotlinx.android.synthetic.main.activity_update_data.*

class UpdateData : AppCompatActivity() {
    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? =  null
    private var cekNama: String? = null
    private var cekNip: String? = null
    private var cekJabatan: String? = null
    private var cekJkel: String? = null

    private fun getJkel(): String{
        val jenisKelamin: String = if (man.isChecked){
            "Laki-laki"
        } else {
            "Perempuan"
        }
        return jenisKelamin
    }
    private fun setDataJabatan() {
        val adapter = ArrayAdapter.createFromResource(this, R.array.Jabatan, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        newjabatan.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        supportActionBar!!.title = "Update Data"
        get()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        setDataJabatan()
        update.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                cekNama = newnama.text.toString()
                cekNip = newnip.text.toString()
                cekJabatan = newjabatan.selectedItem.toString()
                val getJkel : String = getJkel()

                if (TextUtils.isEmpty(cekNama) || TextUtils.isEmpty(cekNip) || TextUtils.isEmpty(cekJabatan) || TextUtils.isEmpty(getJkel)
                ){
                    Toast.makeText(this@UpdateData, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }else{
                    val setData_Karyawan = Data_Karyawan()
                    setData_Karyawan.nama = newnama.text.toString()
                    setData_Karyawan.nip = newnip.text.toString()
                    setData_Karyawan.jabatan = newjabatan.selectedItem.toString()
                    setData_Karyawan.jkel = getJkel()
                    updateKaryawan(setData_Karyawan)
                }
            }
        })
    }
    private fun get() {
        val getNama = intent.extras!!.getString("dataNama")
        val getNip = intent.extras!!.getString("dataNip")
        val getJabatan = intent.extras!!.getString("dataJabatan")
        val getJkel = intent.extras!!.getString("dataJkel")

        newnama!!.setText(getNama)
        newnip!!.setText(getNip)
        newjabatan!!.tag = getJabatan

        if (getJkel == "Laki-laki"){
            man.isChecked = true
        } else  {
            (getJkel == "Perempuan")
        }
    }
    private val data: Unit
        get() {
            val getNama = intent.extras!!.getString("dataNama")
            val getNip = intent.extras!!.getString("dataNip")
            val getJabatan = intent.extras!!.getString("dataJabatan")
            val getJkel = intent.extras!!.getString("dataJkel")

            newnama!!.setText(getNama)
            newnip!!.setText(getNip)
            newjabatan!!.tag = getJabatan

            if (getJkel == "Laki-laki"){
                man.isChecked = true
            } else  {
                (getJkel == "Perempuan")
            }
        }
    private fun updateKaryawan(setData_Karyawan: Data_Karyawan) {
        val userID = auth!!.uid
        val getkey =intent.extras!!.getString("getPrimaryKey")
        database!!.child("Admin")
            .child(userID!!)
            .child("Data Karyawan")
            .child(getkey!!)
            .setValue(setData_Karyawan)
            .addOnSuccessListener {
                newnama!!.setText("")
                newnip!!.setText("")
                newjabatan!!.tag = ""
                if (getJkel() == "Laki-laki"){
                    man.isChecked = true
                } else  {
                    (getJkel() == "Perempuan")
                }

                Toast.makeText(this@UpdateData,"Data Berhasil Diubah",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}