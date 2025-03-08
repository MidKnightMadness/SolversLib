package org.firstinspires.ftc.teamcode.PedroCommandSample;

import java.util.ArrayList;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Path;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;
import com.seattlesolvers.solverslib.pedroCommand.HoldPointCommand;

@Autonomous
public class HoldPointSample extends CommandOpMode {
    Follower follower = new Follower(hardwareMap);

    Pose pose = new Pose(
            72, 72, 90
    );

    @Override
    public void initialize() {
        super.reset();

        schedule(
                // Updates follower to follow path
                new RunCommand(() -> follower.update()),

                new HoldPointCommand(follower, new Pose(0, 4, 0), false),
                new HoldPointCommand(follower, pose, true)
        );
    }

    @Override
    public void run() {
        super.run();
    }
}