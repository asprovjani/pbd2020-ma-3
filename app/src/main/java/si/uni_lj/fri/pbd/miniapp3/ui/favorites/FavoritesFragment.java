package si.uni_lj.fri.pbd.miniapp3.ui.favorites;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import si.uni_lj.fri.pbd.miniapp3.R;


public class FavoritesFragment extends Fragment {

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }
}
