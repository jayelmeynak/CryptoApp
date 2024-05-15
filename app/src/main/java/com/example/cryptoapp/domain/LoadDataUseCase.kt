package com.example.cryptoapp.domain

class LoadDataUseCase(private val repository: CoinRepository) {

    operator suspend fun invoke() = repository.loadData()
}