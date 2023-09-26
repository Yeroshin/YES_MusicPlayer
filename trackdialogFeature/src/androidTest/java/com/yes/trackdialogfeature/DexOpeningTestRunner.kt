package com.yes.trackdialogfeature

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.test.runner.AndroidJUnitRunner
import com.github.tmurakami.dexopener.DexOpener


class DexOpeningTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        // We only need to enable DexOpener for < 28
        // MockK supports for mocking final classes on Android 9+.
        Log.i(ContentValues.TAG, "/////test:DexOpeningTestRunner///// ")
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            DexOpener.install(this)
            Log.i(ContentValues.TAG, "////test:DexOpeningTestRunner running/////")

        }
        return super.newApplication(cl, className, context)
    }
}