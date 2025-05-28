package com.dam.rafaela_tareauf5networkingjson.model;




import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tiempo implements Parcelable {

    @SerializedName("latitude")
    @Expose
    private Float latitude;
    @SerializedName("longitude")
    @Expose
    private Float longitude;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("currently")
    @Expose
    private DatosTiempo currently;
    @SerializedName("offset")
    @Expose
    private Integer offset;

    /**
     * No args constructor for use in serialization
     *
     */
    public Tiempo() {
    }

    /**
     *
     * @param currently
     * @param offset
     * @param timezone
     * @param latitude
     * @param longitude
     */
    public Tiempo(Float latitude, Float longitude, String timezone, DatosTiempo currently, Integer offset) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.timezone = timezone;
        this.currently = currently;
        this.offset = offset;
    }

    protected Tiempo(Parcel in) {
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readFloat();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readFloat();
        }
        timezone = in.readString();
        if (in.readByte() == 0) {
            offset = null;
        } else {
            offset = in.readInt();
        }
    }

    public static final Creator<Tiempo> CREATOR = new Creator<Tiempo>() {
        @Override
        public Tiempo createFromParcel(Parcel in) {
            return new Tiempo(in);
        }

        @Override
        public Tiempo[] newArray(int size) {
            return new Tiempo[size];
        }
    };

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public DatosTiempo getCurrently() {
        return currently;
    }

    public void setCurrently(DatosTiempo currently) {
        this.currently = currently;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }


    @Override
    public String toString() {
        return "Tiempo{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", timezone='" + timezone + '\'' +
                ", currently=" + currently +
                ", offset=" + offset +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(latitude);
        }
        if (longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(longitude);
        }
        dest.writeString(timezone);
        if (offset == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(offset);
        }
    }
}