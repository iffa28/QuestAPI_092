package com.example.project8.ui.view

import com.example.project8.ui.navigation.DestinasiNavigasi

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "Update_mhs"
    override val titleRes = "Edit Mahasiswa"
    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}

