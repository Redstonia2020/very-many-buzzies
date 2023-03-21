package net.redstonia.verymanybuzzies.mixin;

import net.redstonia.verymanybuzzies.VeryManyBuzzies;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeehiveTreeDecorator.class)
public class BeehiveTreeDecoratorMixin {
    @Redirect(method = "generate", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F"))
    private float ReturnNegative() {
        return -1f;
    }
}
