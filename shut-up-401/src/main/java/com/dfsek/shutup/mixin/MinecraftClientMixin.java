package com.dfsek.shutup.mixin;

import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Redirect(method = "createUserApiService(Lcom/mojang/authlib/yggdrasil/YggdrasilAuthenticationService;Lnet/minecraft/client/RunArgs;)Lcom/mojang/authlib/minecraft/UserApiService;", at =@At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Throwable;)V"))
    private void redirect(Logger instance, String s, Throwable throwable) {
        instance.info("im make krabber patterys");
        instance.error(s); // dont log the exception.
        instance.info("shut up.");
    }
}
