package si.uni_lj.fri.pbd.miniapp3.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import si.uni_lj.fri.pbd.miniapp3.database.entity.RecipeDetails;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM RecipeDetails WHERE idMeal = :idMeal")
    RecipeDetails getRecipeById(String idMeal);

    // TODO: Add the missing methods
    @Insert
    void insertRecipe(RecipeDetails recipeDetails);

    @Query("DELETE FROM RecipeDetails where idMeal = :idMeal")
    void deleteRecipeById(String idMeal);

    @Query("SELECT * FROM RecipeDetails")
    LiveData<List<RecipeDetails>> getRecipes();
}
