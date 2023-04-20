package buzzies.side;

import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;

public class BeeNestDecoratorAlways {
    private static final BeehiveTreeDecorator decorator = new BeehiveTreeDecorator(1.0f);

    public static void generate(TreeDecorator.Generator generator) {
        decorator.generate(generator);
    }
}
