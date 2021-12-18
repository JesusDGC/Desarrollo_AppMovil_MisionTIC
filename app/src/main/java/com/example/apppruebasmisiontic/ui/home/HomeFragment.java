package com.example.apppruebasmisiontic.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.apppruebasmisiontic.LoginActivity;
import com.example.apppruebasmisiontic.ProdInformationActivity;
import com.example.apppruebasmisiontic.R;
import com.example.apppruebasmisiontic.RegisterProductActivity;
import com.example.apppruebasmisiontic.databinding.FragmentHomeBinding;
import com.example.apppruebasmisiontic.util.Constantes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private TextView txt_home;
    private Spinner spn_category;
    private RecyclerView rev_products;
    private RecyclerView.Adapter mAdapter;
    private FloatingActionButton fab;
    private FragmentHomeBinding binding;

    private final int ACTIVIDAD_REGISTRAR_PRODUCTO = 1;

    FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        txt_home = root.findViewById(R.id.txt_home);
        spn_category = root.findViewById(R.id.spn_category);
        rev_products = root.findViewById(R.id.rev_products);
        fab = root.findViewById(R.id.fab);

        txt_home.setText("This is home fragment");

        //Opcion 1: String array de la carpeta values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories,
                android.R.layout.simple_spinner_dropdown_item);


        spn_category.setAdapter(adapter);

        rev_products.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Este es para utilizar un recycler view en columnas
        //rev_products.setLayoutManager(new GridLayoutManager(getActivity(),2));

        /*spn_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                txt_home.setText(spn_category.getSelectedItem().toString());
                if(txt_home.equals("Sports and fitness")){
                    try {
                        JSONArray products = new JSONArray(jsonProducts);
                        mAdapter = new ProductsAdapter(products, getActivity());

                        rev_products.setAdapter(mAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/

        //Llamando el adaptador mediante el json manual
        /*
        try {
            JSONArray products = new JSONArray(jsonProducts);
            mAdapter = new ProductsAdapter(products, getActivity());

            rev_products.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

/*
        String jsonProductos = "[{\"nombre\":\"Balón\",\"precio\":50000,\"enstock\":true,\"sucursales\":[{\"nombre\":\"Sucursal A\",\"area\":100,\"encargado\":{\"nombre\":\"Encargado 1\"}},{\"nombre\":\"Sucursal B\",\"area\":200,\"encargado\":{\"nombre\":\"Encargado 2\"}}]},{\"nombre\":\"Guantes\",\"precio\":40000,\"enstock\":false,\"sucursales\":[{\"nombre\":\"Sucursal C\",\"area\":50,\"encargado\":{\"nombre\":\"Encargado 3\"}},{\"nombre\":\"Sucursal D\",\"area\":45,\"encargado\":{\"nombre\":\"Encargado 4\"}}]}]";

        try {
            JSONArray productos = new JSONArray(jsonProductos);

            JSONObject producto0 = productos.getJSONObject(0);

            JSONArray sucursales = producto0.getJSONArray("sucursales");

            JSONObject sucursal0 = sucursales.getJSONObject(0);

            JSONObject encargado = sucursal0.getJSONObject("encargado");

            String nombreEncargado = encargado.getString("nombre");

            Toast.makeText(getActivity(), "nombre: " + nombreEncargado, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();

        }

*/
        db = FirebaseFirestore.getInstance();
        db.collection("productos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            JSONArray productos = new JSONArray();


                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("TAG", document.getId() + " => " + document.getData());

                                String codigo = document.getId();
                                String nombre = document.getData().get("nombre").toString();
                                String categoria = document.getData().get("categoria").toString();
                                String descripcion = document.getData().get("descripcion").toString();
                                int precio = Integer.parseInt(document.getData().get("precio").toString());
                                int enstock = Integer.parseInt(document.getData().get("enstock").toString());
                                String imagen = document.getData().get("imagen").toString();

                                Double latitud;
                                Double longitud;
                                try {
                                    latitud = Double.parseDouble(document.getData().get("latitud").toString());
                                    longitud = Double.parseDouble(document.getData().get("longitud").toString());
                                } catch (Exception e) {
                                    latitud = 0.0;
                                    longitud = 0.0;
                                }


                                JSONObject producto = new JSONObject();
                                try {
                                    producto.put("codigo", codigo);
                                    producto.put("nombre", nombre);
                                    producto.put("categoria", categoria);
                                    producto.put("descripcion", descripcion);
                                    producto.put("precio", precio);
                                    producto.put("enstock", enstock);
                                    producto.put("imagen", imagen);
                                    producto.put("latitud", latitud);
                                    producto.put("longitud", longitud);

                                    productos.put(producto);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                            }

                            mAdapter = new ProductsAdapter(productos, getActivity());
                            rev_products.setAdapter(mAdapter);

                        } else {
                            Log.e("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Agregar Nuevo Producto", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(getActivity(), RegisterProductActivity.class);
                //getActivity().startActivityForResult(intent,ACTIVIDAD_REGISTRAR_PRODUCTO);
                getActivity().startActivity(intent);

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ACTIVIDAD_REGISTRAR_PRODUCTO){
            if(resultCode == Activity.RESULT_OK) {
                Log.e("CLICK_SAVE", "CLICK_SAVE");
                txt_home.setText("Producto agregado correctamente");
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("CLICK_CANCEL", "CLICK_CANCEL");
                txt_home.setText("Producto cancelado ");
            }
            else
                txt_home.setText("No se a agregado el producto");
        }

    }
}


class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private JSONArray products;
    private Activity myActivity;
    private SharedPreferences myPreference;

    private JSONArray favoritos;
    private JSONArray carritoCompras;
    private final String SHARED_FAVORITOS = "FAVORITOS";
    private final String SHARED_CARRITO = "CARRO_COMPRAS";

    //Contructor
    public ProductsAdapter(JSONArray products, Activity myActivity) {
        this.products = products;
        this.myActivity = myActivity;
        this.myPreference = myActivity.getSharedPreferences(Constantes.PREFERENCE, Context.MODE_PRIVATE);
        try {
            this.favoritos = new JSONArray(myPreference.getString(SHARED_FAVORITOS, "[]"));
            this.carritoCompras = new JSONArray(myPreference.getString(SHARED_CARRITO, "[]"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean esFavorito(String codigo) {
        for (int i = 0; i < favoritos.length(); i++) {
            try {
                JSONObject favorito = favoritos.getJSONObject(i);
                if (favorito.getString("codigo").equals(codigo)) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public boolean estaEnElCarrito(String codigo) {
        for (int i = 0; i < carritoCompras.length(); i++) {
            try {
                JSONObject carroC = carritoCompras.getJSONObject(i);
                if (carroC.getString("codigo").equals(codigo)) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public void eliminarProductoFavorito(String codigo) {
        for (int i = 0; i < favoritos.length(); i++) {
            try {
                JSONObject favorito = favoritos.getJSONObject(i);

                if (favorito.getString("codigo").equals(codigo)) {
                    favoritos.remove(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        SharedPreferences.Editor editor = myPreference.edit();
        editor.putString(SHARED_FAVORITOS, favoritos.toString());
        editor.commit();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_products, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    //Agarra el JSON y pinta la información en el view
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        //Obtengo el string de preferencias
        myPreference = myActivity.getSharedPreferences(Constantes.PREFERENCE, Context.MODE_PRIVATE);

        try {
            Log.e("POS_rec", "POS: " + position);
            String codigo = products.getJSONObject(position).getString("codigo");
            String nombre = products.getJSONObject(position).getString("nombre");
            String imagen = products.getJSONObject(position).getString("imagen");
            int precio = products.getJSONObject(position).getInt("precio");
            int stock = products.getJSONObject(position).getInt("enstock");
            //int shipping_cost = products.getJSONObject(position).getInt("shipping_cost");


            holder.txt_name_product.setText(nombre);
            holder.txt_price_product.setText("$ " + precio);

            if (esFavorito(codigo))
                holder.btn_favorite.setImageDrawable(myActivity.getDrawable(R.drawable.ic_favorite));


            Glide.with(myActivity).load(imagen)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img_product);

            //Stock
            if (stock == 0)
                holder.txt_stock.setText("No hay stock");
            else
                holder.txt_stock.setText(stock + " en stock");
            //State send
            /*if (shipping_cost == 0)
                holder.txt_state_send.setText("Envio Gratis");
            else
                holder.txt_state_send.setText("Costo del envio: " + shipping_cost);
*/
            holder.img_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        Log.e("CLICK_IMAGE", products.getJSONObject(position).toString());
                        Intent intent = new Intent(myActivity, ProdInformationActivity.class);
                        intent.putExtra("producto", products.getJSONObject(position).toString());
                        myActivity.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });

            holder.btn_add_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        JSONArray carritoCom = new JSONArray(myPreference.getString(SHARED_CARRITO, "[]"));
                        if(estaEnElCarrito(codigo)) {
                            Toast.makeText(myActivity, "Producto ya agregado al carrito", Toast.LENGTH_SHORT).show();
                        } else {
                            carritoCom.put(products.getJSONObject(position));
                            SharedPreferences.Editor editor = myPreference.edit();
                            editor.putString(SHARED_CARRITO, carritoCom.toString());
                            editor.commit();
                            Toast.makeText(myActivity, "Agregado con exito", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            //https://developer.android.com/guide/topics/ui/controls/button?hl=es-419
            holder.btn_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        JSONArray favoritos = new JSONArray(myPreference.getString(SHARED_FAVORITOS, "[]"));

                        if(esFavorito(codigo)) {
                            eliminarProductoFavorito(codigo);
                            holder.btn_favorite.setImageDrawable(myActivity.getDrawable(R.drawable.ic_favorite_border));
                        } else {
                            favoritos.put(products.getJSONObject(position));
                            SharedPreferences.Editor editor = myPreference.edit();
                            editor.putString(SHARED_FAVORITOS, favoritos.toString());
                            editor.commit();
                            holder.btn_favorite.setImageDrawable(myActivity.getDrawable(R.drawable.ic_favorite));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


            //Glide.with(miActividad).load("http://goo.gl/gEgYUd").into(holder.imv_prodcuto);
        } catch (JSONException e) {
            holder.txt_name_product.setText("error");
        }
    }

    //Obtiene la cantidad de objetos en el json
    @Override
    public int getItemCount() {
//        return userModelList.size();
        return this.products.length();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_name_product;
        private TextView txt_price_product;
        private TextView txt_stock;
        private ImageButton btn_favorite;
        private Button btn_add_cart;
        private ImageView img_product;


        public ViewHolder(View v) {
            super(v);
            txt_name_product = v.findViewById(R.id.txt_name_product);
            txt_price_product = v.findViewById(R.id.txt_price_product);
            txt_stock = v.findViewById(R.id.txt_stock);
            btn_favorite = v.findViewById(R.id.btn_favorite);
            btn_add_cart = v.findViewById(R.id.btn_add_cart);
            img_product = v.findViewById(R.id.img_product);

        }
    }
}
