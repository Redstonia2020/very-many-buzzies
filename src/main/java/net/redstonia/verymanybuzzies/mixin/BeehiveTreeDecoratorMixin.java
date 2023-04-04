package net.redstonia.verymanybuzzies.mixin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Stream;

@Mixin(BeehiveTreeDecorator.class)
public class BeehiveTreeDecoratorMixin {
    @Shadow @Final
    private static Direction BEE_NEST_FACE;
    @Shadow @Final
    private static Direction[] GENERATE_DIRECTIONS;

    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    private void onGenerate(TreeDecorator.Generator generator, CallbackInfo ci) {
        generateGuaranteed(generator);
        ci.cancel();
    }

    public void generateGuaranteed(TreeDecorator.Generator generator) {
        Random random = generator.getRandom();
        ObjectArrayList<BlockPos> leavesPositions = generator.getLeavesPositions();
        ObjectArrayList<BlockPos> logPositions = generator.getLogPositions();
        int yLevel = !leavesPositions.isEmpty() ? Math.max((leavesPositions.get(0)).getY() - 1, (logPositions.get(0)).getY() + 1) : Math.min((logPositions.get(0)).getY() + 1 + random.nextInt(3), (logPositions.get(logPositions.size() - 1)).getY());

        List<BlockPos> potentialBeenestSpots = logPositions.stream().filter(pos -> pos.getY() == yLevel).flatMap(pos -> Stream.of(GENERATE_DIRECTIONS).map(pos::offset)).toList();
        if (potentialBeenestSpots.isEmpty()) { return; }

        List<BlockPos> validBeenestSpots = potentialBeenestSpots.stream().filter(pos -> generator.isAir(pos) && generator.isAir(pos.offset(BEE_NEST_FACE))).toList();
        if (validBeenestSpots.isEmpty()) { return; }

        for (BlockPos spot : validBeenestSpots) {
            generator.replace(spot, Blocks.BEE_NEST.getDefaultState().with(BeehiveBlock.FACING, BEE_NEST_FACE));
            // bees would normally be generated here.
        }
    }
}
