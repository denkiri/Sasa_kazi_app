package com.deletech.sasakazi.models.auth
import androidx.room.Ignore
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class Auth {
    @SerializedName("error")
    @Expose
    var error = false

    @SerializedName("status")
    @Expose
    var status = 0

    @SerializedName("profile")
    @Expose
    var profile: Profile? = null

    @SerializedName("token")
    @Expose
    var token: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
    @Ignore
    constructor(profile: Profile?) {
        this.profile = profile
    }
    constructor()
}