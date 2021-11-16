package com.example.apppruebasmisiontic.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import com.example.apppruebasmisiontic.R;
import com.example.apppruebasmisiontic.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private TextView txt_home;
    private Spinner spn_category;
    private RecyclerView rev_products;
    private RecyclerView.Adapter mAdapter;
    private FragmentHomeBinding binding;

    String jsonProducts = "[{\"nombre\":\"Robot\",\"precio\":50000,\"enstock\":1,\"shipping_cost\":2000},{\"nombre\":\"Guantes\",\"precio\":40000,\"enstock\":0,\"shipping_cost\":0},{\"nombre\":\"Computadora\",\"precio\":4000000,\"enstock\":50,\"shipping_cost\":5000},{\"nombre\":\"Celular\",\"precio\":800000,\"enstock\":10,\"shipping_cost\":0},{\"nombre\":\"Escritorio\",\"precio\":400000,\"enstock\":5,\"shipping_cost\":10000}]";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        txt_home = root.findViewById(R.id.txt_home);
        spn_category = root.findViewById(R.id.spn_category);
        rev_products = root.findViewById(R.id.rev_products);

        txt_home.setText("This is home fragment");

        //Opcion 1: String array de la carpeta values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories,
                android.R.layout.simple_spinner_item);


        spn_category.setAdapter(adapter);

        rev_products.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Este es para utilizar un recycler view en columnas
        //rev_products.setLayoutManager(new GridLayoutManager(getActivity(),2));

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


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private JSONArray products;
    private Activity myActivity;

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

        try {
            Log.e("POS_rec", "POS: " + position);
            String nombre = products.getJSONObject(position).getString("nombre");
            int precio = products.getJSONObject(position).getInt("precio");
            int stock = products.getJSONObject(position).getInt("enstock");
            int shipping_cost = products.getJSONObject(position).getInt("shipping_cost");


            holder.txt_name_product.setText(nombre);
            holder.txt_price_product.setText("$ " + precio);

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
        //private Button btn_favorite;
        private Button btn_buy;
        private ImageView img_product;


        public ViewHolder(View v) {
            super(v);
            txt_name_product = v.findViewById(R.id.txt_name_product);
            txt_price_product = v.findViewById(R.id.txt_price_product);
            txt_stock = v.findViewById(R.id.txt_stock);
            txt_state_send = v.findViewById(R.id.txt_state_send);
            //btn_favorite = v.findViewById(R.id.btn_favorite);
            btn_buy = v.findViewById(R.id.btn_buy);
            img_product = v.findViewById(R.id.img_product);

        }
    }
}
