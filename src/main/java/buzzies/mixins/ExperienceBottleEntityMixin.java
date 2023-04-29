package buzzies.mixins;

import buzzies.settings.BuzziesSettings;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ExperienceBottleEntity.class)
public class ExperienceBottleEntityMixin {
    @Redirect(method = "onCollision",
              at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ExperienceOrbEntity;spawn(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/Vec3d;I)V"))
    private void changeExperienceValue(ServerWorld world, Vec3d pos, int amount) {
        if (BuzziesSettings.xpBottleDropValue == -1) {
            // -1 means this rule is off, so just do vanilla behaviour
            ExperienceOrbEntity.spawn(world, pos, amount);
        } else {
            ExperienceOrbEntity.spawn(world, pos, BuzziesSettings.xpBottleDropValue);
        }
    }
}
