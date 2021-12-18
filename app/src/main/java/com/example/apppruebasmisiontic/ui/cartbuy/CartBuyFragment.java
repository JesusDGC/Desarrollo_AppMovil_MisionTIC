package com.example.apppruebasmisiontic.ui.cartbuy;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.apppruebasmisiontic.R;
import com.example.apppruebasmisiontic.util.Constantes;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartBuyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartBuyFragment extends Fragment {

    private TextView txt_add_cart;
    private RecyclerView rev_add_cart;
    private RecyclerView.Adapter mAdapter;
    private Button btn_buy;

    private SharedPreferences myPreference;

    private JSONArray carritoCompras;
    private final String SHARED_CARRITO = "CARRO_COMPRAS";

    public static CartBuyFragment newInstance() {
        CartBuyFragment fragment = new CartBuyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cart_buy, container, false);

        txt_add_cart = root.findViewById(R.id.txt_add_cart);
        rev_add_cart = root.findViewById(R.id.rev_add_cart);
        btn_buy = root.findViewById(R.id.btn_buy);

        txt_add_cart.setText("This is Add Cart fragment");

        rev_add_cart.setLayoutManager(new LinearLayoutManager(getActivity()));

        myPreference = getActivity().getSharedPreferences(Constantes.PREFERENCE, Context.MODE_PRIVATE);


        try {
            carritoCompras = new JSONArray(myPreference.getString(SHARED_CARRITO, "[]"));

            mAdapter = new ProductsAdapter(carritoCompras, getActivity());
            rev_add_cart.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;
    }
}

class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private JSONArray carrito;
    private Activity myActivity;
    private SharedPreferences myPreference;

    private JSONArray carritoCompras;
    private final String SHARED_CARRITO = "CARRO_COMPRAS";

    //Contructor
    public ProductsAdapter(JSONArray carritoCompras, Activity myActivity) {
        this.carrito = carritoCompras;
        this.myActivity = myActivity;

        this.myPreference = myActivity.getSharedPreferences(Constantes.PREFERENCE, Context.MODE_PRIVATE);
        try {
            this.carritoCompras = new JSONArray(myPreference.getString(SHARED_CARRITO, "[]"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void eliminarProductosCarrito(String codigo) {
        for (int i = 0; i < carritoCompras.length(); i++) {
            try {
                JSONObject carro = carritoCompras.getJSONObject(i);

                if (carro.getString("codigo").equals(codigo)) {
                    carritoCompras.remove(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        SharedPreferences.Editor editor = myPreference.edit();
        editor.putString(SHARED_CARRITO, carritoCompras.toString());
        editor.commit();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_addcart, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    //Agarra el JSon y pinta la información en el view
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Obtengo el string de preferencias
        myPreference = myActivity.getSharedPreferences(Constantes.PREFERENCE, Context.MODE_PRIVATE);

        try {
            String codigo = carrito.getJSONObject(position).getString("codigo");
            String nombre = carrito.getJSONObject(position).getString("nombre");
            String imagen = carrito.getJSONObject(position).getString("imagen");
            int precio = carrito.getJSONObject(position).getInt("precio");
            int stock = carrito.getJSONObject(position).getInt("enstock");

            holder.txt_name_product_add.setText(nombre);
            holder.txt_price_product_add.setText("$ " + precio);


            Glide.with(myActivity).load(imagen)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img_product_add);


            holder.btn_cancel_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eliminarProductosCarrito(codigo);
                    Toast.makeText(myActivity, "Eliminado del carrito de compras", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Obtiene la cantidad de objetos en el json
    @Override
    public int getItemCount() {
//        return userModelList.size();
        return this.carrito.length();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_name_product_add;
        private TextView txt_price_product_add;
        private Button btn_cancel_add;
        private ImageView img_product_add;


        public ViewHolder(View v) {
            super(v);
            txt_name_product_add = v.findViewById(R.id.txt_name_product_add);
            txt_price_product_add = v.findViewById(R.id.txt_price_product_add);

            btn_cancel_add = v.findViewById(R.id.btn_cancel_add);
            img_product_add = v.findViewById(R.id.img_product_add);

        }
    }
}