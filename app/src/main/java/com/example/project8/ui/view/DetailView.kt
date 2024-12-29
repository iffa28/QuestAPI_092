package com.example.project8.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project8.model.Mahasiswa
import com.example.project8.ui.viewModel.DetailMhsUiState

@Composable
fun DetailStatus(
    mhsUiState: DetailMhsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
){
    when(mhsUiState){
        is DetailMhsUiState.Success -> {
            DetailMhsLayout(
                mahasiswa = mhsUiState.mahasiswa,
                modifier = modifier,
                onEditClick = {onEditClick(it)}
            )
        }
        is DetailMhsUiState.Loading -> OnLoading(modifier = modifier)
        is DetailMhsUiState.Error -> OnError(retryAction, modifier = modifier)
    }
}

@Composable
fun DetailMhsLayout(
    mahasiswa: Mahasiswa,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {}
) {
    Column(){
        ItemDetailMhs(
            mahasiswa = mahasiswa,
            modifier = Modifier
                .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            onClick = {
                onEditClick(mahasiswa.nim)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){
            Text(text = "Edit")
        }
    }

}

@Composable
fun ItemDetailMhs(
    modifier: Modifier = Modifier,
    mahasiswa: Mahasiswa
)
{
    Column(modifier = Modifier.padding(16.dp)
    ) {
        ComponentDetailMhs(judul = "Nama Mahasiswa", isinya = mahasiswa.nama)
        Spacer(modifier = Modifier.padding(4.dp))

        ComponentDetailMhs(judul = "NIM", isinya = mahasiswa.nim)
        Spacer(modifier = Modifier.padding(4.dp))

        ComponentDetailMhs(judul = "Jenis Kelamin", isinya = mahasiswa.jenisKelamin)
        Spacer(modifier = Modifier.padding(4.dp))

        ComponentDetailMhs(judul = "Alamat", isinya = mahasiswa.alamat)
        Spacer(modifier = Modifier.padding(4.dp))

        ComponentDetailMhs(judul = "Kelas", isinya = mahasiswa.kelas)
        Spacer(modifier = Modifier.padding(4.dp))

        ComponentDetailMhs(judul = "Angkatan", isinya = mahasiswa.angkatan)
        Spacer(modifier = Modifier.padding(4.dp))
    }
}

@Composable
fun ComponentDetailMhs(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )

    }

}