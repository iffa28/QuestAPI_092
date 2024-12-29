package com.example.project8.ui.ViewModel

import com.example.project8.model.Mahasiswa

sealed class HomeUiState{
    data class Success(val mahasiswa: List<Mahasiswa>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}


