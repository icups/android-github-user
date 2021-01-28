package com.github.ext.picker

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.github.app.BuildConfig
import com.github.app.constant.Request
import com.github.app.constant.Type
import com.github.app.ui.dialog.DialogConfirmation
import com.github.ext.alert.showLongSnackBar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File

fun Activity.pickImageFromGallery(requestCode: Int = Request.Gallery) {
    val intent: Intent = Intent(Intent.ACTION_PICK).apply {
        type = Type.Image
        action = Intent.ACTION_GET_CONTENT
    }

    Dexter.withActivity(this@pickImageFromGallery)
        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        .withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode)
            }

            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                token?.continuePermissionRequest()
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                showLongSnackBar("Gallery need access permission!")
            }
        }).onSameThread().check()
}

fun Activity.pickImageFromCamera(requestCode: Int = Request.Camera) {
    Dexter.withActivity(this@pickImageFromCamera)
        .withPermissions(arrayListOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA))
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                token?.continuePermissionRequest()
            }

            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                if (report.areAllPermissionsGranted()) {
                    val intent = Intent("android.media.action.IMAGE_CAPTURE")

                    val file = File(externalCacheDir, "salonmy_picture.jpg")
                    val authority = BuildConfig.APPLICATION_ID + ".provider"

                    if (intent.resolveActivity(packageManager) != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            val fileUri = FileProvider.getUriForFile(this@pickImageFromCamera, authority, file)
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                        } else {
                            val fileUri: Uri = Uri.fromFile(file)
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                        }

                        startActivityForResult(intent, requestCode)
                    }
                } else {
                    showLongSnackBar("Camera need access permission!")
                }
            }
        }).onSameThread().check()
}

fun Fragment.pickImageFromGallery(requestCode: Int = Request.Gallery, withConfirmation: Boolean = false) {
    if (withConfirmation) {
        DialogConfirmation(requireContext()) { requireActivity().pickImageFromGallery(requestCode) }
    } else {
        requireActivity().pickImageFromGallery(requestCode)
    }
}

fun Fragment.pickImageFromCamera(requestCode: Int = Request.Camera, withConfirmation: Boolean = false) {
    if (withConfirmation) {
        DialogConfirmation(requireContext()) { requireActivity().pickImageFromCamera(requestCode) }
    } else {
        requireActivity().pickImageFromCamera(requestCode)
    }
}