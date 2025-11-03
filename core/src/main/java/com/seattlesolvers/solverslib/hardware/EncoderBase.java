package com.seattlesolvers.solverslib.hardware;

import com.seattlesolvers.solverslib.util.RotationDirection;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public abstract class EncoderBase<E, T extends EncoderBase<E, T>> implements Encoder<E, T> {
    protected double offset = 0.0;
    protected RotationDirection direction = RotationDirection.FORWARD;
    protected AngleUnit angleUnit = AngleUnit.RADIANS;
    protected final E encoder;

    protected EncoderBase(E encoder) {
        this.encoder = encoder;
    }

    @Override
    public int getDirectionMultiplier() {
        return direction.getMultiplier();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setDirection(RotationDirection direction) {
        this.direction = direction;
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setReversed(boolean reversed) {
        direction = reversed ? RotationDirection.REVERSE : RotationDirection.FORWARD;
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T zero() {
        this.setAngle(0);
        return (T) this;
    }

    @Override
    public RotationDirection getDirection() {
        return direction;
    }

    @Override
    public boolean getReversed() {
        return direction == RotationDirection.REVERSE;
    }

    @Override
    public AngleUnit getAngleUnit() {
        return angleUnit;
    }

    @Override
    public void resetOffset() {
        this.offset = 0;
    }

    @Override
    public E getEncoder() {
        return encoder;
    }
}