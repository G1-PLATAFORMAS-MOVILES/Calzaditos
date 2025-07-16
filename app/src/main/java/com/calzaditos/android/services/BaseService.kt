package com.calzaditos.android.services

import com.android.volley.DefaultRetryPolicy

open class BaseService {
    protected val ApiBaseURL = "http://44.195.8.100/"
    protected val RetryPolicy = DefaultRetryPolicy(
        1000,
        5,
        1.0f
    )
}