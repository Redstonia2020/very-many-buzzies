package buzzies.side;

import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;

public class BeenestDecoratorAlways {
    private static final BeehiveTreeDecorator generator = new BeehiveTreeDecorator(1.0f);

    public static void generate(TreeDecorator.Generator generator) {
        BeenestDecoratorAlways.generator.generate(generator);
    }
}
