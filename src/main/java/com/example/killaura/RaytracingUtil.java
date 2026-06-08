package com.example.killaura;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;

public class RaytracingUtil {
    public static boolean rayTrace(Entity entity, double range) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return false;
        Vec3d start = mc.player.getEyePos();
        Vec3d end = entity.getPos().add(0, entity.getHeight() / 2, 0);
        var ray = new net.minecraft.util.math.Ray(start, end.subtract(start).normalize());
        HitResult hit = mc.world.raycast(ray, range);
        return hit != null && hit.getType() == HitResult.Type.MISS;
    }
}
