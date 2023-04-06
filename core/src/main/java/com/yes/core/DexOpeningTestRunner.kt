package com.yes.core

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.test.runner.AndroidJUnitRunner


class DexOpeningTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        // We only need to enable DexOpener for < 28
        // MockK supports for mocking final classes on Android 9+.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
           // DexOpener.install(this)
        }
        return super.newApplication(cl, className, context)
    }
}