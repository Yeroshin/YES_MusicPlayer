package com.yes.trackdialogfeature

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.yes.trackdialogfeature.api.Dependency
import com.yes.trackdialogfeature.presentation.ui.TrackDialog

class MockFragmentFactoryImpl (
    private val dep: Dependency
    ) : FragmentFactory() {

        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            return when (loadFragmentClass(classLoader, className)) {
                TrackDialog::class.java -> TrackDialog(dep)
                else -> super.instantiate(classLoader, className)
            }
        }
}