package dk.nodes.template.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Employee(

        @SerializedName("id")
        var id: Int?,
        @SerializedName("firstName")
        var firstName: String?,
        @SerializedName("lastName")
        var lastName: String?,
        @SerializedName("gender")
        var gender: String?,
        @SerializedName("departments")
        var departments: Array<String>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Employee

        if (departments != null) {
            if (other.departments == null) return false
            if (!departments!!.contentEquals(other.departments!!)) return false
        } else if (other.departments != null) return false

        return true
    }

    override fun hashCode(): Int {
        return departments?.contentHashCode() ?: 0
    }
}


