package com.example.killaura.mixin;

import com.example.killaura.KillAura;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    private static KeyBinding key = null;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (key == null) {
            key = new KeyBinding("key.killaura.toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "category.killaura");
        }
        if (key.wasPressed()) KillAura.toggle();
        KillAura.update();
    }
}
