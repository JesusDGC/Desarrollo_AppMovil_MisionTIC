package com.example.apppruebasmisiontic.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.apppruebasmisiontic.R;
import com.example.apppruebasmisiontic.RegisterProductActivity;
import com.example.apppruebasmisiontic.databinding.FragmentHomeBinding;
import com.example.apppruebasmisiontic.ui.util.Constantes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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


    String jsonProducts = "[{\"codigo\":\"01\",\"nombre\":\"Balón Fútbol\",\"informacion\":\"Uefa Champions League #5 Importado Original Ad\",\"precio\":244800,\"enstock\":2,\"shipping_cost\":0,\"imagen\":\"https://http2.mlstatic.com/D_NQ_NP_806746-MCO48105554378_112021-O.webp\"}," +
            "{\"codigo\":\"02\",\"nombre\":\"Bicicletas Roadmaster\",\"informacion\":\"Tornado Rin 29 24 Vel Shimano Palanca\",\"precio\":1840000,\"enstock\":243,\"shipping_cost\":0,\"imagen\":\"https://http2.mlstatic.com/D_NQ_NP_2X_948611-MCO46721884405_072021-F.webp\"}," +
            "{\"codigo\":\"03\",\"nombre\":\"Bolso Morral\",\"informacion\":\"Gw Hidratacion Vejiga 2l Todoterreno Ciclismo\",\"precio\":145000,\"enstock\":25,\"shipping_cost\":0,\"imagen\":\"https://http2.mlstatic.com/D_NQ_NP_744194-MCO43441745237_092020-O.webp\"}," +
            "{\"codigo\":\"04\",\"nombre\":\"Rodillera Ortopédica\",\"informacion\":\"Protección Rodilla Rotula Deportes 733\",\"precio\":15900,\"enstock\":425,\"shipping_cost\":9800,\"imagen\":\"https://http2.mlstatic.com/D_NQ_NP_954304-MCO46388561710_062021-O.webp\"}," +
            "{\"codigo\":\"05\",\"nombre\":\"Smart Gainer Proscience\",\"informacion\":\"Smart Gainer 3lb Proteina Sin Azucar\",\"precio\":70000,\"enstock\":425,\"shipping_cost\":0,\"imagen\":\"https://http2.mlstatic.com/D_NQ_NP_990168-MCO44427501678_122020-O.webp\"}," +
            "{\"codigo\":\"06\",\"nombre\":\"Patines\",\"informacion\":\"En Linea Profesionales Cougar Sr1\",\"precio\":1115000,\"enstock\":18,\"shipping_cost\":0,\"imagen\":\"https://http2.mlstatic.com/D_NQ_NP_999867-MCO42008338809_052020-O.webp\"}," +
            "{\"codigo\":\"07\",\"nombre\":\"Saco De Boxeo\",\"informacion\":\"Tula Mma Pro Punisher +guantes Mma Pro Caray\",\"precio\":125000,\"enstock\":274,\"shipping_cost\":0,\"imagen\":\"https://http2.mlstatic.com/D_NQ_NP_705056-MCO43675680444_102020-O.webp\"}," +
            "{\"codigo\":\"08\",\"nombre\":\"Caminadora\",\"informacion\":\"Trotadora Plegable Residencial Banda Electrica\",\"precio\":986000,\"enstock\":5,\"shipping_cost\":0,\"imagen\":\"https://http2.mlstatic.com/D_NQ_NP_903360-MCO45394396285_032021-O.webp\"}," +
            "{\"codigo\":\"09\",\"nombre\":\"Carpa Camuflada Pixel\",\"informacion\":\"2 Personas Asgard Camping 2x1.2\",\"precio\":64900,\"enstock\":12,\"shipping_cost\":9800,\"imagen\":\"https://http2.mlstatic.com/D_NQ_NP_629303-MCO44022495069_112020-O.webp\"}," +
            "{\"codigo\":\"10\",\"nombre\":\"Kit Mancuernas\",\"informacion\":\"20 Kilos 2 Pesas Maletin Transporte\",\"precio\":154000,\"enstock\":125,\"shipping_cost\":0,\"imagen\":\"https://http2.mlstatic.com/D_NQ_NP_909304-MCO47650383331_092021-O.webp\"}]";

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
        try {
            JSONArray products = new JSONArray(jsonProducts);
            mAdapter = new ProductsAdapter(products, getActivity());

            rev_products.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Agregar Nuevo Producto", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(getActivity(), RegisterProductActivity.class);
                getActivity().startActivityForResult(intent,ACTIVIDAD_REGISTRAR_PRODUCTO);

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

    //Contructor
    public ProductsAdapter(JSONArray products, Activity myActivity) {
        this.products = products;
        this.myActivity = myActivity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_products, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    //Agarra el JSon y pinta la información en el view
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Obtengo el string de preferencias
        myPreference = myActivity.getSharedPreferences(Constantes.PREFERENCE, Context.MODE_PRIVATE);
        String stringFavorites = myPreference.getString("favorites","{\"values\":[]}");

        try {
            Log.e("POS_rec", "POS: " + position);
            String codigo = products.getJSONObject(position).getString("codigo");
            String nombre = products.getJSONObject(position).getString("nombre");
            String imagen = products.getJSONObject(position).getString("imagen");
            int precio = products.getJSONObject(position).getInt("precio");
            int stock = products.getJSONObject(position).getInt("enstock");
            int shipping_cost = products.getJSONObject(position).getInt("shipping_cost");


            holder.txt_name_product.setText(nombre);
            holder.txt_price_product.setText("$ " + precio);

            if(stringFavorites.indexOf(codigo) == -1){
                holder.btn_favorite.setImageResource(R.drawable.ic_favorite_border);
            }
            else{
                holder.btn_favorite.setImageResource(R.drawable.ic_favorite);
            }
            Glide.with(myActivity).load(imagen)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img_product);

            //Stock
            if (stock == 0)
                holder.txt_stock.setText("No hay stock");
            else
                holder.txt_stock.setText(stock + " en stock");
            //State send
            if (shipping_cost == 0)
                holder.txt_state_send.setText("Envio Gratis");
            else
                holder.txt_state_send.setText("Costo del envio: " + shipping_cost);

            //https://developer.android.com/guide/topics/ui/controls/button?hl=es-419
            holder.btn_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        JSONArray jsonFavorites = new JSONObject(stringFavorites).getJSONArray("values");

                        if(stringFavorites.indexOf(codigo) == -1){
                            //Añado al json el codigo del objeto al que le dio favorito
                            jsonFavorites.put(codigo);
                            //Convierto de nuevo el JSON a string
                            String favorites = new Gson().toJson(jsonFavorites);

                            //Modifico y guardo el string en las preferencias
                            SharedPreferences.Editor editor = myPreference.edit();
                            editor.putString("favorites", favorites);

                            //Debe confirmar los cambios en el sharedPreferences para que se vean reflejados
                            editor.commit();

                            holder.btn_favorite.setImageResource(R.drawable.ic_favorite);
                        }
                        else{
                            for(int i= 0; i < jsonFavorites.length(); i++){

                                if(jsonFavorites.getString(i).equals(codigo)){
                                    jsonFavorites.remove(i);

                                    //Convierto de nuevo el JSON a string
                                    String favorites = new Gson().toJson(jsonFavorites);

                                    SharedPreferences.Editor editor = myPreference.edit();
                                    editor.putString("favorites", favorites);
                                    editor.commit();
                                }
                            }
                            holder.btn_favorite.setImageResource(R.drawable.ic_favorite_border);
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
        private TextView txt_state_send;
        private ImageButton btn_favorite;
        private Button btn_buy;
        private ImageView img_product;


        public ViewHolder(View v) {
            super(v);
            txt_name_product = v.findViewById(R.id.txt_name_product);
            txt_price_product = v.findViewById(R.id.txt_price_product);
            txt_stock = v.findViewById(R.id.txt_stock);
            txt_state_send = v.findViewById(R.id.txt_state_send);
            btn_favorite = v.findViewById(R.id.btn_favorite);
            btn_buy = v.findViewById(R.id.btn_buy);
            img_product = v.findViewById(R.id.img_product);

        }
    }
}
