package com.seattlesolvers.solverslib.hardware;

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
     * @return Whether the encoder is reversed
     */
    boolean getReversed();

    /**
     * Sets an angular offset for any future values returned when reading the encoder
     * @param offset The angular offset in the units specified by the user previously
     * @return The object itself for chaining purposes
     */
    Encoder resetAngle(double offset);
}
