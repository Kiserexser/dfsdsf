package com.example.killaura;

public class Angle {
    private float yaw, pitch;
    public Angle(float yaw, float pitch) { this.yaw = yaw; this.pitch = pitch; }
    public float getYaw() { return yaw; }
    public float getPitch() { return pitch; }
    public void setYaw(float yaw) { this.yaw = yaw; }
    public void setPitch(float pitch) { this.pitch = pitch; }
}
