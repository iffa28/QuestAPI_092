package com.example.project8.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project8.repository.MahasiswaRepository
import kotlinx.coroutines.launch

class UpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val mahasiswaRepository: MahasiswaRepository
) : ViewModel() {
    var updateMhsUiState by mutableStateOf(InsertUiState())
    private set
    val nim: String = checkNotNull(savedStateHandle["nim"])

    init {
        viewModelScope.launch {
            updateMhsUiState = mahasiswaRepository.getMahasiswaById(nim).toUiStateMhs()
        }
    }

    fun updateInsertMhsState(insertUiEvent: InsertUiEvent){
        updateMhsUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun  updateMhs(){
        viewModelScope.launch {
            try {
                mahasiswaRepository.updateMahasiswa(nim, updateMhsUiState.insertUiEvent.toMhs())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}