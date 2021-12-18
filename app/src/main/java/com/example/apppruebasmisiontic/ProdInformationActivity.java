package com.example.apppruebasmisiontic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class ProdInformationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Button btn_buy;
    private TextView txt_descripcion;
    private TextView txt_name_product;
    private TextView txt_category;
    private TextView txt_price;
    private TextView txt_stock_product;
    private ImageView img_product;
    private TextView txt_detalle_sin_ubicacion;
    private Button btn_detalle_googlemaps;

    private GoogleMap mMap;

    private Double latitud;
    private Double longitud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_information);

        btn_buy = findViewById(R.id.btn_buy);
        txt_descripcion = findViewById(R.id.txt_descripcion);
        txt_name_product = findViewById(R.id.txt_name_product);
        txt_category = findViewById(R.id.txt_category);
        txt_price = findViewById(R.id.txt_price);
        txt_stock_product = findViewById(R.id.txt_stock_product);
        img_product = findViewById(R.id.img_product);
        txt_detalle_sin_ubicacion = findViewById(R.id.txt_detalle_sin_ubicacion);
        btn_detalle_googlemaps = findViewById(R.id.btn_detalle_googlemaps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getView().setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null && bundle.getString("producto")!= null){
            try {
                JSONObject producto = new JSONObject(bundle.getString("producto"));
                latitud = producto.getDouble("latitud");
                longitud = producto.getDouble("longitud");

                txt_name_product.setText(producto.getString("nombre"));
                txt_descripcion.setText(producto.getString("descripcion"));
                txt_category.setText(producto.getString("categoria"));
                txt_price.setText(producto.getString("precio"));
                txt_stock_product.setText(producto.getString("enstock"));

                if (latitud != 0.0 && longitud != 0.0) {
                    mapFragment.getView().setVisibility(View.VISIBLE);
                    mapFragment.getMapAsync(this);
                } else {
                    txt_detalle_sin_ubicacion.setVisibility(View.VISIBLE);
                }

                Glide.with(this).load(producto.getString("imagen"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(img_product);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        btn_detalle_googlemaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=Taronga+Zoo,+Sydney+Australia");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.e("MAPA_CARGADO", "MAPA_CARGADO");

        LatLng ubicacionVendedor = new LatLng(latitud, longitud);
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(ubicacionVendedor).title("Vendedor"));
        // Move the camera instantly to Sydney with a zoom of 15.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionVendedor, 15));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());

        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(ubicacionVendedor)      // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                //.bearing(90)                // Sets the orientation of the camera to east
                //.tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}