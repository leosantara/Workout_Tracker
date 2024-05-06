package id.ac.ukdw.workout_tracker

import android.os.Parcel
import android.os.Parcelable

data class NotifClass(val Judul: String, val JenisWorkout:String, val JumlahLatihan:Int, val Kalori:Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Judul)
        parcel.writeString(JenisWorkout)
        parcel.writeInt(JumlahLatihan)
        parcel.writeInt(Kalori)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NotifClass> {
        override fun createFromParcel(parcel: Parcel): NotifClass {
            return NotifClass(parcel)
        }

        override fun newArray(size: Int): Array<NotifClass?> {
            return arrayOfNulls(size)
        }
    }

}