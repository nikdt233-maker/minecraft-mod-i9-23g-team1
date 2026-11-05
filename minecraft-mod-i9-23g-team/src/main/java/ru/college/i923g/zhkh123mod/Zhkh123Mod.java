package ru.college.i923g.zhkh123mod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Точка входа мода. Fabric вызывает onInitialize() при загрузке игры.
 */
public class Zhkh123Mod implements ModInitializer {

    // Идентификатор мода (совпадает с "id" в fabric.mod.json)
    public static final String MOD_ID = "zhkh_123mod";

    // Регистратор для сообщений в журнале запуска
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // Регистрация блока "Батарея" и его предмета (ЛР №7)
        ModBlocks.initialize();

        // Добавление предмета во вкладку творческого инвентаря (ЛР №9)
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS)
                .register(entries -> entries.accept(ModBlocks.BATTERY_ITEM));

        // Дополнительное поведение блока (ЛР №10)
        registerBatteryUseBehavior();

        // Диагностическое сообщение из ЛР №6
        LOGGER.info("Проект загружен успешно");
    }

    /**
     * Поведение: игрок кликает правой кнопкой по батарее пустой рукой -
     * в чат приходит сообщение "Батарея тёплая.".
     * Обработчик вызывается при каждом использовании ЛЮБОГО блока,
     * поэтому первым делом проверяем, что это именно наша батарея.
     */
    private static void registerBatteryUseBehavior() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            // Узнаём, по какому блоку кликнул игрок
            BlockState state = world.getBlockState(hitResult.getBlockPos());

            // Это не наша батарея - пропускаем событие дальше (обычное поведение игры)
            if (!state.is(ModBlocks.BATTERY)) {
                return InteractionResult.PASS;
            }

            // Эффект только для основной руки и только если рука пуста:
            // так игрок с блоком в руке по-прежнему сможет строить рядом с батареей
            if (hand != InteractionHand.MAIN_HAND || !player.getItemInHand(hand).isEmpty()) {
                return InteractionResult.PASS;
            }

            // Сообщение отправляем только на стороне сервера (!isClientSide),
            // иначе событие сработает на клиенте и на сервере и сообщение придёт дважды
            if (!world.isClientSide()) {
                // false - показать в чате (true показало бы над панелью предметов)
                player.displayClientMessage(Component.literal("Батарея тёплая."), false);
            }

            // Действие выполнено: игра не будет делать ничего другого с этим кликом
            return InteractionResult.SUCCESS;
        });
    }
}
