package com.andriiginting.common_di

import android.app.Activity

fun Activity.muviAppDaggerComponent(): MuviAppComponent {
    return (this.application as MuviDepsProvider).appDeps
}