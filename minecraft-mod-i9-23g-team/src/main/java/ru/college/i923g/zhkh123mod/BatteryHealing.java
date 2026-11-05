package ru.college.i923g.zhkh123mod;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

/**

 * +0,5 единицы здоровья в минуту, если батарея в радиусе 5 блоков.
 */
public class BatteryHealing {

    // Радиус действия батареи в блоках
    public static final int RADIUS = 5;
    // Как часто лечим: 1200 тиков = 60 секунд (в игре 20 тиков в секунду)
    public static final int INTERVAL_TICKS = 1200;
    // Сколько здоровья восстанавливаем за раз: 1 единицу = половина сердца (целое сердце = 2 единицы)
    public static final float HEAL_AMOUNT = 1f;

    // Подключаем обработчик конца каждого тика сервера
    public static void initialize() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            // Работаем только раз в INTERVAL_TICKS тиков, чтобы не нагружать игру
            if (server.getTickCount() % INTERVAL_TICKS != 0) {
                return;
            }
            // Перебираем всех игроков на сервере
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                // Наблюдателей и мёртвых игроков не лечим
                if (player.isSpectator() || !player.isAlive()) {
                    continue;
                }
                // Лечим, если здоровье неполное и рядом есть батарея
                if (player.getHealth() < player.getMaxHealth() && isBatteryNearby(player)) {
                    player.heal(HEAL_AMOUNT);
                }
            }
        });
    }

    // Проверяет, есть ли блок "Батарея" в радиусе RADIUS блоков от игрока
    private static boolean isBatteryNearby(ServerPlayer player) {
        Level level = player.level();
        BlockPos center = player.blockPosition();
        // Перебираем все блоки в кубе вокруг игрока
        for (BlockPos pos : BlockPos.betweenClosed(
                center.offset(-RADIUS, -RADIUS, -RADIUS),
                center.offset(RADIUS, RADIUS, RADIUS))) {
            // Оставляем только блоки внутри шара радиуса RADIUS и ищем среди них батарею
            if (pos.distSqr(center) <= RADIUS * RADIUS
                    && level.getBlockState(pos).is(ModBlocks.BATTERY)) {
                return true;
            }
        }
        return false;
    }
}
