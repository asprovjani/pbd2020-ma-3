package si.uni_lj.fri.pbd.miniapp3.models;

import si.uni_lj.fri.pbd.miniapp3.database.entity.RecipeDetails;
import si.uni_lj.fri.pbd.miniapp3.models.dto.RecipeDetailsDTO;

public final class Mapper {

    public static RecipeSummaryIM mapRecipeDetailsToRecipeSummaryIm(RecipeDetails rec) {
        return new RecipeSummaryIM(rec.getStrMeal(), rec.getStrMealThumb(), rec.getIdMeal());
    }

    // TODO: Uncomment the code below

    public static RecipeDetails mapRecipeDetailsDtoToRecipeDetails(Boolean isFavorite, RecipeDetailsDTO dto) {
        return new RecipeDetails(
                isFavorite,
                dto.getIdMeal(),
                dto.getStrMeal(),
                dto.getStrCategory(),
                dto.getStrArea(),
                dto.getStrInstructions(),
                dto.getStrMealThumb(),
                dto.getStrYoutube(),
                dto.getStrIngredient1(),
                dto.getStrIngredient2(),
                dto.getStrIngredient3(),
                dto.getStrIngredient4(),
                dto.getStrIngredient5(),
                dto.getStrIngredient6(),
                dto.getStrIngredient7(),
                dto.getStrIngredient8(),
                dto.getStrIngredient9(),
                dto.getStrIngredient10(),
                dto.getStrIngredient11(),
                dto.getStrIngredient12(),
                dto.getStrIngredient13(),
                dto.getStrIngredient14(),
                dto.getStrIngredient15(),
                dto.getStrIngredient16(),
                dto.getStrIngredient17(),
                dto.getStrIngredient18(),
                dto.getStrIngredient19(),
                dto.getStrIngredient20(),
                dto.getStrMeasure1(),
                dto.getStrMeasure2(),
                dto.getStrMeasure3(),
                dto.getStrMeasure4(),
                dto.getStrMeasure5(),
                dto.getStrMeasure6(),
                dto.getStrMeasure7(),
                dto.getStrMeasure8(),
                dto.getStrMeasure9(),
                dto.getStrMeasure10(),
                dto.getStrMeasure11(),
                dto.getStrMeasure12(),
                dto.getStrMeasure13(),
                dto.getStrMeasure14(),
                dto.getStrMeasure15(),
                dto.getStrMeasure16(),
                dto.getStrMeasure17(),
                dto.getStrMeasure18(),
                dto.getStrMeasure19(),
                dto.getStrMeasure20(),
                dto.getStrSource());
    }

    public static RecipeDetailsIM mapRecipeDetailsDtoToRecipeDetailsIm(Boolean isFavorite, RecipeDetailsDTO dto) {
        return new RecipeDetailsIM(isFavorite,
                dto.getIdMeal(),
                dto.getStrMeal(),
                dto.getStrCategory(),
                dto.getStrArea(),
                dto.getStrInstructions(),
                dto.getStrMealThumb(),
                dto.getStrYoutube(),
                dto.getStrIngredient1(),
                dto.getStrIngredient2(),
                dto.getStrIngredient3(),
                dto.getStrIngredient4(),
                dto.getStrIngredient5(),
                dto.getStrIngredient6(),
                dto.getStrIngredient7(),
                dto.getStrIngredient8(),
                dto.getStrIngredient9(),
                dto.getStrIngredient10(),
                dto.getStrIngredient11(),
                dto.getStrIngredient12(),
                dto.getStrIngredient13(),
                dto.getStrIngredient14(),
                dto.getStrIngredient15(),
                dto.getStrIngredient16(),
                dto.getStrIngredient17(),
                dto.getStrIngredient18(),
                dto.getStrIngredient19(),
                dto.getStrIngredient20(),
                dto.getStrMeasure1(),
                dto.getStrMeasure2(),
                dto.getStrMeasure3(),
                dto.getStrMeasure4(),
                dto.getStrMeasure5(),
                dto.getStrMeasure6(),
                dto.getStrMeasure7(),
                dto.getStrMeasure8(),
                dto.getStrMeasure9(),
                dto.getStrMeasure10(),
                dto.getStrMeasure11(),
                dto.getStrMeasure12(),
                dto.getStrMeasure13(),
                dto.getStrMeasure14(),
                dto.getStrMeasure15(),
                dto.getStrMeasure16(),
                dto.getStrMeasure17(),
                dto.getStrMeasure18(),
                dto.getStrMeasure19(),
                dto.getStrMeasure20(),
                dto.getStrSource());
    }

    public static RecipeDetailsIM mapRecipeDetailsToRecipeDetailsIm(Boolean isFavorite, RecipeDetails dto) {
        return new RecipeDetailsIM(
                isFavorite,
                dto.getIdMeal(),
                dto.getStrMeal(),
                dto.getStrCategory(),
                dto.getStrArea(),
                dto.getStrInstructions(),
                dto.getStrMealThumb(),
                dto.getStrYoutube(),
                dto.getStrIngredient1(),
                dto.getStrIngredient2(),
                dto.getStrIngredient3(),
                dto.getStrIngredient4(),
                dto.getStrIngredient5(),
                dto.getStrIngredient6(),
                dto.getStrIngredient7(),
                dto.getStrIngredient8(),
                dto.getStrIngredient9(),
                dto.getStrIngredient10(),
                dto.getStrIngredient11(),
                dto.getStrIngredient12(),
                dto.getStrIngredient13(),
                dto.getStrIngredient14(),
                dto.getStrIngredient15(),
                dto.getStrIngredient16(),
                dto.getStrIngredient17(),
                dto.getStrIngredient18(),
                dto.getStrIngredient19(),
                dto.getStrIngredient20(),
                dto.getStrMeasure1(),
                dto.getStrMeasure2(),
                dto.getStrMeasure3(),
                dto.getStrMeasure4(),
                dto.getStrMeasure5(),
                dto.getStrMeasure6(),
                dto.getStrMeasure7(),
                dto.getStrMeasure8(),
                dto.getStrMeasure9(),
                dto.getStrMeasure10(),
                dto.getStrMeasure11(),
                dto.getStrMeasure12(),
                dto.getStrMeasure13(),
                dto.getStrMeasure14(),
                dto.getStrMeasure15(),
                dto.getStrMeasure16(),
                dto.getStrMeasure17(),
                dto.getStrMeasure18(),
                dto.getStrMeasure19(),
                dto.getStrMeasure20(),
                dto.getStrSource());
    }

}
