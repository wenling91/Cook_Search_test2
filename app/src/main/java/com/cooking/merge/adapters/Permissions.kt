package com.cooking.merge.adapters

import android.Manifest

//以下為開啟相機所需要使用者授權的權限
object Permissions {
    val PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    val CAMERA_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA
    )
    val WRITE_STORAGE_PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    val READ_STORAGE_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
}