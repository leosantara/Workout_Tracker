package id.ac.ukdw.workout_tracker

import android.os.Parcel
import android.os.Parcelable

data class NotifClass(val JenisWorkout:String, val Waktu:Long) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(Waktu)
        parcel.writeString(JenisWorkout)
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