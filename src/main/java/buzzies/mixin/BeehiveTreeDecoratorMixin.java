package buzzies.mixin;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import buzzies.custom.BeenestAlwaysDecorator;
import buzzies.BuzziesSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(BeehiveTreeDecorator.class)
public abstract class BeehiveTreeDecoratorMixin
{
    @Shadow @Final
    private static Direction BEE_NEST_FACE;
    @Shadow @Final
    private float probability;

    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    private void onGenerate(TreeDecorator.Generator generator, CallbackInfo ci)
    {
        if (BuzziesSettings.beenestGeneration.cancelsDefaultDecorator() && probability < 1.0f) {
            ci.cancel();
            if (BuzziesSettings.beenestGeneration.usesModifiedDecorator()) {
                BeenestAlwaysDecorator.generate(generator);
            }
        }
    }

    @Inject(
            method = "generate",
            at = @At(value = "INVOKE", target = "Ljava/util/Collections;shuffle(Ljava/util/List;)V"),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void customGenerationLogic(TreeDecorator.Generator generator, CallbackInfo ci, Random random, List list, List list2, int i, List<BlockPos> list3) {
        // would be nice to get rid of random, list, list2 and i, but I've no braincells to get this to work
        if (BuzziesSettings.beenestGeneration == BuzziesSettings.BeenestGenerationOptions.ALWAYS_ALL) {
            ci.cancel();
            tryGenerateNestInSpots(generator, list3);
        }
    }

    private void tryGenerateNestInSpots(TreeDecorator.Generator generator, List<BlockPos> nestSpots) {
        List<BlockPos> beenestSpots = nestSpots.stream().filter(pos -> generator.isAir(pos) && generator.isAir((pos).offset(BEE_NEST_FACE))).toList();
        // feels messy cause just stolen directly from mojank code
        for (BlockPos beenestSpot : beenestSpots)
        {
            generator.replace(beenestSpot, Blocks.BEE_NEST.getDefaultState().with(BeehiveBlock.FACING, BEE_NEST_FACE));
        }
    }
}
