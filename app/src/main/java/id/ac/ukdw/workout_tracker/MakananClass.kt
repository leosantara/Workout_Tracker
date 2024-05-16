package id.ac.ukdw.workout_tracker

import android.os.Parcel
import android.os.Parcelable

data class MakananClass(
    val Judul: String = "",
    val Bahan: String = "",
    val Cara: String = "",
    val Foto: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Judul)
        parcel.writeString(Bahan)
        parcel.writeString(Cara)
        parcel.writeString(Foto)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MakananClass> {
        override fun createFromParcel(parcel: Parcel): MakananClass {
            return MakananClass(parcel)
        }

        override fun newArray(size: Int): Array<MakananClass?> {
            return arrayOfNulls(size)
        }
    }
}