package com.example.project8.ui.viewModel

import com.example.project8.model.Mahasiswa

// Kelas sealed untuk merepresentasikan berbagai status UI pada DetailMhs
sealed class DetailMhsUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailMhsUiState()
    object Error : DetailMhsUiState()
    object Loading : DetailMhsUiState()
}

