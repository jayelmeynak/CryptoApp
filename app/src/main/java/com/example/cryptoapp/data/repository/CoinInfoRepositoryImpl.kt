package com.example.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.domain.CoinInfo
import com.example.cryptoapp.domain.CoinRepository
import com.example.cryptoapp.mapper.Mapper
import kotlinx.coroutines.delay

class CoinInfoRepositoryImpl(application: Application): CoinRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val mapper = Mapper()
    private val apiService = ApiFactory.apiService
    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return  Transformations.map(coinInfoDao.getPriceList()){
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return Transformations.map(coinInfoDao.getPriceInfoAboutCoin(fromSymbol)){
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true) {
            val topCoins = apiService.getTopCoinsInfo(limit = 50)
            val fSyms = mapper.mapNamesListToString(topCoins)
            val jsonContainer = apiService.getFullPriceList(fSyms = fSyms)
            val coinInfoListDto = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
            val coinInfoListDbModel = coinInfoListDto.map {
                mapper.mapDtoToDbModel(it)
            }
            coinInfoDao.insertPriceList(coinInfoListDbModel)
            delay(10000)
        }
    }
}