package buzzies.commands;

@FunctionalInterface
public interface ExecutionFunction<T extends Execution> {
    int run(T execution);
}
