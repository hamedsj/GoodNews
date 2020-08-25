package me.pitok.firebase.repository

import me.pitok.datasource.Writable
import me.pitok.firebase.repository.apis.FcmApiInterface
import me.pitok.networking.*
import java.io.IOException


class FcmTokenRefresher constructor(private val fcmApiInterface: FcmApiInterface) : Writable.Suspendable<String>{
    override suspend fun write(input: String) {
        try {
            fcmApiInterface.refreshToken(fcm_token =  input)
        }catch (t: Throwable){
            if (BuildConfig.DEBUG){
                t.printStackTrace()
            }
        }
    }
}