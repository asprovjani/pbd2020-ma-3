package si.uni_lj.fri.pbd.miniapp3.ui.search;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import si.uni_lj.fri.pbd.miniapp3.R;
import si.uni_lj.fri.pbd.miniapp3.adapter.RecyclerViewAdapter;
import si.uni_lj.fri.pbd.miniapp3.adapter.SpinnerAdapter;
import si.uni_lj.fri.pbd.miniapp3.models.RecipeSummaryIM;
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
    private RecyclerViewAdapter rvAdapter;
    private TextView error;
    private RestAPI apiService;
    private SwipeRefreshLayout refreshLayout;

    private List<IngredientDTO> ingredients;
    private List<RecipeSummaryIM> recipes;
    private long startTime, endTime;

    private ConnectivityManager mNwManager;
    private ConnectivityManager.NetworkCallback mNwCallback;
    private NetworkInfo networkInfo;

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
        startTime = System.currentTimeMillis();
        apiService = ServiceGenerator.createService(RestAPI.class);
        progressBar = getActivity().findViewById(R.id.progress_bar);
        spinner = getActivity().findViewById(R.id.spinner);
        recyclerView = getActivity().findViewById(R.id.recipes_recycler_view);
        error = getActivity().findViewById(R.id.errorMsg);
        refreshLayout = getActivity().findViewById(R.id.ingredients_refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                endTime = System.currentTimeMillis();

                // if 5 seconds have passed since last refresh, fetch data
                if ((endTime - startTime) / 1000 >= 5) {
                    IngredientDTO selectedIngredient = (IngredientDTO) spinner.getSelectedItem();
                    networkInfo = mNwManager.getActiveNetworkInfo();
                    //if not connected to internet show error
                    if(networkInfo == null)
                        showError(getString(R.string.noInternet));
                    else {
                        getRecipesByIngredient(selectedIngredient.getStrIngredient());
                        rvAdapter.notifyDataSetChanged();
                    }
                }

                refreshLayout.setRefreshing(false);

                // update startTime
                startTime = endTime;
            }
        });

        mNwCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);
                //when connected to internet, fetch data
                getAllIngredients();
            }
        };

        // set up Connectivity Manager
        mNwManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        mNwManager.registerDefaultNetworkCallback(mNwCallback);

        // if not connected to internet show error
        networkInfo = mNwManager.getActiveNetworkInfo();
        if(networkInfo == null)
            showError(getString(R.string.noInternet));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNwManager.unregisterNetworkCallback(mNwCallback);
    }

    private void showError(String msg) {
        recyclerView.setVisibility(View.INVISIBLE);
        error.setText(msg);
        error.setVisibility(View.VISIBLE);
    }


    // configure spinner with adapter and onItemSelectedListener
    private void configureSpinner() {
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(ingredients, getContext());
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IngredientDTO selectedIngredient = (IngredientDTO) parent.getItemAtPosition(position);

                // show progressBar while recipes are being downloaded
                progressBar.setVisibility(View.VISIBLE);

                // if not connected to internet show error, else get recipe for selected ingredient
                networkInfo = mNwManager.getActiveNetworkInfo();
                if(networkInfo == null)
                    showError(getString(R.string.noInternet));
                else
                    getRecipesByIngredient(selectedIngredient.getStrIngredient());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // configure RecyclerView with adapter
    private void configureRecyclerView() {
        //RecyclerViewAdapter rvAdapter = new RecyclerViewAdapter("SearchFragment", getContext(), recipes);
        rvAdapter = new RecyclerViewAdapter("SearchFragment", getContext(), recipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(rvAdapter);
    }

    // get all ingredients to populate spinner
    private void getAllIngredients() {

        Call<IngredientsDTO> ingredientsCall = apiService.getAllIngredients();
        ingredientsCall.enqueue(new Callback<IngredientsDTO>() {
            @Override
            public void onResponse(Call<IngredientsDTO> call, Response<IngredientsDTO> response) {
                if (response.code() == 200) {
                    IngredientsDTO ingredientsCallResponse = response.body();
                    ingredients = ingredientsCallResponse.getIngredients();
                    // configure spinner with retrieved ingredients
                    configureSpinner();
                }
            }
            @Override
            public void onFailure(Call<IngredientsDTO> call, Throwable t) {
                Log.d(TAG, "getAllIngredients: ERROR");
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
                    // hide progressBar
                    progressBar.setVisibility(View.INVISIBLE);

                    // get recipes and convert them from DTO to IM
                    RecipesByIngredientDTO recipesByIngredientCall = response.body();
                    List<RecipeSummaryDTO> recipesDTOS = recipesByIngredientCall.getRecipes();

                    // if no recipes found show error
                    if(recipesDTOS == null) {
                        showError(getString(R.string.noRecipesExist));
                    }
                    else {
                        recipes = new ArrayList<>();
                        for(RecipeSummaryDTO r : recipesDTOS) {
                            RecipeSummaryIM rsIM = new RecipeSummaryIM(r.getStrMeal(), r.getStrMealThumb(), r.getIdMeal());
                            recipes.add(rsIM);
                        }

                        error.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        configureRecyclerView();
                    }
                }
            }

            @Override
            public void onFailure(Call<RecipesByIngredientDTO> call, Throwable t) {
                Log.d(TAG, "getRecipesByIngredient: ERROR");
            }
        });
    }
}
