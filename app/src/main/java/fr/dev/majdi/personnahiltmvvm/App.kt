package fr.dev.majdi.personnahiltmvvm

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.InternetObservingSettings
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.strategy.SocketInternetObservingStrategy
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *Created by Majdi RABEH on 24/08/2020
 *Email = "m.rabeh.majdi@gmail.com")
 */
@SuppressLint("CheckResult")
@HiltAndroidApp
class App : Application() {

    companion object {
        var TAG = App::class.java.simpleName
        var isInternetConnected = false

        private var onInternetConnectivity: OnInternetConnectivity? = null

        fun setOnInternetConnectivity(onInternetConnectivity: OnInternetConnectivity?) {
            this.onInternetConnectivity = onInternetConnectivity
        }
    }

    override fun onCreate() {
        super.onCreate()
        checkInternetConnexion()
    }

    /**
     * Observing state of internet connexion using Reactive Network library
     */

    private fun checkInternetConnexion() {

        val settings = InternetObservingSettings.builder()
            .host("www.google.com")
            .strategy(SocketInternetObservingStrategy())
            .build()

        ReactiveNetwork
            .observeInternetConnectivity(settings)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isConnected: Boolean ->
                Log.e(TAG, "isInternetConnected $isConnected")
                if (onInternetConnectivity != null)
                    onInternetConnectivity!!.observeInternetConnectivity(isConnected)

                isInternetConnected = isConnected
                if (!isInternetConnected) {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.no_internet),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    interface OnInternetConnectivity {
        fun observeInternetConnectivity(isConnected: Boolean)
    }

}