package com.seattlesolvers.solverslib.hardware;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.util.MathUtils;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * An extended wrapper class for AnalogInput absolute encoders.
 *
 * @author Saket
 */
public class AbsoluteAnalogEncoder extends EncoderBase<AnalogInput, AbsoluteAnalogEncoder> {
    public static double DEFAULT_RANGE = 3.3;
    private final String id;
    private final double range;

    /**
     * The constructor for absolute analog encoders
     * @param hwMap the hardwareMap
     * @param id the ID of the encoder as configured
     * @param range the range of voltage returned by the sensor
     */
    public AbsoluteAnalogEncoder(HardwareMap hwMap, String id, double range, AngleUnit angleUnit) {
        super(hwMap.get(AnalogInput.class, id));
        this.angleUnit = angleUnit;
        this.range = range;
        this.id = id;
    }

    /**
     * The constructor for absolute analog encoders, with default values of 3.3v and radians for the range and angle unit respectively
     * @param hwMap the hardwareMap
     * @param id the ID of the encoder as configured
     */
    public AbsoluteAnalogEncoder(HardwareMap hwMap, String id) {
        this(hwMap, id, DEFAULT_RANGE, AngleUnit.RADIANS);
    }

    /**
     * @return The raw voltage returned by the encoder
     */
    public double getVoltage() {
        return encoder.getVoltage();
    }

    public double getRawAngle() {
        return getVoltage() / range * MathUtils.returnMaxForAngleUnit(angleUnit);
    }

    @Override
    public AbsoluteAnalogEncoder setAngle(double angle) {
        offset += getAngle() - angle;
        return null;
    }

    @Override
    public double getAngle() {
        return MathUtils.normalizeAngle(
                getDirectionMultiplier() * (getRawAngle() + offset),
                true,
                angleUnit
        );
    }

    @Override
    public String getDeviceType() {
        return "Absolute Analog Encoder; " + id;
    }
}
