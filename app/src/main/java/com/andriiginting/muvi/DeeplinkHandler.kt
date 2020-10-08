package com.andriiginting.muvi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.DeepLinkHandler
import com.andriiginting.muvi.detail.MuviDetailDeepLinkModule
import com.andriiginting.muvi.detail.MuviDetailDeepLinkModuleLoader

@DeepLinkHandler(
    AppDeeplinkModule::class,
    MuviDetailDeepLinkModule::class
)
class DeeplinkHandlerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DeepLinkDelegate(
            AppDeeplinkModuleLoader(),
            MuviDetailDeepLinkModuleLoader()
        ).dispatchFrom(this)
        finish()

    }
}