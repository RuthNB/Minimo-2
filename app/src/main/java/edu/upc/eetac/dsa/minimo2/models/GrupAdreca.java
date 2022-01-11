package edu.upc.eetac.dsa.minimo2.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GrupAdreca implements Parcelable {

    @SerializedName("adreca")
    @Expose
    private String adreca;
    @SerializedName("codi_postal")
    @Expose
    private String codiPostal;
    @SerializedName("municipi_nom")
    @Expose
    private String municipiNom;
    @SerializedName("adreca_completa")
    @Expose
    private String adrecaCompleta;

    protected GrupAdreca(Parcel in) {
        adreca = in.readString();
        codiPostal = in.readString();
        municipiNom = in.readString();
        adrecaCompleta = in.readString();
    }

    public static final Creator<GrupAdreca> CREATOR = new Creator<GrupAdreca>() {
        @Override
        public GrupAdreca createFromParcel(Parcel in) {
            return new GrupAdreca(in);
        }

        @Override
        public GrupAdreca[] newArray(int size) {
            return new GrupAdreca[size];
        }
    };

    public String getAdreca() {
        return adreca;
    }

    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }

    public String getCodiPostal() {
        return codiPostal;
    }

    public void setCodiPostal(String codiPostal) {
        this.codiPostal = codiPostal;
    }

    public String getMunicipiNom() {
        return municipiNom;
    }

    public void setMunicipiNom(String municipiNom) {
        this.municipiNom = municipiNom;
    }

    public String getAdrecaCompleta() {
        return adrecaCompleta;
    }

    public void setAdrecaCompleta(String adrecaCompleta) {
        this.adrecaCompleta = adrecaCompleta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(adreca);
        dest.writeString(codiPostal);
        dest.writeString(municipiNom);
        dest.writeString(adrecaCompleta);
    }
}
