package com.seattlesolvers.solverslib.hardware;

import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.util.MathUtils;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public abstract class EncoderBase implements Encoder {
    protected double offset = 0.0;
    protected boolean reversed = false;
    protected AngleUnit angleUnit;

    /**
     * @return 1 for normal, -1 for reversed
     */
    public int getDirectionMultiplier() {
        return reversed ? -1 : 1;
    }

    @Override
    public EncoderBase setReversed(boolean reversed) {
        this.reversed = reversed;
        return this;
    }

    @Override
    public EncoderBase setDirection(Motor.Direction direction) {
        return setReversed(direction == Motor.Direction.REVERSE);
    }

    @Override
    public boolean getReversed() {
        return reversed;
    }

    @Override
    public AngleUnit getAngleUnit() {
        return angleUnit;
    }

    @Override
    public EncoderBase resetOffset() {
        this.offset = 0;
        return this;
    }
}