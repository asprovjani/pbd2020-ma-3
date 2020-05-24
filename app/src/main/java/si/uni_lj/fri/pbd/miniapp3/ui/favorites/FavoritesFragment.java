package si.uni_lj.fri.pbd.miniapp3.ui.favorites;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import si.uni_lj.fri.pbd.miniapp3.MainViewModel;
import si.uni_lj.fri.pbd.miniapp3.R;
import si.uni_lj.fri.pbd.miniapp3.adapter.RecyclerViewAdapter;
import si.uni_lj.fri.pbd.miniapp3.database.entity.RecipeDetails;
import si.uni_lj.fri.pbd.miniapp3.models.Mapper;
import si.uni_lj.fri.pbd.miniapp3.models.RecipeSummaryIM;


public class FavoritesFragment extends Fragment {
    private static final String TAG = "FavoritesFragment";
    private RecyclerView recyclerView;
    private TextView noRecipes;

    private MainViewModel mViewModel;

    List<RecipeSummaryIM> recipes;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel =  new ViewModelProvider(this).get(MainViewModel.class);
        recyclerView = getActivity().findViewById(R.id.favorite_recipes_recycler_view);
        noRecipes = getActivity().findViewById(R.id.noFavoriteRecipes);
        getRecipes();
    }

    // get Recipes from DB
    private void getRecipes() {
        mViewModel.getAllRecipes().observe(getViewLifecycleOwner(), new Observer<List<RecipeDetails>>() {
            @Override
            public void onChanged(List<RecipeDetails> recipeDetails) {
                recipes = new ArrayList<>();
                for(RecipeDetails r : recipeDetails) {
                  recipes.add(Mapper.mapRecipeDetailsToRecipeSummaryIm(r));
                }

                // if no recipes found show error, else show favorite recipes
                if(recipes.size() == 0) {
                    recyclerView.setVisibility(View.INVISIBLE);
                    noRecipes.setVisibility(View.VISIBLE);
                }
                else {
                    noRecipes.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    configureRecyclerView();
                }
            }
        });
    }

    // configure RecyclerView with adapter
    private void configureRecyclerView() {
        RecyclerViewAdapter rvAdapter = new RecyclerViewAdapter("FavoritesFragment", getContext(), recipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(rvAdapter);
    }

}
