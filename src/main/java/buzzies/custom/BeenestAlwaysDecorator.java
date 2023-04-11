package buzzies.custom;

import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;

public class BeenestAlwaysDecorator
{
    private static final BeehiveTreeDecorator _generator = new BeehiveTreeDecorator(1.0f);

    public static void generate(TreeDecorator.Generator generator)
    {
        _generator.generate(generator);
    }
}
