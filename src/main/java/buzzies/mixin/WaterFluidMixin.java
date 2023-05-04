package buzzies.mixin;

import buzzies.settings.BuzziesSettings;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WaterFluid.class)
public class WaterFluidMixin {
    @Inject(method = "getTickRate",
            at = @At("TAIL"),
            cancellable = true)
    private void changeTickRate(WorldView world, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(BuzziesSettings.waterFlowRate);
    }
}
