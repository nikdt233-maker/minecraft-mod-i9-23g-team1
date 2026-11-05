package ru.college.i923g.zhkh123mod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Zhkh123Mod implements ModInitializer {

    public static final String MOD_ID = "zhkh_123mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        ModBlocks.initialize();
        registerBatteryUseBehavior();
        BatteryHealing.initialize();
        LOGGER.info("Проект загружен успешно");
    }

    private static void registerBatteryUseBehavior() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockState state = world.getBlockState(hitResult.getBlockPos());
            if (!state.is(ModBlocks.BATTERY)) {
                return InteractionResult.PASS;
            }
            if (hand != InteractionHand.MAIN_HAND || !player.getItemInHand(hand).isEmpty()) {
                return InteractionResult.PASS;
            }
            if (!world.isClientSide()) {
                player.displayClientMessage(Component.literal("Батарея тёплая."), false);
            }
            return InteractionResult.SUCCESS;
        });
    }
}
