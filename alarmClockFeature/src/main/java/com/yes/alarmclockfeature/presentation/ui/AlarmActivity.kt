package com.yes.alarmclockfeature.presentation.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.yes.alarmclockfeature.databinding.AlarmSetScreenBinding
import com.yes.alarmclockfeature.di.components.AlarmClockComponent
import com.yes.alarmclockfeature.domain.usecase.SetTracksToPlayerPlaylistUseCase
import com.yes.core.di.component.BaseComponent
import com.yes.core.domain.models.DomainResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Deprecated("do not use this")
class AlarmActivity : AppCompatActivity(){

    private lateinit var binding: ViewBinding
    private val binder by lazy {
        binding as AlarmSetScreenBinding
    }
    private lateinit var component: BaseComponent

    private val getCurrentPlaylistTracksUseCase by lazy {
        (component as AlarmClockComponent).getGetCurrentPlaylistTracksUseCase()
    }
    private val setTracksToPlayerPlaylistUseCase by lazy {
        (component as AlarmClockComponent).getSetTracksToPlayerPlaylistUseCase()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        showWhenLockedAndTurnScreenOn()
        super.onCreate(savedInstanceState)
        checkPermissions()
        //setFragments()
        Toast.makeText(this, "result ", Toast.LENGTH_SHORT).show()
      //  turnScreenOnAndKeyguardOff()
    }

    override fun onStart() {
        super.onStart()

       // run()
    }
    private fun run(){
        component=(applicationContext as AlarmsScreen.DependencyResolver).getComponent()

        CoroutineScope(Dispatchers.Main).launch {

            val result = getCurrentPlaylistTracksUseCase()
            println("result getCurrentPlaylistTracksUseCase")
          //  Toast.makeText(this, "result ", Toast.LENGTH_SHORT).show()
            when (result) {
                is DomainResult.Success -> {
                    println("result SUCCESS")
                    setTracksToPlayerPlaylistUseCase(
                        SetTracksToPlayerPlaylistUseCase.Params(
                            result.data
                        )
                    )
                }
                is DomainResult.Error -> TODO()
            }
        }
    }
    private fun showWhenLockedAndTurnScreenOn() {
        val powerManager = this.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!powerManager.isInteractive) { // if screen is not already on, turn it on (get wake_lock)
            @SuppressLint("InvalidWakeLockTag") val wl = powerManager.newWakeLock(
                PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE or PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
                "id:wakeupscreen"
            )
            wl.acquire()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
    }

   /* private fun Activity.turnScreenOnAndKeyguardOff() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
            )
        }

        with(getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                requestDismissKeyguard(this@turnScreenOnAndKeyguardOff, null)
            }
        }
    }*/
    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
        } else {
            packageManager.getPackageInfo(
                packageName, PackageManager.PackageInfoFlags.of(
                    PackageManager.GET_PERMISSIONS.toLong()
                )
            )
        }?.requestedPermissions?.filter { permission ->
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    packageManager.getPermissionInfo(
                        permission,
                        PackageManager.GET_META_DATA
                    ).protection == PermissionInfo.PROTECTION_DANGEROUS
                } else {
                    packageManager.getPermissionInfo(
                        permission,
                        PackageManager.GET_META_DATA
                    ).protectionLevel == PermissionInfo.PROTECTION_DANGEROUS
                }
            } catch (e: Exception) {
                return@filter false
            }
        }?.filter { dangerousPermission ->
            ContextCompat.checkSelfPermission(
                this,
                dangerousPermission
            ) == PackageManager.PERMISSION_DENIED
        }?.let {
            if (it.isNotEmpty()) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, it[0])) {
                    Toast.makeText(this, "Grant all needed permissions!!!", Toast.LENGTH_LONG)
                        .show()
                    ActivityCompat.requestPermissions((this as Activity), it.toTypedArray(), 1)
                } else {
                    ActivityCompat.requestPermissions((this as Activity), it.toTypedArray(), 1)
                }
            } else {
                setFragments()
                run()
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissions.filterIndexed { index, _ ->
            grantResults[index] == PackageManager.PERMISSION_DENIED
        }.let {
            if (it.isNotEmpty()) {
                Toast.makeText(
                    this,
                    "Player is unavailable because the feature requires a permission that the you has denied.",
                    Toast.LENGTH_LONG
                ).show()
                // checkPermissions()
            } else {
                setFragments()
            }

            // checkPermissions()
        }

    }

    private fun setFragments() {

        binding = AlarmSetScreenBinding.inflate(layoutInflater)
        // supportFragmentManager.fragmentFactory = fragmentFactory
        val view = binding.root
        setContentView(view)


    }


}