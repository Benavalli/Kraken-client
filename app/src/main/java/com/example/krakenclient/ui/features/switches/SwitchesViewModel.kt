package com.example.krakenclient.ui.features.switches

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.krakenclient.data.RelayStateRequest
import com.example.krakenclient.data.RelayStatus
import com.example.krakenclient.data.RelayType
import com.example.krakenclient.repository.KrakenServerRepository
import io.reactivex.disposables.CompositeDisposable

class SwitchesViewModel(private val krakenServerRepository: KrakenServerRepository) : ViewModel() {

    companion object {
        private const val TAG = "SwitchesViewModel"
    }

    private val subscription = CompositeDisposable()
    val lightRelay: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val pumpRelay: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val humidifierRelay: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val exhaustRelay: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun getRelays() {
        subscription.add(
            krakenServerRepository.getRelays().subscribe({ devices ->
                devices.relays.forEach {
                    when (it.device) {
                        RelayType.LIGHT.name -> lightRelay.value = it.isEnabled
                        RelayType.PUMP.name -> pumpRelay.value = it.isEnabled
                        RelayType.HUMIDIFIER.name -> humidifierRelay.value = it.isEnabled
                        RelayType.EXHAUST.name -> exhaustRelay.value = it.isEnabled
                    }
                }
            }, { throwable -> Log.e(TAG, throwable.message ?: "") })
        )
    }

    fun changeLightRelayState(isEnabled: Boolean) {
        subscription.add(
            krakenServerRepository
                .changeLightRelayState(RelayStateRequest(isEnabled.toServerState()))
                .doOnError { Log.e(TAG, it.message ?: "") }
                .subscribe()
        )
    }

    fun changePumpRelayState(isEnabled: Boolean) {
        subscription.add(
            krakenServerRepository
                .changePumpRelayState(RelayStateRequest(isEnabled.toServerState()))
                .doOnError { Log.e(TAG, it.message ?: "") }
                .subscribe()
        )
    }

    fun changeHumidifierRelayState(isEnabled: Boolean) {
        subscription.add(
            krakenServerRepository
                .changeHumidifierRelayState(RelayStateRequest(isEnabled.toServerState()))
                .doOnError { Log.e(TAG, it.message ?: "") }
                .subscribe()
        )
    }

    fun changeExhaustRelayState(isEnabled: Boolean) {
        subscription.add(
            krakenServerRepository
                .changeExhaustRelayState(RelayStateRequest(isEnabled.toServerState()))
                .doOnError { Log.e(TAG, it.message ?: "") }
                .subscribe()
        )
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun Boolean.toServerState() =
        if (this) RelayStatus.ENABLED.name else RelayStatus.DISABLED.name
}
