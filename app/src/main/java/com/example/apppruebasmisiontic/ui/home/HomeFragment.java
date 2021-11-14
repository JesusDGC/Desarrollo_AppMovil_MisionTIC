package com.example.apppruebasmisiontic.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.apppruebasmisiontic.R;
import com.example.apppruebasmisiontic.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private TextView txt_home;
    private Spinner spn_category;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        txt_home = root.findViewById(R.id.txt_home);
        spn_category = root.findViewById(R.id.spn_category);

        txt_home.setText("This is home fragment");

        //Opcion 1: String array de la carpeta values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories,
                android.R.layout.simple_spinner_item);

        spn_category.setAdapter(adapter);

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



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}