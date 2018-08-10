package io.flutter.dof

import android.app.Application
import io.flutter.facade.Flutter

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Flutter.startInitialization(this)
    }
}