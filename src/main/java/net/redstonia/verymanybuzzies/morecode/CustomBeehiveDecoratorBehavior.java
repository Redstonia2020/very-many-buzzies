package net.redstonia.verymanybuzzies.morecode;

import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;

public class CustomBeehiveDecoratorBehavior {
    private static final BeehiveTreeDecorator guaranteedBeehiveGenerator = new BeehiveTreeDecorator(1.0f);

    public static void generateGuaranteed(TreeDecorator.Generator generator) {
        guaranteedBeehiveGenerator.generate(generator);
    }
}
