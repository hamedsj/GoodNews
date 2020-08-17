package me.pitok.firebase.repository

import me.pitok.datasource.Writable
import me.pitok.firebase.repository.apis.FcmApiInterface
import me.pitok.networking.BuildConfig
import me.pitok.networking.ifNotSuccessful


class FcmTokenRefresher constructor(private val fcmApiInterface: FcmApiInterface) : Writable.Suspendable<String>{
    override suspend fun write(input: String) {
        return fcmApiInterface.refreshToken(fcm_token =  input).run {
            ifNotSuccessful {
                if (BuildConfig.DEBUG){
                    it.printStackTrace()
                }
            }
        }
    }
}