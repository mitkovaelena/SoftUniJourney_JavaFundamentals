package hell.entities.miscellaneous;

import hell.interfaces.Inventory;
import hell.interfaces.Item;
import hell.interfaces.Recipe;
import hell.models.items.CommonItem;

import java.util.*;

public class HeroInventory implements Inventory {

    @ItemCollection
    private Map<String, Item> commonItems;

    private Map<String, Recipe> recipeItems;

    public HeroInventory() {
        this.commonItems = new LinkedHashMap<String, Item>();
        this.recipeItems = new LinkedHashMap<String, Recipe>();
    }

    @Override
    public long getTotalStrengthBonus() {
        return this.commonItems.entrySet().stream().mapToLong((x) -> x.getValue().getStrengthBonus()).sum();
    }

    @Override
    public long getTotalAgilityBonus() {
        return this.commonItems.entrySet().stream().mapToLong((x) -> x.getValue().getAgilityBonus()).sum();
    }

    @Override
    public long getTotalIntelligenceBonus() {
        return this.commonItems.entrySet().stream().mapToLong((x) -> x.getValue().getIntelligenceBonus()).sum();
    }

    @Override
    public long getTotalHitPointsBonus() {
        return this.commonItems.entrySet().stream().mapToLong((x) -> x.getValue().getHitPointsBonus()).sum();
    }

    @Override
    public long getTotalDamageBonus() {
        return this.commonItems.entrySet().stream().mapToLong((x) -> x.getValue().getDamageBonus()).sum();
    }

    @Override
    public void addCommonItem(Item item) {
        this.commonItems.put(item.getName(), item);
        this.checkRecipes();
    }

    @Override
    public void addRecipeItem(Recipe recipe) {
        this.recipeItems.put(recipe.getName(), recipe);
        this.checkRecipes();
    }

    private void checkRecipes() {
        List<String> usedRecepies = new ArrayList<>();
        for (String  recipe : this.recipeItems.keySet()) {
            boolean isForCombinig = true;
            List<String> requiredItems = this.recipeItems.get(recipe).getRequiredItems();

            for (String item : requiredItems) {
                if (!this.commonItems.containsKey(item)) {
                    isForCombinig = false;
                    break;
                }
            }

            if (isForCombinig) {
                usedRecepies.add(recipe);
                this.combineRecipe(this.recipeItems.get(recipe));
            }
        }

        for(String name : usedRecepies){
            this.recipeItems.remove(name);
        }
    }

    private void combineRecipe(Recipe recipe) {

        for (int i = 0; i < recipe.getRequiredItems().size(); i++) {
            String item = recipe.getRequiredItems().get(i);
            this.commonItems.remove(item);
        }

        Item newItem = new CommonItem(recipe.getName(), recipe.getStrengthBonus(), recipe.getAgilityBonus(), recipe.getIntelligenceBonus(), recipe.getHitPointsBonus(), recipe.getDamageBonus());

        this.commonItems.put(newItem.getName(), newItem);
    }
}