package net.ramixin.dunchanting.mixins.grindstone;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.GrindstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.ramixin.dunchanting.menus.ModGrindstoneMenu;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GrindstoneBlock.class)
public class GrindstoneBlockMixin {

    @ModifyReturnValue(method = "getMenuProvider", at = @At("RETURN"))
    private MenuProvider replaceProvider(MenuProvider original, BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider(
                (syncId, inventory, player) ->
                        new ModGrindstoneMenu(syncId, inventory, ContainerLevelAccess.create(level, pos)),
                original.getDisplayName()
        );
    }

}
