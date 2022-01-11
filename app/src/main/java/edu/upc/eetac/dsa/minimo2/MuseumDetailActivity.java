package edu.upc.eetac.dsa.minimo2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.upc.eetac.dsa.minimo2.models.Element;

public class MuseumDetailActivity extends AppCompatActivity {
    private static Element element;
    TextView descriptionText;
    ImageView bandera,escut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_detail);
        descriptionText = findViewById(R.id.descripcion);
        bandera = findViewById(R.id.banderaImg);
        escut = findViewById(R.id.escutImg);
        //Recieve Data of the Element from main activity
        element = getIntent().getParcelableExtra("Element");
        //After having the data Show the Data to User and button to close
        /* (campos del JSON recibido del elemento):
        adreca_nom, descripció, grup_adreca.adreca, grup_adreca.,codi_postal,
        group_adreca_municipi_nom, email, telefon_contacte, municipi_escut (imagen),
        municipi_bancera, imagen[0] (imagen), nombre_habitants, extensio y altitud
        */
        //One Giant TextBox for the Whole Json String Description easy way hehe!
        String description = "";
        description = "Nom " + element.getAdrecaNom();
        description = description + ", Descripcio "+ element.getDescripcio()+ ", adreca ";
        description = description + element.getGrupAdreca().getAdreca() +", adreca municipi ";
        description = description + element.getGrupAdreca().getMunicipiNom() + ",email ";
        description = description + element.getEmail() + ",Telefono contacte "+ element.getTelefonContacte() + ", nombre habitants ";
        descriptionText.setText(description);
        Log.w("Descr","Description "+ description);
        Log.w("Descr","Bandera "+ element.getRelMunicipis().getMunicipiBandera());
        Log.w("Descr","escut "+ element.getRelMunicipis().getMunicipiEscut());
        //Just in case, if the bandera or Escut is there and not empty
        if(element.getRelMunicipis().getMunicipiBandera() != null){
            if(!element.getRelMunicipis().getMunicipiBandera().isEmpty())
                Picasso.get().load(element.getRelMunicipis().getMunicipiBandera()).into(bandera);
        }
        if(element.getRelMunicipis().getMunicipiEscut() != null){
            if(!element.getRelMunicipis().getMunicipiEscut().isEmpty())
                Picasso.get().load(element.getRelMunicipis().getMunicipiEscut()).into(escut);
        }
    }
    public void onBackClicked(View view){
        //Close the Activity
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
        finish();
    }
}
