package buzzies.mixin;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import buzzies.CarpetBuzziesSettings;
import buzzies.CarpetBuzziesSettings.BeeNestGenerationOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(BeehiveTreeDecorator.class)
public abstract class BeehiveTreeDecoratorMixin {
    @Shadow @Final
    private static Direction BEE_NEST_FACE;

    @Inject(method = "generate",
            at = @At("HEAD"),
            cancellable = true)
    private void cancelGeneration(TreeDecorator.Generator generator, CallbackInfo ci) {
        if (CarpetBuzziesSettings.beeNestGeneration == BeeNestGenerationOptions.OFF) {
            ci.cancel();
        }
    }

    @Redirect(method = "generate",
              at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextFloat()F"))
    private float alwaysGenerate(Random random) {
        if (CarpetBuzziesSettings.beeNestGeneration.alwaysGenerates())
            return 0f;
        return random.nextFloat();
    }

    @Inject(method = "generate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/treedecorator/TreeDecorator$Generator;replace(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V", shift = At.Shift.AFTER),
            cancellable = true)
    private void preventBees(TreeDecorator.Generator generator, CallbackInfo ci) {
        if (!CarpetBuzziesSettings.beeNestsGenerateBees) {
            ci.cancel();
        }
    }

    @Inject(method = "generate",
            at = @At(value = "INVOKE", target = "Ljava/util/Collections;shuffle(Ljava/util/List;)V"),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void generateInAllPositions(TreeDecorator.Generator generator, CallbackInfo ci, Random random, List list, List list2, int i, List<BlockPos> list3) {
        // would be nice to get rid of unused things, but I've no braincells to get that to work
        if (CarpetBuzziesSettings.beeNestGeneration == CarpetBuzziesSettings.BeeNestGenerationOptions.ALWAYS_ALL) {
            ci.cancel();
            tryGenerateNestInPositions(generator, list3);
        }
    }

    private void tryGenerateNestInPositions(TreeDecorator.Generator generator, List<BlockPos> nestPositions) {
        List<BlockPos> validPositions = nestPositions.stream()
                .filter(pos -> generator.isAir(pos) && generator.isAir(pos.offset(BEE_NEST_FACE)))
                .toList();
        for (BlockPos pos : validPositions) {
            generator.replace(pos, Blocks.BEE_NEST.getDefaultState().with(BeehiveBlock.FACING, BEE_NEST_FACE));
        }
    }
}
