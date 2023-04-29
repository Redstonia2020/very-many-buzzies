package buzzies.mixins;

import net.minecraft.fluid.LavaFluid;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static buzzies.settings.BuzziesSettings.lavaFlowRate;
import static buzzies.settings.BuzziesSettings.lavaFlowRateUltrawarm;

@Mixin(LavaFluid.class)
public class LavaFluidMixin {
    @Inject(method = "getTickRate",
            at = @At("TAIL"),
            cancellable = true)
    private void changeTickRate(WorldView world, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(world.getDimension().ultrawarm() ? lavaFlowRateUltrawarm : lavaFlowRate);
    }
}
