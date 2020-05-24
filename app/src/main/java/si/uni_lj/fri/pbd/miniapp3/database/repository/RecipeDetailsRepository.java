package si.uni_lj.fri.pbd.miniapp3.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import si.uni_lj.fri.pbd.miniapp3.database.Database;
import si.uni_lj.fri.pbd.miniapp3.database.dao.RecipeDao;
import si.uni_lj.fri.pbd.miniapp3.database.entity.RecipeDetails;

public class RecipeDetailsRepository {

    private LiveData<List<RecipeDetails>> allRecipes;
    private LiveData<RecipeDetails> recipe;

    private RecipeDao recipeDao;

    public RecipeDetailsRepository(Application application) {
        Database db = Database.getDatabase(application.getApplicationContext());
        recipeDao = db.recipeDao();
        allRecipes = recipeDao.getRecipes();
    }

    public void insertRecipe(final RecipeDetails recipe) {
        Database.dbWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                recipeDao.insertRecipe(recipe);
            }
        });
    }

    public void deleteRecipe(final String recipeID) {
        Database.dbWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                recipeDao.deleteRecipeById(recipeID);
            }
        });
    }

    public LiveData<List<RecipeDetails>> getAllRecipes() {
        return this.allRecipes;
    }


    public RecipeDetails getRecipeDetails(String recipeID) {
        //may need fixing
        final RecipeDetails[] result = new RecipeDetails[1];
        Database.dbWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                result[0] = recipeDao.getRecipeById(recipeID);
            }
        });

        return result[0];
    }

}
