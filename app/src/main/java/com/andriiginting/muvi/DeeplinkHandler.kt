package com.andriiginting.muvi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.BaseDeepLinkDelegate
import com.airbnb.deeplinkdispatch.DeepLinkHandler
import com.airbnb.deeplinkdispatch.Parser
import com.andriiginting.muvi.detail.MuviDetailDeepLinkModule
import com.andriiginting.muvi.detail.MuviDetailDeepLinkModuleLoader

@DeepLinkHandler(
    AppDeeplinkModule::class,
    MuviDetailDeepLinkModule::class
)
class DeeplinkHandlerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseDeepLinkDelegate(
            listOfNotNull(
                AppDeeplinkModuleLoader(),
                MuviDetailDeepLinkModuleLoader(),
                loadDeepLinkLoader(DYNAMIC_FEATURE_FAVORITE_MODULE)
            )
        ).dispatchFrom(this)
        finish()
    }

    //use reflection to support dynamic module deeplink
    private fun loadDeepLinkLoader(loadClassName: String): Parser? {
        return try {
            Class.forName(loadClassName).newInstance() as Parser
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        private const val DYNAMIC_FEATURE_FAVORITE_MODULE =
            "com.andriiginting.favorite.MuviFavoriteDeeplinkModuleLoader"
    }
}