package com.seattlesolvers.solverslib.pedroCommand.old;

import com.seattlesolvers.solverslib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.PathChain;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;


// Thanks Powercube from Watt-sUP 16166, we copied verbatim

/**
 * Now incorporated into one command, {@link FollowPathCommand )
 * <p>
 * @author Arush - FTC 23511
 * @author Saket - FTC 23511
 */
@Deprecated
public class FollowPathChainCommand extends CommandBase {

    private final Follower follower;
    private final PathChain path;
    private boolean holdEnd = true;

    public FollowPathChainCommand(Follower follower, PathChain path) {
        this.follower = follower;
        this.path = path;
    }

    public FollowPathChainCommand(Follower follower, PathChain path, boolean holdEnd) {
        this.follower = follower;
        this.path = path;
        this.holdEnd = holdEnd;
    }

    /**
     * Decides whether or not to make the robot maintain its position once the path ends.
     *
     * @param holdEnd If the robot should maintain its ending position
     * @return This command for compatibility in command groups
     */
    public FollowPathChainCommand setHoldEnd(boolean holdEnd) {
        this.holdEnd = holdEnd;
        return this;
    }

    @Override
    public void initialize() {
        follower.followPath(path, holdEnd);
    }

    @Override
    public boolean isFinished() {
        return !follower.isBusy();
    }
}