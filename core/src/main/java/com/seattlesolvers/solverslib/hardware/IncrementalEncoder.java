package com.seattlesolvers.solverslib.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.util.MathUtils;

public class IncrementalEncoder extends EncoderBase {
    private final DcMotor encoder;
    private final double ticksPerRevolution;

    private int lastPosition;
    private double lastTimeStamp, veloEstimate, dpp, accel, lastVelo;

    /**
     * The constructor for incremental encoders
     * @param hwMap the hardwareMap
     * @param id the ID of the encoder as configured
     * @param ticksPerRevolution the number of encoder ticks per full revolution
     */
    public IncrementalEncoder(HardwareMap hwMap, String id, double ticksPerRevolution) {
        this(hwMap.get(DcMotor.class, id), ticksPerRevolution);
    }

    /**
     * The constructor for incremental encoders
     * @param encoder the DcMotor which encoder is bound to
     * @param ticksPerRevolution the number of encoder ticks per full revolution
     */
    public IncrementalEncoder(DcMotor encoder, double ticksPerRevolution) {
        this.encoder = encoder;
        this.ticksPerRevolution = ticksPerRevolution;
        dpp = 1;
        lastPosition = 0;
        veloEstimate = 0;
        lastTimeStamp = (double) System.nanoTime() / 1E9;
    }

    /**
     * @return the current position of the encoder
     */
    public int getPosition() {
        int currentPosition = encoder.getCurrentPosition();
        if (currentPosition != lastPosition) {
            double currentTime = (double) System.nanoTime() / 1E9;
            double dt = currentTime - lastTimeStamp;
            veloEstimate = (currentPosition - lastPosition) / dt;
            lastPosition = currentPosition;
            lastTimeStamp = currentTime;
        }
        return getDirectionMultiplier() * currentPosition - (int) offset;
    }

    /**
     * @return the distance per pulse
     */
    public double getDpp() {
        return dpp;
    }

    /**
     * @return the distance traveled by the encoder
     */
    public double getDistance() {
        return dpp * getPosition();
    }

    /**
     * @return the velocity of the encoder adjusted to account for the distance per pulse
     */
    public double getRate() {
        return dpp * ((DcMotorEx) encoder).getVelocity();
    }

    /**
     * Sets the distance per pulse of the encoder.
     *
     * @param distancePerPulse the desired distance per pulse (in units per tick)
     */
    public IncrementalEncoder setDistancePerPulse(double distancePerPulse) {
        dpp = distancePerPulse;
        return this;
    }

    /**
     * @return the number of revolutions turned by the encoder
     */
    public double getRevolutions() {
        return getPosition() / ticksPerRevolution;
    }

    /**
     * @return the raw velocity of the motor reported by the encoder
     */
    public double getRawVelocity() {
        double velo = ((DcMotorEx) encoder).getVelocity();
        if (velo != lastVelo) {
            double currentTime = (double) System.nanoTime() / 1E9;
            double dt = currentTime - lastTimeStamp;
            accel = (velo - lastVelo) / dt;
            lastVelo = velo;
            lastTimeStamp = currentTime;
        }
        return velo;
    }

    /**
     * @return the estimated acceleration of the motor in ticks per second squared
     */
    public double getAcceleration() {
        return accel;
    }

    private final static int CPS_STEP = 0x10000;

    /**
     * Corrects for velocity overflow
     *
     * @return the corrected velocity
     */
    public double getCorrectedVelocity() {
        double real = getRawVelocity();
        while (Math.abs(veloEstimate - real) > CPS_STEP / 2.0) {
            real += Math.signum(veloEstimate - real) * CPS_STEP;
        }
        return real;
    }

    @Override
    public IncrementalEncoder zero() {
        offset = getPosition();
        return this;
    }

    @Override
    public double getAngle() {
        return MathUtils.normalizeAngle(
                (getPosition() % ticksPerRevolution) * MathUtils.returnMaxForAngleUnit(angleUnit),
                true,
                angleUnit
        );
    }

    @Override
    public void disable() {
        // "take no action" (encoder.close() call in SDK)
    }

    @Override
    public String getDeviceType() {
        return "Incremental Encoder; " + encoder.getDeviceName();
    }
}