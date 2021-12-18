package com.example.apppruebasmisiontic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apppruebasmisiontic.util.Constantes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterProductActivity extends AppCompatActivity {

    private Button btn_take_photo;
    private Button btn_cancel;
    private Button btn_save;
    private ImageView img_product_image;
    private EditText edt_enlace_image;
    private EditText edt_name_product;
    private EditText edt_category;
    private EditText edt_price_product;
    private EditText edt_stock_product;
    private EditText edt_description;

    private Button btn_producto_ubicacion;
    private TextView txt_producto_ubicacion;

    final int OPEN_GALLERY = 1;
    final int OPEN_MAP = 2;

    private SharedPreferences mypreferences;

    Uri data1;

    String urlImage;
    String usuario;

    FirebaseFirestore db;

    Double latitud;
    Double longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_product);

        db = FirebaseFirestore.getInstance();

        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_take_photo = findViewById(R.id.btn_take_photo);
        img_product_image = findViewById(R.id.img_product_image);
        edt_enlace_image = findViewById(R.id.edt_enlace_image);
        edt_name_product = findViewById(R.id.edt_name_product);
        edt_category = findViewById(R.id.edt_category);
        edt_price_product = findViewById(R.id.edt_price_product);
        edt_stock_product = findViewById(R.id.edt_stock_product);
        edt_description = findViewById(R.id.edt_description);
        btn_producto_ubicacion = findViewById(R.id.btn_producto_ubicacion);
        txt_producto_ubicacion = findViewById(R.id.tev_producto_ubicacion);


        mypreferences = getSharedPreferences(Constantes.PREFERENCE, MODE_PRIVATE);


        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), OPEN_GALLERY);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("CLICK_SAVE", "CLICK_SAVE");

                if (edt_name_product.getText().toString().equals("") || edt_category.getText().toString().equals("") || edt_description.getText().toString().equals("") || edt_price_product.getText().toString().equals("") || edt_stock_product.getText().toString().equals("") ){
                    Toast.makeText(RegisterProductActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }
                else{
                     try {
                         Integer.parseInt(edt_price_product.getText().toString());
                         Integer.parseInt(edt_stock_product.getText().toString());
                         //Intent resultIntent = new Intent();
                         //Una sobrecarga
                         //setResult(Activity.RESULT_OK);
                         //finish();
                         subirImagen();
                         guardarProducto();

                     }
                     catch (Exception e){
                        Toast.makeText(RegisterProductActivity.this, "El precio y el stock deben ser datos numericos " + e, Toast.LENGTH_SHORT).show();
                     }


                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("CLICK_CANCEL", "CLICK_CANCEL");
                //Intent resultIntent = new Intent();
                //Una sobrecarga
                //setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        btn_producto_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterProductActivity.this, MapaActivity.class);
                startActivityForResult(intent, OPEN_MAP);
            }
        });
    }


    public void guardarProducto() {

            String nombre = edt_name_product.getText().toString();
            String categoria = edt_category.getText().toString();
            String descripcion = edt_description.getText().toString();
            String image = edt_enlace_image.getText().toString();
            int precio = Integer.parseInt(edt_price_product.getText().toString());
            int enstock = Integer.parseInt(edt_stock_product.getText().toString());

            // Create a new user with a first and last name
            Map<String, Object> producto = new HashMap<>();
            producto.put("nombre", nombre);
            producto.put("categoria", categoria);
            producto.put("descripcion", descripcion);
            producto.put("precio", precio);

            if(urlImage!= null)
                producto.put("imagen", urlImage);
            else
                producto.put("imagen", image);

            producto.put("enstock", enstock);
            //producto.put("usuario", usuario);
            producto.put("latitud", latitud);
            producto.put("longitud", longitud);


            //  Add a new document with a generated ID
            db.collection("productos")
                    .add(producto)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());

                            edt_name_product.setText("");
                            edt_category.setText("");
                            edt_description.setText("");
                            edt_price_product.setText("");
                            edt_stock_product.setText("");
                            img_product_image.setImageDrawable(getDrawable(R.drawable.dw_camera));
                            txt_producto_ubicacion.setText("Mi ubicación:");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "Error adding document", e);
                        }
                    });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPEN_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                data1 = data.getData();
                Toast.makeText(RegisterProductActivity.this, "Open galleri OK", Toast.LENGTH_LONG).show();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data1);
                    img_product_image.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == OPEN_MAP) {
            if (resultCode ==Activity.RESULT_OK) {

                latitud = data.getDoubleExtra("latitud", 0);
                longitud = data.getDoubleExtra("longitud", 0);

                txt_producto_ubicacion.setText("Mi ubicación es: " + latitud + " - " + longitud);
            }
        }
    }

    public void subirImagen() {


        usuario = mypreferences.getString("email", "No hay usuario");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        //if there is a file to upload
        if (data1 != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(RegisterProductActivity.this);
            progressDialog.setTitle("Subiendo");
            progressDialog.show();

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String strDate = sdf.format(c.getTime());
            String nombreImagen = strDate + ".jpg";

            StorageReference riversRef = storageReference.child(usuario + "/" + nombreImagen);
            riversRef.putFile(data1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(RegisterProductActivity.this, "El archivo se ha subido", Toast.LENGTH_LONG).show();

                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    urlImage = uri.toString();
                                    Log.e("URL_IMAGE", urlImage);
                                    guardarProducto();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(RegisterProductActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }
}