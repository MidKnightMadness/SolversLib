package com.seattlesolvers.solverslib.colors;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

public class Threshold {
    public static final int GLOBAL_MIN = 0;
    public static final int GLOBAL_MAX = 255;

    public final boolean inverted;
    public final double low;
    public final double high;

    public Threshold(double low, double high, boolean invert) {
        assert low >= GLOBAL_MIN && low <= GLOBAL_MAX:
                "Min threshold must be between " + GLOBAL_MIN + " and " + GLOBAL_MAX;
        assert high >= GLOBAL_MIN && high <= GLOBAL_MAX:
                "Max threshold must be between " + GLOBAL_MIN + " and " + GLOBAL_MAX;
        assert low <= high:
                "Min threshold must be less than or equal to high threshold";

        this.low = low;
        this.high = high;
        this.inverted = invert;
    }

    public Threshold(double low, double high) {
        this(low, high, false);
    }

    public static Threshold of(double low, double high, boolean invert) {
        return new Threshold(low, high, invert);
    }

    public static Threshold of(double low, double high) {
        return new Threshold(low, high);
    }

    public static Threshold any() {
        return new Threshold(GLOBAL_MIN, GLOBAL_MAX);
    }

    public static Threshold fromRatios(double lowRatio, double highRatio, boolean invert) {
        return new Threshold(
                lowRatio * (GLOBAL_MAX - GLOBAL_MIN) + GLOBAL_MIN,
                highRatio * (GLOBAL_MAX - GLOBAL_MIN) + GLOBAL_MIN,
                invert
        );
    }

    public static Threshold fromRatios(double lowRatio, double highRatio) {
        return fromRatios(lowRatio, highRatio, false);
    }

    public double lowRatio() {
        return (low - GLOBAL_MIN) / (GLOBAL_MAX - GLOBAL_MIN);
    }

    public double highRatio() {
        return (high - GLOBAL_MIN) / (GLOBAL_MAX - GLOBAL_MIN);
    }

    public double randomValue() {
        if (!inverted) return low + Math.random() * (high - low);

        double lowerLength = low - GLOBAL_MIN;
        double higherLength = GLOBAL_MAX - high;
        double r = Math.random() * (lowerLength + higherLength);
        if (r < lowerLength) {
            return GLOBAL_MIN + r;
        } else {
            return high + (r - lowerLength);
        }
    }

    public boolean contains(double value) {
        return (value >= low && value <= high) ^ inverted; // XOR trick
    }

    public double range() {
        return high - low;
    }

    private static void maskNativeImpl(
            int depth, double[] lows, double[] highs,
            Threshold[] thresholds, Mat temp, Mat src, Mat dst
    ) {
        Threshold threshold = thresholds[depth];
        if (!threshold.inverted) {
            lows[depth] = threshold.low;
            highs[depth] = threshold.high;
            if (depth >= thresholds.length - 1) {
                Core.inRange(src, new Scalar(lows), new Scalar(highs), temp);
                Core.max(dst, temp, dst);
            } else {
                maskNativeImpl(depth + 1, lows, highs, thresholds, temp, src, dst);
            }
            return;
        }

        lows[depth] = GLOBAL_MIN;
        highs[depth] = threshold.low;
        if (depth >= thresholds.length - 1) {
            Core.inRange(temp, new Scalar(lows), new Scalar(highs), temp);
            Core.max(dst, temp, dst);
        } else {
            maskNativeImpl(depth + 1, lows, highs, thresholds, temp, src, dst);
        }

        lows[depth] = threshold.high;
        highs[depth] = GLOBAL_MAX;
        if (depth >= thresholds.length - 1) {
            Core.inRange(src, new Scalar(lows), new Scalar(highs), temp);
            Core.max(dst, temp, dst);
        } else {
            maskNativeImpl(depth + 1, lows, highs, thresholds, temp, src, dst);
        }
    }

    public static void maskNative(Threshold[] thresholds, Mat src, Mat dst) {
        double[] firstPixel = src.get(0, 0);
        assert thresholds.length == firstPixel.length:
                "Thresholds length must be equal to src color space length of " +
                        firstPixel.length + " got: " + thresholds.length;

        Mat temp = new Mat();
        maskNativeImpl(
                0, new double[thresholds.length],
                new double[thresholds.length], thresholds, temp, src, dst
        );
        temp.release();
    }

    public static boolean isWithin(Threshold[] thresholds, double[] color) {
        assert thresholds.length == color.length:
                "Thresholds length must be equal to color space length of " +
                        color.length + " got: " + thresholds.length;

        boolean isWithin = true;
        for (int i = 0; i < color.length; i++) {
            if (!thresholds[i].contains(color[i])) {
                isWithin = false;
                break;
            }
        }
        return isWithin;
    }
}