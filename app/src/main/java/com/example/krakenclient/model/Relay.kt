package com.example.krakenclient.model

import com.google.gson.annotations.SerializedName

data class RelaysResponse(@SerializedName("relay-devices")val relays: List<Relay>)

data class Relay(val device: String, val pin: Int, val state: String) {
    val isEnabled: Boolean
        get() = state == RelayStatus.ENABLED.name
}

enum class RelayType { LIGHT, PUMP, HUMIDIFIER, EXHAUST }

enum class RelayStatus { ENABLED, DISABLED }
