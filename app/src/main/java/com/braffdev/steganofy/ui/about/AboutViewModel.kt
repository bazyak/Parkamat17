package com.braffdev.steganofy.ui.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.braffdev.steganofy.BuildConfig
import com.braffdev.steganofy.R
import com.braffdev.steganofy.ui.common.SingleLiveEvent
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

class AboutViewModel(val applicationContext: Context) {

    val liveEventStartActivity = SingleLiveEvent<Intent>()
    val liveDataAppInfo = MutableLiveData<String>()

    fun initialize() {
        val versionName: String = BuildConfig.VERSION_NAME
        val versionBuild: String = BuildConfig.VERSION_CODE.toString()
        liveDataAppInfo.value = applicationContext.getString(R.string.about_version, versionName, versionBuild)
    }

    fun emailClicked() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:ruslan@bazyak.com?subject=Parkamat 17")
        liveEventStartActivity.value = intent
    }

    fun gitHubClicked() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://github.com/bazyak/Parkamat17")
        liveEventStartActivity.value = intent
    }

    fun licensesClicked() {
        liveEventStartActivity.value = Intent(applicationContext, OssLicensesMenuActivity::class.java)
    }

}