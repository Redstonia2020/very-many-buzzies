package buzzies.mixin;

import buzzies.settings.BuzziesSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {
    @Redirect(method = "convertInWater",
              at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityType;DROWNED:Lnet/minecraft/entity/EntityType;"))
    private EntityType drierDrown() {
        if (BuzziesSettings.zombiesResistDrowning)
            return EntityType.HUSK;
        return EntityType.DROWNED;
    }
}
