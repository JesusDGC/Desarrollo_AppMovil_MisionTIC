package com.example.apppruebasmisiontic.ui.favorite;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.apppruebasmisiontic.R;
import com.example.apppruebasmisiontic.databinding.FragmentHomeBinding;
import com.example.apppruebasmisiontic.ui.util.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FavoritesFragment extends Fragment {


    private TextView txt_fav;
    private RecyclerView rev_products_fav;
    private RecyclerView.Adapter mAdapter;

    private SharedPreferences myPreference;


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

    //String jsonFavorites = "{\"favoritos\":[\"02\",\"05\",\"08\",\"10\"]}";

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
        String jsonFavorites = myPreference.getString("favorites","");
        try {
            JSONArray products = new JSONArray(jsonProducts);
            JSONArray favoriteProd = new JSONObject(jsonFavorites).getJSONArray("values");

            mAdapter = new ProductsAdapter(products,favoriteProd,getActivity());
            rev_products_fav.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return root;
    }


}


class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private JSONArray products;
    private JSONArray favoriteProd;
    private Activity myActivity;
    private int state = 0;

    //Contructor
    public ProductsAdapter(JSONArray products, JSONArray favoriteProd, Activity myActivity) {
        this.products = products;
        this.favoriteProd = favoriteProd;
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

        Log.e("POS_rec", "POS: " + position);
        int tam_pro = products.length();
        int tam_fav = favoriteProd.length();

        /*try {
            String favorite = favoriteProd.getString(position);
            holder.txt_name_product.setText(favorite);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        for(int i=0;i<tam_fav;i++){
            for(int j=0; j<tam_pro;j++){
                try {
                    String favorite = favoriteProd.getString(position);
                    String codigo = products.getJSONObject(j).getString("codigo");

                    if(favorite.equals(codigo)){
                        String nombre = products.getJSONObject(j).getString("nombre");
                        int precio = products.getJSONObject(j).getInt("precio");
                        int stock = products.getJSONObject(j).getInt("enstock");
                        int shipping_cost = products.getJSONObject(j).getInt("shipping_cost");
                        holder.txt_name_product.setText(nombre);
                        holder.txt_price_product.setText("$ " + precio);
                        holder.btn_favorite.setImageResource(R.drawable.ic_favorite);
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

                    }
                    //Glide.with(miActividad).load("http://goo.gl/gEgYUd").into(holder.imv_prodcuto);
                } catch (JSONException e) {
                    holder.txt_name_product.setText("error");
                }
            }
        }
    }

    //Obtiene la cantidad de objetos en el json
    @Override
    public int getItemCount() {
//        return userModelList.size();
        return this.favoriteProd.length();
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