package com.deletech.sasakazi.models.auth
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
@Entity(
        indices = [(Index("id"))],
        primaryKeys = ["id"]
)
class Profile {
    @field:SerializedName("id")
    @Expose
    var id:Int? = 0
    @field:SerializedName("password")
    @Expose
    var password: String? = null
    @field:SerializedName("name")
    @Expose
    var name: String? = null
    @field:SerializedName("email")
    @Expose
    var email: String? = null
    @field:SerializedName("mobile")
    @Expose
    var mobile: String? = null
    @field:SerializedName("token")
    @Expose
    var token: String? = null
    @Ignore
    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }
    constructor(email: String?, mobile: String?,name:String,password: String?) {
        this.email = email
        this.mobile=mobile
        this.name=name
        this.password = password
    }
    constructor()
}