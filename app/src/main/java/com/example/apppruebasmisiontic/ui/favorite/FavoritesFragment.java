package com.example.apppruebasmisiontic.ui.favorite;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apppruebasmisiontic.R;
import com.example.apppruebasmisiontic.databinding.FragmentHomeBinding;

public class FavoritesFragment extends Fragment {


    private FavoritesViewModel favoriteViewModel;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        favoriteViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        // TODO: Use the ViewModel
    }

}