package com.example.project8.depedenciesInjection

import com.example.project8.repository.MahasiswaRepository

interface AppContainer{
    val mahasiswaRepository: MahasiswaRepository
}

