package org.zeith.modid.recipe;

import net.minecraftforge.common.Tags;
import org.zeith.hammerlib.annotations.*;
import org.zeith.hammerlib.api.IRecipeProvider;
import org.zeith.hammerlib.event.recipe.RegisterRecipesEvent;

import static org.zeith.modid.init.ItemsMI.WAND;

@ProvideRecipes
public class ModRecipe implements IRecipeProvider {
    @Override
    public void provideRecipes(RegisterRecipesEvent event) {

        event.shaped()
                .result(WAND)
                .shape("aaa", "npn", "npn")
                .map('n', Tags.Items.NUGGETS_IRON)
                .map('a', Tags.Items.GEMS_AMETHYST)
                .map('p', Tags.Items.GEMS_DIAMOND)
                .register();
    }
}
