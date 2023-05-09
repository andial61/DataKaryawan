package com.example.datakaryawan

class Data_Karyawan {
    var nama: String? = null
    var nip: String? = null
    var jabatan: String? = null
    var jkel: String? = null
    var key: String? = null

    constructor()

    constructor(nama: String?, nip: String?, jabatan: String?, jkel: String?){
        this.nama = nama
        this.nip = nip
        this.jabatan = jabatan
        this.jkel = jkel
    }
}