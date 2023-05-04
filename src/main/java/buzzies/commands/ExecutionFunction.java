package buzzies.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

@FunctionalInterface
public interface ExecutionFunction<T extends Execution> {
    int run(T execution) throws CommandSyntaxException;
}
