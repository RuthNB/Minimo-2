package edu.upc.eetac.dsa.minimo2.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelMunicipis implements Parcelable {

    @SerializedName("ine")
    @Expose
    private String ine;
    @SerializedName("municipi_nom")
    @Expose
    private String municipiNom;
    @SerializedName("municipi_nom_curt")
    @Expose
    private String municipiNomCurt;
    @SerializedName("municipi_article")
    @Expose
    private String municipiArticle;
    @SerializedName("municipi_transliterat")
    @Expose
    private String municipiTransliterat;
    @SerializedName("municipi_curt_transliterat")
    @Expose
    private String municipiCurtTransliterat;
    @SerializedName("centre_municipal")
    @Expose
    private String centreMunicipal;
    @SerializedName("grup_comarca")
    @Expose
    private GrupComarca grupComarca;
    @SerializedName("grup_provincia")
    @Expose
    private GrupProvincia grupProvincia;
    @SerializedName("grup_ajuntament")
    @Expose
    private GrupAjuntament grupAjuntament;
    @SerializedName("municipi_escut")
    @Expose
    private String municipiEscut;
    @SerializedName("municipi_bandera")
    @Expose
    private String municipiBandera;
    @SerializedName("municipi_vista")
    @Expose
    private String municipiVista;
    @SerializedName("ine6")
    @Expose
    private String ine6;
    @SerializedName("nom_dbpedia")
    @Expose
    private String nomDbpedia;

    protected RelMunicipis(Parcel in) {
        ine = in.readString();
        municipiNom = in.readString();
        municipiNomCurt = in.readString();
        municipiArticle = in.readString();
        municipiTransliterat = in.readString();
        municipiCurtTransliterat = in.readString();
        centreMunicipal = in.readString();
        municipiEscut = in.readString();
        municipiBandera = in.readString();
        municipiVista = in.readString();
        ine6 = in.readString();
        nomDbpedia = in.readString();
    }

    public static final Creator<RelMunicipis> CREATOR = new Creator<RelMunicipis>() {
        @Override
        public RelMunicipis createFromParcel(Parcel in) {
            return new RelMunicipis(in);
        }

        @Override
        public RelMunicipis[] newArray(int size) {
            return new RelMunicipis[size];
        }
    };

    public String getIne() {
        return ine;
    }

    public void setIne(String ine) {
        this.ine = ine;
    }

    public String getMunicipiNom() {
        return municipiNom;
    }

    public void setMunicipiNom(String municipiNom) {
        this.municipiNom = municipiNom;
    }

    public String getMunicipiNomCurt() {
        return municipiNomCurt;
    }

    public void setMunicipiNomCurt(String municipiNomCurt) {
        this.municipiNomCurt = municipiNomCurt;
    }

    public String getMunicipiArticle() {
        return municipiArticle;
    }

    public void setMunicipiArticle(String municipiArticle) {
        this.municipiArticle = municipiArticle;
    }

    public String getMunicipiTransliterat() {
        return municipiTransliterat;
    }

    public void setMunicipiTransliterat(String municipiTransliterat) {
        this.municipiTransliterat = municipiTransliterat;
    }

    public String getMunicipiCurtTransliterat() {
        return municipiCurtTransliterat;
    }

    public void setMunicipiCurtTransliterat(String municipiCurtTransliterat) {
        this.municipiCurtTransliterat = municipiCurtTransliterat;
    }

    public String getCentreMunicipal() {
        return centreMunicipal;
    }

    public void setCentreMunicipal(String centreMunicipal) {
        this.centreMunicipal = centreMunicipal;
    }

    public GrupComarca getGrupComarca() {
        return grupComarca;
    }

    public void setGrupComarca(GrupComarca grupComarca) {
        this.grupComarca = grupComarca;
    }

    public GrupProvincia getGrupProvincia() {
        return grupProvincia;
    }

    public void setGrupProvincia(GrupProvincia grupProvincia) {
        this.grupProvincia = grupProvincia;
    }

    public GrupAjuntament getGrupAjuntament() {
        return grupAjuntament;
    }

    public void setGrupAjuntament(GrupAjuntament grupAjuntament) {
        this.grupAjuntament = grupAjuntament;
    }

    public String getMunicipiEscut() {
        return municipiEscut;
    }

    public void setMunicipiEscut(String municipiEscut) {
        this.municipiEscut = municipiEscut;
    }

    public String getMunicipiBandera() {
        return municipiBandera;
    }

    public void setMunicipiBandera(String municipiBandera) {
        this.municipiBandera = municipiBandera;
    }

    public String getMunicipiVista() {
        return municipiVista;
    }

    public void setMunicipiVista(String municipiVista) {
        this.municipiVista = municipiVista;
    }

    public String getIne6() {
        return ine6;
    }

    public void setIne6(String ine6) {
        this.ine6 = ine6;
    }

    public String getNomDbpedia() {
        return nomDbpedia;
    }

    public void setNomDbpedia(String nomDbpedia) {
        this.nomDbpedia = nomDbpedia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ine);
        dest.writeString(municipiNom);
        dest.writeString(municipiNomCurt);
        dest.writeString(municipiArticle);
        dest.writeString(municipiTransliterat);
        dest.writeString(municipiCurtTransliterat);
        dest.writeString(centreMunicipal);
        dest.writeString(municipiEscut);
        dest.writeString(municipiBandera);
        dest.writeString(municipiVista);
        dest.writeString(ine6);
        dest.writeString(nomDbpedia);
    }
}
