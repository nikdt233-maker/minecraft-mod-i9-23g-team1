package ru.college.i923g.zhkh123mod;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Класс регистрации блоков мода.
 * Здесь блок "Батарея" и связанный с ним предмет добавляются в реестры игры.
 */
public class ModBlocks {

    // Блок "Батарея". Полный идентификатор: zhkh_123mod:battery
    public static final Block BATTERY = Registry.register(
            BuiltInRegistries.BLOCK, // реестр блоков
            id("battery"),           // идентификатор блока
            new Block(BlockBehaviour.Properties
                    .ofFullCopy(Blocks.IRON_BLOCK) // берём настройки железного блока
                    .strength(3.0f, 6.0f)          // прочность 3.0, взрывоустойчивость 6.0
                    .lightLevel(state -> 8)));     // уровень света 8 (в любом состоянии)

    // Предмет-блок: без него блок нельзя держать в руке, получить через /give и поставить.
    // Идентификатор такой же, как у блока.
    public static final Item BATTERY_ITEM = Registry.register(
            BuiltInRegistries.ITEM, // реестр предметов
            id("battery"),
            new BlockItem(BATTERY, new Item.Properties()));

    // Вспомогательный метод: собирает идентификатор "zhkh_123mod:имя"
    private static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(Zhkh123Mod.MOD_ID, name);
    }

    // Пустой метод. Его вызов в точке входа загружает класс,
    // а при загрузке класса выполняются Registry.register выше.
    public static void initialize() {
    }
}