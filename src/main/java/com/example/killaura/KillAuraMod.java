package com.example.killaura;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KillAuraMod {
    public static final Logger LOGGER = LoggerFactory.getLogger("killaura");
    public static void init() {
        LOGGER.info("KillAura + SlothAI initialized");
    }
}
