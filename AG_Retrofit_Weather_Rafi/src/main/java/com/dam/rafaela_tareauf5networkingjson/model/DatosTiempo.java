
package com.dam.rafaela_tareauf5networkingjson.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatosTiempo implements Parcelable {

    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("precipIntensity")
    @Expose
    private Float precipIntensity;
    @SerializedName("precipProbability")
    @Expose
    private Float precipProbability;
    @SerializedName("precipType")
    @Expose
    private String precipType;
    @SerializedName("temperature")
    @Expose
    private Float temperature;
    @SerializedName("apparentTemperature")
    @Expose
    private Float apparentTemperature;
    @SerializedName("dewPoint")
    @Expose
    private Float dewPoint;
    @SerializedName("humidity")
    @Expose
    private Float humidity;
    @SerializedName("pressure")
    @Expose
    private Float pressure;
    @SerializedName("windSpeed")
    @Expose
    private Float windSpeed;
    @SerializedName("windGust")
    @Expose
    private Float windGust;
    @SerializedName("windBearing")
    @Expose
    private Integer windBearing;
    @SerializedName("cloudCover")
    @Expose
    private Float cloudCover;
    @SerializedName("uvIndex")
    @Expose
    private Integer uvIndex;
    @SerializedName("visibility")
    @Expose
    private Integer visibility;
    @SerializedName("ozone")
    @Expose
    private Float ozone;

    /**
     * No args constructor for use in serialization
     *
     */
    public DatosTiempo() {
    }

    /**
     *
     * @param summary
     * @param precipProbability
     * @param visibility
     * @param windGust
     * @param precipIntensity
     * @param icon
     * @param cloudCover
     * @param windBearing
     * @param apparentTemperature
     * @param pressure
     * @param dewPoint
     * @param ozone
     * @param precipType
     * @param temperature
     * @param humidity
     * @param time
     * @param windSpeed
     * @param uvIndex
     */
    public DatosTiempo(Integer time, String summary, String icon, Float precipIntensity, Float precipProbability, String precipType, Float temperature, Float apparentTemperature, Float dewPoint, Float humidity, Float pressure, Float windSpeed, Float windGust, Integer windBearing, Float cloudCover, Integer uvIndex, Integer visibility, Float ozone) {
        super();
        this.time = time;
        this.summary = summary;
        this.icon = icon;
        this.precipIntensity = precipIntensity;
        this.precipProbability = precipProbability;
        this.precipType = precipType;
        this.temperature = temperature;
        this.apparentTemperature = apparentTemperature;
        this.dewPoint = dewPoint;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windGust = windGust;
        this.windBearing = windBearing;
        this.cloudCover = cloudCover;
        this.uvIndex = uvIndex;
        this.visibility = visibility;
        this.ozone = ozone;
    }

    protected DatosTiempo(Parcel in) {
        if (in.readByte() == 0) {
            time = null;
        } else {
            time = in.readInt();
        }
        summary = in.readString();
        icon = in.readString();
        if (in.readByte() == 0) {
            precipIntensity = null;
        } else {
            precipIntensity = in.readFloat();
        }
        if (in.readByte() == 0) {
            precipProbability = null;
        } else {
            precipProbability = in.readFloat();
        }
        precipType = in.readString();
        if (in.readByte() == 0) {
            temperature = null;
        } else {
            temperature = in.readFloat();
        }
        if (in.readByte() == 0) {
            apparentTemperature = null;
        } else {
            apparentTemperature = in.readFloat();
        }
        if (in.readByte() == 0) {
            dewPoint = null;
        } else {
            dewPoint = in.readFloat();
        }
        if (in.readByte() == 0) {
            humidity = null;
        } else {
            humidity = in.readFloat();
        }
        if (in.readByte() == 0) {
            pressure = null;
        } else {
            pressure = in.readFloat();
        }
        if (in.readByte() == 0) {
            windSpeed = null;
        } else {
            windSpeed = in.readFloat();
        }
        if (in.readByte() == 0) {
            windGust = null;
        } else {
            windGust = in.readFloat();
        }
        if (in.readByte() == 0) {
            windBearing = null;
        } else {
            windBearing = in.readInt();
        }
        if (in.readByte() == 0) {
            cloudCover = null;
        } else {
            cloudCover = in.readFloat();
        }
        if (in.readByte() == 0) {
            uvIndex = null;
        } else {
            uvIndex = in.readInt();
        }
        if (in.readByte() == 0) {
            visibility = null;
        } else {
            visibility = in.readInt();
        }
        if (in.readByte() == 0) {
            ozone = null;
        } else {
            ozone = in.readFloat();
        }
    }

    public static final Creator<DatosTiempo> CREATOR = new Creator<DatosTiempo>() {
        @Override
        public DatosTiempo createFromParcel(Parcel in) {
            return new DatosTiempo(in);
        }

        @Override
        public DatosTiempo[] newArray(int size) {
            return new DatosTiempo[size];
        }
    };

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Float getPrecipIntensity() {
        return precipIntensity;
    }

    public void setPrecipIntensity(Float precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    public Float getPrecipProbability() {
        return precipProbability;
    }

    public void setPrecipProbability(Float precipProbability) {
        this.precipProbability = precipProbability;
    }

    public String getPrecipType() {
        return precipType;
    }

    public void setPrecipType(String precipType) {
        this.precipType = precipType;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(Float apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public Float getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Float dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public Float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Float getWindGust() {
        return windGust;
    }

    public void setWindGust(Float windGust) {
        this.windGust = windGust;
    }

    public Integer getWindBearing() {
        return windBearing;
    }

    public void setWindBearing(Integer windBearing) {
        this.windBearing = windBearing;
    }

    public Float getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(Float cloudCover) {
        this.cloudCover = cloudCover;
    }

    public Integer getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(Integer uvIndex) {
        this.uvIndex = uvIndex;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Float getOzone() {
        return ozone;
    }

    public void setOzone(Float ozone) {
        this.ozone = ozone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (time == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(time);
        }
        dest.writeString(summary);
        dest.writeString(icon);
        if (precipIntensity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(precipIntensity);
        }
        if (precipProbability == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(precipProbability);
        }
        dest.writeString(precipType);
        if (temperature == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(temperature);
        }
        if (apparentTemperature == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(apparentTemperature);
        }
        if (dewPoint == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(dewPoint);
        }
        if (humidity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(humidity);
        }
        if (pressure == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(pressure);
        }
        if (windSpeed == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(windSpeed);
        }
        if (windGust == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(windGust);
        }
        if (windBearing == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(windBearing);
        }
        if (cloudCover == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(cloudCover);
        }
        if (uvIndex == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(uvIndex);
        }
        if (visibility == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(visibility);
        }
        if (ozone == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(ozone);
        }
    }
}