package com.example.apppruebasmisiontic.ui.favorite;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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

public class FavoritesFragment extends Fragment {


    private TextView txt_fav;
    private RecyclerView rev_products_fav;
    private RecyclerView.Adapter mAdapter;

    private SharedPreferences myPreference;

    private JSONArray favoritos;
    private final String SHARED_FAVORITOS = "FAVORITOS";

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        txt_fav = root.findViewById(R.id.txt_fav);
        rev_products_fav = root.findViewById(R.id.rev_products_fav);

        txt_fav.setText("This is favorite fragment");

        rev_products_fav.setLayoutManager(new LinearLayoutManager(getActivity()));

        myPreference = getActivity().getSharedPreferences(Constantes.PREFERENCE, Context.MODE_PRIVATE);


        try {
            favoritos = new JSONArray(myPreference.getString(SHARED_FAVORITOS, "[]"));

            mAdapter = new ProductsAdapter(favoritos,getActivity());
            rev_products_fav.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return root;
    }


}


class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private JSONArray jsonFavorites;
    private Activity myActivity;
    private SharedPreferences myPreference;

    private JSONArray favoritos;
    private final String SHARED_FAVORITOS = "FAVORITOS";

    //Contructor
    public ProductsAdapter(JSONArray favoriteProd, Activity myActivity) {
        this.jsonFavorites = favoriteProd;
        this.myActivity = myActivity;

        this.myPreference = myActivity.getSharedPreferences(Constantes.PREFERENCE, Context.MODE_PRIVATE);
        try {
            this.favoritos = new JSONArray(myPreference.getString(SHARED_FAVORITOS, "[]"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    //Agarra el JSon y pinta la información en el view
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Obtengo el string de preferencias
        myPreference = myActivity.getSharedPreferences(Constantes.PREFERENCE, Context.MODE_PRIVATE);


        try {
            String codigo = jsonFavorites.getJSONObject(position).getString("codigo");
            String nombre = jsonFavorites.getJSONObject(position).getString("nombre");
            String imagen = jsonFavorites.getJSONObject(position).getString("imagen");
            int precio = jsonFavorites.getJSONObject(position).getInt("precio");
            int stock = jsonFavorites.getJSONObject(position).getInt("enstock");

            holder.txt_name_product.setText(nombre);
            holder.txt_price_product.setText("$ " + precio);


            holder.btn_favorite.setImageDrawable(myActivity.getDrawable(R.drawable.ic_favorite));


            Glide.with(myActivity).load(imagen)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img_product);

            //Stock
            if (stock == 0)
                holder.txt_stock.setText("No hay stock");
            else
                holder.txt_stock.setText(stock + " en stock");


            holder.btn_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eliminarProductoFavorito(codigo);
                    Toast.makeText(myActivity, "Eliminado de favoritos", Toast.LENGTH_SHORT).show();
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
        return this.jsonFavorites.length();
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
            //txt_state_send = v.findViewById(R.id.txt_state_send);
            btn_favorite = v.findViewById(R.id.btn_favorite);
            btn_buy = v.findViewById(R.id.btn_buy);
            img_product = v.findViewById(R.id.img_product);

        }
    }
}