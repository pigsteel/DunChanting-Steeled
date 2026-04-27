package net.ramixin.dunchanting.mixins.attribution;

import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.ramixin.dunchanting.AttributionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(PrimaryLevelData.class)
public abstract class PrimaryLevelDataMixin {

    @SuppressWarnings("deprecation")
    @Inject(method = "parse", at = @At("TAIL"))
    private static <T> void loadAttributionData(Dynamic<T> input, LevelSettings settings, PrimaryLevelData.SpecialWorldProperty specialWorldProperty, Lifecycle worldGenSettingsLifecycle, CallbackInfoReturnable<PrimaryLevelData> cir) {
        CompoundTag root = input.get("DungeonEnchants").flatMap(CompoundTag.CODEC::parse).result().orElse(new CompoundTag());
        AttributionManager.load(root);
    }

    @Inject(method = "setTagData", at = @At("TAIL"))
    private void saveAttributionData(CompoundTag tag, UUID singlePlayerUUID, CallbackInfo ci) {
        CompoundTag root = new CompoundTag();
        AttributionManager.save(root);
        tag.put("DungeonEnchants", root);
    }

}
