package ru.college.i923g.zhkh123mod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Zhkh123Mod implements ModInitializer {

    public static final String MOD_ID = "zhkh_123mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModBlocks.initialize();
        LOGGER.info("Проект загружен успешно");
    }
}