package com.github.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "header")
class HeaderEntity(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @Json(name = "Content-Type") var contentType: String = "",
    @Json(name = "DeviceId") var deviceId: String = "",
    @Json(name = "DeviceName") var deviceName: String = "",
    @Json(name = "Version") var version: String = "",
    @Json(name = "TimeZone") var timeZone: String = "",
    @Json(name = "Os") var operatingSystem: String = "",
    @Json(name = "Long") var long: String = "",
    @Json(name = "Lat") var lat: String = "",
    @Json(name = "Firebase") var fireBase: String = ""
) : Parcelable