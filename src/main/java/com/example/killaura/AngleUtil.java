package com.example.killaura;

public class AngleUtil {
    public static Angle calculateDelta(Angle a1, Angle a2) {
        float yaw = a2.getYaw() - a1.getYaw();
        float pitch = a2.getPitch() - a1.getPitch();
        if (yaw > 180) yaw -= 360;
        if (yaw < -180) yaw += 360;
        return new Angle(yaw, pitch);
    }
}
