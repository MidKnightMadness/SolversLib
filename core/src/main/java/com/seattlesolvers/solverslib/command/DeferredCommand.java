package com.seattlesolvers.solverslib.command;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.Subsystem;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Defers Command construction to runtime. Runs the command returned by a supplier when this command
 * is initialized, and ends when it ends. Useful for performing runtime tasks before creating a new
 * command, like building a new trajectory in the middle of auto. If this command is interrupted, it
 * will cancel the command.
 *
 * @author Suchir Ryali - X BOTS 19448
 */

public class DeferredCommand implements Command {
    private final Supplier<Command> supplier;
    private final Set<Subsystem> requirements;
    @Nullable
    private Command command;
    
    public DeferredCommand(@NonNull Supplier<Command> supplier, @Nullable Set<Subsystem> requirements) {
        this.supplier = Objects.requireNonNull(supplier);
        this.requirements = requirements != null ? requirements : Collections.emptySet();
    }

    @Override
    public void initialize() {
        Command cmd = supplier.get();
        if (cmd != null) {
            command = cmd;
        } else {
            throw new NullPointerException("DeferredCommand: Supplied command was null!");
        }
        command.initialize();
    }

    @Override
    public void execute() {
        if (command != null)
            command.execute();
    }

    @Override
    public boolean isFinished() {
        return command == null || command.isFinished();
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return requirements;
    }

    @Override
    public void end(boolean interrupted) {
        if (command != null)
            command.end(interrupted);
        command = null;
    }
