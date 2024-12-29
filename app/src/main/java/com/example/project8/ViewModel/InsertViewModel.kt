package com.example.project8.ViewModel

data class InsertUiEvent(
    val nim: String = "",
    val nama: String = "",
    val alamat: String = "",
    val jenisKelamin: String = "",
    val kelas: String = "",
    val angkatan: String = ""
)

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)
