package com.example.killaura;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import java.util.Random;

public class SlothAiMode {
    private static final Random RANDOM = new Random();
    public static Angle limitAngleChange(Angle currentAngle, Angle targetAngle, Vec3d vec3d, Entity entity) {
        Angle angleDelta = AngleUtil.calculateDelta(currentAngle, targetAngle);
        float yawDelta = angleDelta.getYaw();
        float pitchDelta = angleDelta.getPitch();
        float rotationDifference = (float) Math.hypot(Math.abs(yawDelta), Math.abs(pitchDelta));
        boolean attack = KillAura.isEnabled() && KillAura.getTarget() != null && MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.getAttackCooldownProgress(0) >= 0.9f;
        boolean rayVisible = RaytracingUtil.rayTrace(entity, KillAura.getRange());
        float yaw = attack ? 0 : (KillAura.getTarget() != null) ? (float) (randomLerp(1, 40) * Math.sin(System.currentTimeMillis() / 60D)) : 0;
        float pitch = attack ? 0 : (KillAura.getTarget() != null) ? (float) (randomLerp(30, 180) * Math.cos(System.currentTimeMillis() / 40D)) : 0;
        float speed = attack ? 1.0f : (KillAura.isTargetValid() && KillAura.canAttack(1) ? 0.5f : 0.3f);
        if (attack && !rayVisible) speed = 1.0f;
        float lineYaw = (Math.abs(yawDelta / rotationDifference) * 180);
        float linePitch = (Math.abs(pitchDelta) * 180);
        float moveYaw = MathHelper.clamp(yawDelta, -lineYaw, lineYaw);
        float movePitch = MathHelper.clamp(pitchDelta, -linePitch, linePitch);
        Angle moveAngle = new Angle(currentAngle.getYaw(), currentAngle.getPitch());
        float lerpFactor = MathHelper.clamp(randomLerp(speed, speed + 0.2F), 0f, 1f);
        moveAngle.setYaw(MathHelper.lerp(lerpFactor, currentAngle.getYaw(), currentAngle.getYaw() + moveYaw) + yaw);
        moveAngle.setPitch(MathHelper.lerp(lerpFactor, currentAngle.getPitch(), currentAngle.getPitch() + movePitch) + pitch);
        return moveAngle;
    }
    private static float randomLerp(float min, float max) {
        return MathHelper.lerp(RANDOM.nextFloat(), min, max);
    }
    public static Vec3d randomValue() { return Vec3d.ZERO; }
}
