package net.redstonia.verymanybuzzies.mixin;

import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.redstonia.verymanybuzzies.morecode.CustomBeehiveDecoratorBehavior;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeehiveTreeDecorator.class)
public abstract class BeehiveTreeDecoratorMixin {
    @Shadow @Final
    private float probability;

    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    private void onGenerate(TreeDecorator.Generator generator, CallbackInfo ci) {
        if (probability < 1.0f) {
            CustomBeehiveDecoratorBehavior.generateGuaranteed(generator);
            ci.cancel();
        }
    }

//    public void generateGuaranteed(TreeDecorator.Generator generator) {
//        Random random = generator.getRandom();
//        ObjectArrayList<BlockPos> leavesPositions = generator.getLeavesPositions();
//        ObjectArrayList<BlockPos> logPositions = generator.getLogPositions();
//        int yLevel = !leavesPositions.isEmpty() ? Math.max((leavesPositions.get(0)).getY() - 1, (logPositions.get(0)).getY() + 1) : Math.min((logPositions.get(0)).getY() + 1 + random.nextInt(3), (logPositions.get(logPositions.size() - 1)).getY());
//
//        List<BlockPos> potentialBeenestSpots = logPositions.stream().filter(pos -> pos.getY() == yLevel).flatMap(pos -> Stream.of(GENERATE_DIRECTIONS).map(pos::offset)).toList();
//        if (potentialBeenestSpots.isEmpty()) { return; }
//
//        List<BlockPos> validBeenestSpots = potentialBeenestSpots.stream().filter(pos -> generator.isAir(pos) && generator.isAir(pos.offset(BEE_NEST_FACE))).toList();
//        if (validBeenestSpots.isEmpty()) { return; }
//
//        for (BlockPos spot : validBeenestSpots) {
//            generator.replace(spot, Blocks.BEE_NEST.getDefaultState().with(BeehiveBlock.FACING, BEE_NEST_FACE));
//            // bees would normally be generated here.
//        }
//    }
}
