package si.uni_lj.fri.pbd.miniapp3;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import si.uni_lj.fri.pbd.miniapp3.database.entity.RecipeDetails;
import si.uni_lj.fri.pbd.miniapp3.database.repository.RecipeDetailsRepository;

public class MainViewModel extends AndroidViewModel {

    private RecipeDetailsRepository repository;
    private RecipeDetails recipe;

    private LiveData<List<RecipeDetails>> recipes;


    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = new RecipeDetailsRepository(application);
        recipes = repository.getAllRecipes();
    }

    public LiveData<List<RecipeDetails>> getAllRecipes() {
        return recipes;
    }

    public RecipeDetails getRecipe(String recipeID) {
        return repository.getRecipeDetails(recipeID);
    }

    public void insertRecipe(RecipeDetails recipe) {
        repository.insertRecipe(recipe);
    }

    public void deleteRecipe(String recipeID) {
        repository.deleteRecipe(recipeID);
    }
}
