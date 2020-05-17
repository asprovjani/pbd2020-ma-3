package si.uni_lj.fri.pbd.miniapp3.ui.search;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import si.uni_lj.fri.pbd.miniapp3.R;
import si.uni_lj.fri.pbd.miniapp3.adapter.RecyclerViewAdapter;
import si.uni_lj.fri.pbd.miniapp3.adapter.SpinnerAdapter;
import si.uni_lj.fri.pbd.miniapp3.models.dto.IngredientDTO;
import si.uni_lj.fri.pbd.miniapp3.models.dto.IngredientsDTO;
import si.uni_lj.fri.pbd.miniapp3.models.dto.RecipeSummaryDTO;
import si.uni_lj.fri.pbd.miniapp3.models.dto.RecipesByIngredientDTO;
import si.uni_lj.fri.pbd.miniapp3.rest.RestAPI;
import si.uni_lj.fri.pbd.miniapp3.rest.ServiceGenerator;


public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";

    private Spinner spinner;
    private MaterialProgressBar progressBar;
    private RecyclerView recyclerView;
    private RestAPI apiService;

    private List<IngredientDTO> ingredients;
    private List<RecipeSummaryDTO> recipes;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        apiService = ServiceGenerator.createService(RestAPI.class);
        progressBar = getActivity().findViewById(R.id.progress_bar);
        spinner = getActivity().findViewById(R.id.spinner);
        recyclerView = getActivity().findViewById(R.id.recipes_recycler_view);

        getAllIngredients();
    }

    // configure spinner with adapter and onItemSelectedListener
    private void configureSpinner() {
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(ingredients, getContext());
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IngredientDTO selectedIngredient = (IngredientDTO) parent.getItemAtPosition(position);

                //show progressBar while recipes are being downloaded
                progressBar.setVisibility(View.VISIBLE);

                getRecipesByIngredient(selectedIngredient.getStrIngredient());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // configure RecyclerView with adapter
    private void configureRecyclerView() {
        RecyclerViewAdapter rvAdapter = new RecyclerViewAdapter("SearchFragment", getContext(),recipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(rvAdapter);
    }

    // get all ingredients to populate spinner
    private void getAllIngredients() {
        Call<IngredientsDTO> ingredientsCall = apiService.getAllIngredients();
        ingredientsCall.enqueue(new Callback<IngredientsDTO>() {
            @Override
            public void onResponse(Call<IngredientsDTO> call, Response<IngredientsDTO> response) {
                if(response.code() == 200) {
                    IngredientsDTO ingredientsCallResponse = response.body();
                    ingredients = ingredientsCallResponse.getIngredients();

                    //configure spinner with retrieved ingredients
                    configureSpinner();

                    //for(IngredientDTO i : ingredients)
                    //    Log.d(TAG, "onResponse: " + i.getStrIngredient());
                }
            }

            @Override
            public void onFailure(Call<IngredientsDTO> call, Throwable t) {
                Toast.makeText(getContext(), "PROBLEM", Toast.LENGTH_LONG);
            }
        });
    }

    // get recipes by selected ingredient
    private void getRecipesByIngredient(String ingredient) {
        Call<RecipesByIngredientDTO> recipesByIngredientCall = apiService.getRecipesByIngredient(ingredient);
        recipesByIngredientCall.enqueue(new Callback<RecipesByIngredientDTO>() {
            @Override
            public void onResponse(Call<RecipesByIngredientDTO> call, Response<RecipesByIngredientDTO> response) {
                if(response.code() == 200) {
                    progressBar.setVisibility(View.INVISIBLE);
                    RecipesByIngredientDTO recipesByIngredientCall = response.body();
                    recipes = recipesByIngredientCall.getRecipes();
                    configureRecyclerView();

                    //for(RecipeSummaryDTO r : recipes)
                    //   Log.d(TAG, "onResponse: " + r.getStrMeal());
                }
            }

            @Override
            public void onFailure(Call<RecipesByIngredientDTO> call, Throwable t) {
                Log.d(TAG, "getRecipesByIngredient: ERROR");
            }
        });
    }
}
