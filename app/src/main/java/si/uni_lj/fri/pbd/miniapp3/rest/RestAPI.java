package si.uni_lj.fri.pbd.miniapp3.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import si.uni_lj.fri.pbd.miniapp3.models.dto.IngredientsDTO;
import si.uni_lj.fri.pbd.miniapp3.models.dto.RecipesByIngredientDTO;

public interface RestAPI {

    @GET("list.php?i=list")
    Call<IngredientsDTO> getAllIngredients();

    // TODO: Add missing endpoints
    @GET("filter.php")
    Call<RecipesByIngredientDTO> getRecipesByIngredient(@Query("i") String ingredient);
}