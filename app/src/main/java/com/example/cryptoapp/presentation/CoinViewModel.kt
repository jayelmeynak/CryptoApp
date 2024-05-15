package com.example.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.network.model.CoinInfoDto
import com.example.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

//    fun getDetailInfo(fSym: String): LiveData<CoinInfoDto> {
//        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
//    }

    init {
        loadData()
    }

    private fun loadData() {
//        val disposable = ApiFactory.apiService.getTopCoinsInfo(limit = 50)
//            .map { it.names?.map { it.coinInfo?.name }?.joinToString(",") }
//            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
//            .map { getPriceListFromRawData(it) }
//            .delaySubscription(10, TimeUnit.SECONDS)
//            .repeat()
//            .retry()
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//                db.coinPriceInfoDao().insertPriceList(it)
//                Log.d("TEST_OF_LOADING_DATA", "Success: $it")
//            }, {
//                Log.d("TEST_OF_LOADING_DATA", "Failure: ${it.message}")
//            })
//        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRawData(
        coinPriceInfoRawData: CoinInfoJsonContainerDto
    ): List<CoinInfoDto> {
        val result = ArrayList<CoinInfoDto>()
        val jsonObject = coinPriceInfoRawData.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}