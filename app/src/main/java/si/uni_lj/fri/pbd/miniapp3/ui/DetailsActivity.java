package si.uni_lj.fri.pbd.miniapp3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import si.uni_lj.fri.pbd.miniapp3.R;
import si.uni_lj.fri.pbd.miniapp3.database.entity.RecipeDetails;
import si.uni_lj.fri.pbd.miniapp3.models.dto.RecipeDetailsDTO;
import si.uni_lj.fri.pbd.miniapp3.models.dto.RecipesByIdDTO;
import si.uni_lj.fri.pbd.miniapp3.rest.RestAPI;
import si.uni_lj.fri.pbd.miniapp3.rest.ServiceGenerator;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = "DetailsActivity";
    private String instantiatedBy;
    private String recipeID;

    private RestAPI apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        apiService = ServiceGenerator.createService(RestAPI.class);

        //get info about who started the activity and recipeID
        Intent i = getIntent();
        instantiatedBy =  i.getStringExtra("instantiatedBy");
        recipeID =  i.getStringExtra("recipeID");

        if(instantiatedBy.equals("SearchFragment"))
            getRecipeDetails();
        else {
            // TODO: pull data from local storage
        }
    }

    private void getRecipeDetails() {
        Call<RecipesByIdDTO> recipeByIdCall = apiService.getRecipeDetails(recipeID);
        recipeByIdCall.enqueue(new Callback<RecipesByIdDTO>() {
            @Override
            public void onResponse(Call<RecipesByIdDTO> call, Response<RecipesByIdDTO> response) {
                if(response.code() == 200) {
                    RecipesByIdDTO recipeByIdCallResponse = response.body();
                    RecipeDetailsDTO recipe = recipeByIdCallResponse.getRecipeDetails().get(0);
                    Glide.with(DetailsActivity.this)
                         .load(recipe.getStrMealThumb())
                         .into((ImageView)findViewById(R.id.recipeImage));

                    TextView recipeName = findViewById(R.id.recipeName);
                    recipeName.setText(recipe.getStrMeal());
                    TextView recipeArea = findViewById(R.id.recipeArea);
                    recipeArea.setText(recipe.getStrArea());
                    TextView ingredients = findViewById(R.id.ingredients);
                    ingredients.setText(recipe.getStrIngredient1() + ", " +
                                        recipe.getStrIngredient2() + "...");
                    TextView measurements = findViewById(R.id.measurements);
                    measurements.setText(recipe.getStrMeasure1() + ", " +
                                         recipe.getStrMeasure2() + "...");
                    TextView instructions = findViewById(R.id.instructions);
                    instructions.setText(recipe.getStrInstructions());
                }
            }

            @Override
            public void onFailure(Call<RecipesByIdDTO> call, Throwable t) {
                Log.d(TAG, "getRecipeDetails: ERROR");
            }
        });
    }
}
