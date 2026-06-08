package com.example.killaura;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class KillAura {
    private static boolean enabled = false;
    private static double range = 4.5;
    private static int delayTicks = 2;
    private static int cooldown = 0;
    private static Entity target = null;
    private static final Random RANDOM = new Random();

    public static void toggle() {
        enabled = !enabled;
        if (!enabled) target = null;
    }
    public static boolean isEnabled() { return enabled; }
    public static Entity getTarget() { return target; }
    public static double getRange() { return range; }
    public static void update() {
        if (!enabled) return;
        if (cooldown > 0) { cooldown--; return; }
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.world == null) return;
        if (mc.player.isDead() || mc.player.getAttackCooldownProgress(0) < 0.9f) return;
        List<Entity> entities = mc.world.getEntitiesByClass(LivingEntity.class,
                Box.of(mc.player.getPos(), range * 2, range * 2, range * 2),
                e -> e != mc.player && e.isAlive() && !e.isRemoved());
        target = entities.stream().min(Comparator.comparingDouble(e -> e.squaredDistanceTo(mc.player))).orElse(null);
        if (target == null) return;
        if (target.squaredDistanceTo(mc.player) > range * range) return;
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
        cooldown = delayTicks;
    }
    public static boolean canAttack(int ticks) {
        if (!enabled || cooldown > 0 || target == null) return false;
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return false;
        return mc.player.getAttackCooldownProgress(0) >= 0.9f;
    }
    public static boolean isTargetValid() { return target != null && target.isAlive(); }
}
