package com.example.project8.ui.ViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.project8.MahasiswaApplications

fun CreationExtras.aplikasiMahasiswa(): MahasiswaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApplications)