package com.example.project8.model

import kotlinx.serialization.SerialName

data class Mahasiswa(
    val nim: String,
    val nama: String,
    val alamat: String,

    @SerialName("jenis_kelamin")
    val jenisKelamin: String,

    val kelas: String,
    val angkatan: String
)