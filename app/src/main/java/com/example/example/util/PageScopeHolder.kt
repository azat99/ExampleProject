package com.example.example.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope

interface PageScopeHolder {

    val pageScope: LifecycleCoroutineScope

    val pageLifecycle: Lifecycle
}