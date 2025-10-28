package com.seattlesolvers.solverslib.hardware;

import com.seattlesolvers.solverslib.util.RotationDirection;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public interface Encoder extends HardwareDevice {
    /**
     * @return The normalized angular position of the encoder
     */
    double getAngle();

    /**
     * @return The angle unit associated with the encoder
     */
    AngleUnit getAngleUnit();

    /**
     * Sets whether or not the encoder should be reversed for any future values returned when reading the encoder
     * @param reversed Whether or not the encoder should be reversed for any future values
     * @return The object itself for chaining purposes
     */
    Encoder setReversed(boolean reversed);

    /**
     * Sets whether or not the encoder should be reversed for any future values returned when reading the encoder
     * @param direction The direction of the encoder should be reversed for any future values
     * @return The object itself for chaining purposes
     */
    Encoder setDirection(RotationDirection direction);

    /**
     * @return Whether the encoder is reversed
     */
    boolean getReversed();

    /**
     * Resets the encoder for any future values returned
     * @return The object itself for chaining purposes
     */
    Encoder zero();

    /**
     * Resets the offset for any future values returned
     */
    void resetOffset();
}
