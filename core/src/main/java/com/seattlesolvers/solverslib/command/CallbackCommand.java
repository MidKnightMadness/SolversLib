package com.seattlesolvers.solverslib.command;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

/**
 * Schedules a Command as uninterruptible
 * @author Daniel - FTC 7854
 */
public class CallbackCommand extends CommandBase {
    private final Map<BooleanSupplier, Runnable> whenRunnables = new HashMap<>();
    private final Map<BooleanSupplier, Command> whenCommands = new HashMap<>();
    private final Map<Function<Command, Boolean>, Runnable> whenSelfRunnables = new HashMap<>();
    private final Map<Function<Command, Boolean>, Command> whenSelfCommands = new HashMap<>();
    private final Command command;

    /**
     * Wrapper for adding custom callbacks to commands. This expects a single command,
     * so multiple commands need to be put in a CommandGroup first:
     * @param command the command to be schedules as uninterruptible
     * {@link SequentialCommandGroup}
     * {@link ParallelCommandGroup}
     */
    public CallbackCommand(Command command) {
        this.command = command;
    }

    /**
     * Adds a callback with a boolean supplier
     * @param condition Runs the runnable the first time this is true
     * @param runnable Callback to run
     * @return Itself for chaining purposes
     */
    @Override
    public CallbackCommand when(BooleanSupplier condition, Runnable runnable) {
        whenRunnables.put(condition, runnable);
        return this;
    }

    /**
     * Adds a callback with a boolean supplier
     * @param condition Schedules the command the first time this is true
     * @param command Command to schedule
     * @return Itself for chaining purposes
     */
    @Override
    public CallbackCommand when(BooleanSupplier condition, Command command) {
        whenCommands.put(condition, command);
        return this;
    }

    /**
     * Adds a callback with access to the inner command
     * @param condition Runs the runnable the first time this is true
     * @param runnable Callback to run
     * @return Itself for chaining purposes
     */
    @Override
    public CallbackCommand whenSelf(Function<Command, Boolean> condition, Runnable runnable) {
        whenSelfRunnables.put(condition, runnable);
        return this;
    }

    /**
     * Adds a callback with access to the inner command
     * @param condition Schedules the command the first time this is true
     * @param command Command to schedule
     * @return Itself for chaining purposes
     */
    @Override
    public CallbackCommand whenSelf(Function<Command, Boolean> condition, Command command) {
        whenSelfCommands.put(condition, command);
        return this;
    }

    @Override
    public void initialize() {
        command.schedule();
    }

    @Override
    public void execute() {
        for (Iterator<Map.Entry<BooleanSupplier, Runnable>> it = whenRunnables.entrySet().iterator(); it.hasNext();) {
            Map.Entry<BooleanSupplier, Runnable> action = it.next();
            if (action.getKey().getAsBoolean()) {
                action.getValue().run();
                it.remove();
            }
        }
        for (Iterator<Map.Entry<BooleanSupplier, Command>> it = whenCommands.entrySet().iterator(); it.hasNext();) {
            Map.Entry<BooleanSupplier, Command> action = it.next();
            if (action.getKey().getAsBoolean()) {
                action.getValue().schedule();
                it.remove();
            }
        }

        for (Iterator<Map.Entry<Function<Command, Boolean>, Runnable>> it = whenSelfRunnables.entrySet().iterator(); it.hasNext();) {
            Map.Entry<Function<Command, Boolean>, Runnable> action = it.next();
            if (action.getKey().apply(command)) {
                action.getValue().run();
                it.remove();
            }
        }
        for (Iterator<Map.Entry<Function<Command, Boolean>, Command>> it = whenSelfCommands.entrySet().iterator(); it.hasNext();) {
            Map.Entry<Function<Command, Boolean>, Command> action = it.next();
            if (action.getKey().apply(command)) {
                action.getValue().schedule();
                it.remove();
            }
        }
    }

    @Override
    public boolean isFinished() {
        return !CommandScheduler.getInstance().isScheduled(command);
    }
}
