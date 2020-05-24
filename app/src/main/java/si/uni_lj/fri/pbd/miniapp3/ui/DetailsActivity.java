package si.uni_lj.fri.pbd.miniapp3.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.amitshekhar.DebugDB;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import si.uni_lj.fri.pbd.miniapp3.MainViewModel;
import si.uni_lj.fri.pbd.miniapp3.R;
import si.uni_lj.fri.pbd.miniapp3.database.entity.RecipeDetails;
import si.uni_lj.fri.pbd.miniapp3.database.repository.RecipeDetailsRepository;
import si.uni_lj.fri.pbd.miniapp3.models.Mapper;
import si.uni_lj.fri.pbd.miniapp3.models.RecipeDetailsIM;
import si.uni_lj.fri.pbd.miniapp3.models.dto.RecipeDetailsDTO;
import si.uni_lj.fri.pbd.miniapp3.models.dto.RecipesByIdDTO;
import si.uni_lj.fri.pbd.miniapp3.rest.RestAPI;
import si.uni_lj.fri.pbd.miniapp3.rest.ServiceGenerator;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = "DetailsActivity";
    boolean checked = false;

    private RecipeDetailsIM recipeIM;
    private RecipeDetailsDTO recipeDTO;
    private String instantiatedBy;
    private String recipeID;

    private RestAPI apiService;
    private MainViewModel mViewModel;

    private ImageView recipeImage;
    private TextView recipeTitle;
    private TextView recipeArea;
    private TextView ingredients;
    private TextView measurements;
    private TextView instructions;
    private ToggleButton favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + DebugDB.getAddressLog());
        setContentView(R.layout.activity_details);

        apiService = ServiceGenerator.createService(RestAPI.class);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        recipeImage = findViewById(R.id.recipeImage);
        recipeTitle = findViewById(R.id.recipeTitle);
        recipeArea = findViewById(R.id.recipeArea);
        ingredients = findViewById(R.id.ingredients);
        measurements = findViewById(R.id.measurements);
        instructions = findViewById(R.id.instructions);
        favoriteButton = findViewById(R.id.favoriteButton);

        //get info about who started the activity and recipeID
        Intent i = getIntent();
        instantiatedBy =  i.getStringExtra("instantiatedBy");
        recipeID =  i.getStringExtra("recipeID");

        if(instantiatedBy.equals("SearchFragment")) {
            getRecipeDetails();
            mViewModel.getAllRecipes().observe(this, new Observer<List<RecipeDetails>>() {
                @Override
                public void onChanged(List<RecipeDetails> recipeDetails) {
                    for(RecipeDetails r : recipeDetails) {
                        if(r.getIdMeal().equals(recipeID))
                            if(r.getFavorite()) {
                                favoriteButton.setChecked(true);
                                checked = true;
                            }
                    }
                }
            });
        }
        else {
            getRecipeDetailsLocalStorage();
        }
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checked) {
                    Log.d(TAG, "onCheckedChanged: IMA");
                    mViewModel.insertRecipe(Mapper.mapRecipeDetailsDtoToRecipeDetails(true, recipeDTO));
                    checked = true;
                }
                else {
                    Log.d(TAG, "onCheckedChanged: NEMA");
                    mViewModel.deleteRecipe(recipeID);
                    checked = false;
                }
            }
        });

    }

    private void getRecipeDetails() {
        Call<RecipesByIdDTO> recipeByIdCall = apiService.getRecipeDetails(recipeID);
        recipeByIdCall.enqueue(new Callback<RecipesByIdDTO>() {
            @Override
            public void onResponse(Call<RecipesByIdDTO> call, Response<RecipesByIdDTO> response) {
                if(response.code() == 200) {
                    RecipesByIdDTO recipeByIdCallResponse = response.body();
                    recipeDTO = recipeByIdCallResponse.getRecipeDetails().get(0);
                    recipeIM = Mapper.mapRecipeDetailsDtoToRecipeDetailsIm(false, recipeDTO);
                    updateUI(recipeIM);
                }
            }

            @Override
            public void onFailure(Call<RecipesByIdDTO> call, Throwable t) {
                Log.d(TAG, "getRecipeDetails: ERROR");
            }
        });
    }

    private void getRecipeDetailsLocalStorage() {
        RecipeDetails r = mViewModel.getRecipe(recipeID);
        if(r == null) {
            Log.d(TAG, "getRecipeDetailsLocalStorage: NULL");
        }
    }

    private void updateUI(RecipeDetailsIM r) {
        Glide.with(DetailsActivity.this).load(r.getStrMealThumb()).into(recipeImage);
        recipeTitle.setText(r.getStrMeal());
        recipeArea.setText(r.getStrArea());
        ingredients.setText(getIngredients(r, new ArrayList<String>()));
        measurements.setText(getMeasurements(r, new ArrayList<String>()));
        instructions.setText(r.getStrInstructions());
    }

    private String getIngredients(RecipeDetailsIM r, ArrayList<String> ingredients) {
        ingredients.add(checkNull(r.getStrIngredient1()));
        ingredients.add(checkNull(r.getStrIngredient2()));
        ingredients.add(checkNull(r.getStrIngredient3()));
        ingredients.add(checkNull(r.getStrIngredient4()));
        ingredients.add(checkNull(r.getStrIngredient5()));
        ingredients.add(checkNull(r.getStrIngredient6()));
        ingredients.add(checkNull(r.getStrIngredient7()));
        ingredients.add(checkNull(r.getStrIngredient8()));
        ingredients.add(checkNull(r.getStrIngredient9()));
        ingredients.add(checkNull(r.getStrIngredient10()));
        ingredients.add(checkNull(r.getStrIngredient11()));
        ingredients.add(checkNull(r.getStrIngredient12()));
        ingredients.add(checkNull(r.getStrIngredient13()));
        ingredients.add(checkNull(r.getStrIngredient14()));
        ingredients.add(checkNull(r.getStrIngredient15()));
        ingredients.add(checkNull(r.getStrIngredient16()));
        ingredients.add(checkNull(r.getStrIngredient17()));
        ingredients.add(checkNull(r.getStrIngredient18()));
        ingredients.add(checkNull(r.getStrIngredient19()));
        ingredients.add(checkNull(r.getStrIngredient20()));

        String result = "";
        for(String ingredient : ingredients) {
            if(!ingredient.equals(""))
                result += ingredient + ", ";
        }

        return result.subSequence(0, result.length() - 2) + ".";
    }

    private String getMeasurements(RecipeDetailsIM r, ArrayList<String> measurements) {
        measurements.add(checkNull(r.getStrMeasure1()));
        measurements.add(checkNull(r.getStrMeasure2()));
        measurements.add(checkNull(r.getStrMeasure3()));
        measurements.add(checkNull(r.getStrMeasure4()));
        measurements.add(checkNull(r.getStrMeasure5()));
        measurements.add(checkNull(r.getStrMeasure6()));
        measurements.add(checkNull(r.getStrMeasure7()));
        measurements.add(checkNull(r.getStrMeasure8()));
        measurements.add(checkNull(r.getStrMeasure9()));
        measurements.add(checkNull(r.getStrMeasure10()));
        measurements.add(checkNull(r.getStrMeasure11()));
        measurements.add(checkNull(r.getStrMeasure12()));
        measurements.add(checkNull(r.getStrMeasure13()));
        measurements.add(checkNull(r.getStrMeasure14()));
        measurements.add(checkNull(r.getStrMeasure15()));
        measurements.add(checkNull(r.getStrMeasure16()));
        measurements.add(checkNull(r.getStrMeasure17()));
        measurements.add(checkNull(r.getStrMeasure18()));
        measurements.add(checkNull(r.getStrMeasure19()));
        measurements.add(checkNull(r.getStrMeasure20()));

        String result = "";
        for(String measurement : measurements) {
            if(!measurement.equals(""))
                result += measurement + ", ";
        }

        return result.subSequence(0, result.length() - 2) + ".";
    }

    private String checkNull(String s) {
        return s != null ? s : "";
    }
}
