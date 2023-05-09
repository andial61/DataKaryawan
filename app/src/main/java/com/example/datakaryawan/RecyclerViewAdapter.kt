package com.example.datakaryawan

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val listData_Karyawan: ArrayList<Data_Karyawan>, context: Context):
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){
    private val context: Context

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val Nama : TextView
        val Nip : TextView
        val Jabatan : TextView
        val Jkel : TextView
        val ListItem : LinearLayout

        init {
            Nama = itemView.findViewById(R.id.nama)
            Nip = itemView.findViewById(R.id.nip)
            Jabatan = itemView.findViewById(R.id.jabatan)
            Jkel = itemView.findViewById(R.id.jkel)
            ListItem = itemView.findViewById(R.id.list_item)
        }
    }
    var listener: data_listener? = null
    interface data_listener {
        fun OnDeleteData(data:Data_Karyawan?, position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val V:View = LayoutInflater.from(parent.context).inflate(R.layout.view_design,
            parent, false)
        return ViewHolder(V)
    }
    @SuppressLint("SetTextI18n", "RecyclerView")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Nama : String? = listData_Karyawan.get(position).nama
        val Nip : String? = listData_Karyawan.get(position).nip
        val Jabatan : String? = listData_Karyawan.get(position).jabatan
        val Jkel : String? = listData_Karyawan.get(position).jkel

        holder.Nama.text = "Nama : $Nama"
        holder.Nip.text = "Nomor Induk Pegawai : $Nip"
        holder.Jabatan.text = "Jabatan : $Jabatan"
        holder.Jkel.text = "Jenis Kelamin : $Jkel"
        holder.ListItem.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                holder.ListItem.setOnLongClickListener {view->
                    val action = arrayOf("Update","Delete")
                    val alert: AlertDialog.Builder = AlertDialog.Builder(view.context)
                    alert.setItems(action, DialogInterface.OnClickListener{ dialog, i ->
                        when (i) {
                            0-> {
                                val bundle = Bundle()
                                bundle.putString("dataNama",listData_Karyawan[position].nama)
                                bundle.putString("dataNip",listData_Karyawan[position].nip)
                                bundle.putString("dataJabatan",listData_Karyawan[position].jabatan)
                                bundle.putString("dataJkel",listData_Karyawan[position].jkel)
                                bundle.putString("getPrimaryKey",listData_Karyawan[position].key)
                                val intent = Intent(view.context, UpdateData::class.java)
                                intent.putExtras(bundle)
                                context.startActivity(intent)
                            }
                            1->{
                                listener?.OnDeleteData(listData_Karyawan[position],position)
                            }
                        }
                    })
                    alert.create()
                    alert.show()
                    true
                }
                return true
            }
        })
    }
    override fun getItemCount(): Int {
        return listData_Karyawan.size
    }

    init {
        this.context = context
        this.listener= context as MyListData
    }
}