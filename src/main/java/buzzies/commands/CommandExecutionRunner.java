package buzzies.commands;

@FunctionalInterface
public interface CommandExecutionRunner<T extends CommandExecution> {
    int run(T execution);
}
