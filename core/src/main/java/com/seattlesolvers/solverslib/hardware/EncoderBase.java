package com.seattlesolvers.solverslib.hardware;

import com.seattlesolvers.solverslib.util.MathUtils;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public abstract class EncoderBase implements Encoder {
    protected boolean reversed = false;
    protected double angleOffset = 0.0;
    protected AngleUnit angleUnit;

    @Override
    public EncoderBase setReversed(boolean reversed) {
        this.reversed = reversed;
        return this;
    }

    @Override
    public boolean getReversed() {
        return reversed;
    }

    @Override
    public EncoderBase resetAngle(double offset) {
        this.angleOffset = offset;
        return this;
    }

    @Override
    public double getAngle() {
        return MathUtils.normalizeAngle(
                (reversed ? -1 : 1) * (getRawAngle() + angleOffset),
                true,
                angleUnit
        );
    }

    @Override
    public AngleUnit getAngleUnit() {
        return angleUnit;
    }

    /**
     * @return The raw calculated angle, before reversing or offsetting
     */
    public abstract double getRawAngle();
}