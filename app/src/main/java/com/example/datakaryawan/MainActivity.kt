package com.example.datakaryawan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logout.setOnClickListener(this)
        save.setOnClickListener(this)
        lihat.setOnClickListener(this)

        auth= FirebaseAuth.getInstance()
        setDataJabatan()
    }

    private fun setDataJabatan() {
        val adapter = ArrayAdapter.createFromResource(this, R.array.Jabatan, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        jabatan.adapter = adapter
    }

    private fun isEmpty(s: String): Boolean{
        return TextUtils.isEmpty(s)
    }
    private fun getJkel(): String{
        var jenisKelamin = ""
        jenisKelamin = if (man.isChecked){
            "Laki-laki"
        } else {
            "Perempuan"
        }
        return jenisKelamin
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.save->{
                val getUserID = auth!!.currentUser!!.uid
                val database = FirebaseDatabase.getInstance()

                val getNama: String = nama.text.toString()
                val getNip: String = nip.text.toString()
                val getJabatan: String = jabatan.selectedItem.toString()
                val getJkel: String = getJkel()

                val getReference: DatabaseReference = database.reference

                if (isEmpty(getNama) || isEmpty(getNip) || isEmpty(getJkel) ){
                    Toast.makeText(this@MainActivity, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }else{
                    getReference.child("Admin").child(getUserID).child("Data Karyawan").push()
                        .setValue(Data_Karyawan(getNama, getNip, getJabatan, getJkel))
                        .addOnCompleteListener(this){
                            nama.setText("")
                            nip.setText("")
                            man.isChecked = false
                            woman.isChecked = false
                            Toast.makeText(this@MainActivity, "Data Tersimpan", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            R.id.logout->{
                AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener {
                        Toast.makeText(this@MainActivity, "Logout Berhasil", Toast.LENGTH_SHORT)
                            .show()
                        intent = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
            }
            R.id.lihat->{
                startActivity(Intent(this@MainActivity, MyListData::class.java))
            }
        }
    }
}