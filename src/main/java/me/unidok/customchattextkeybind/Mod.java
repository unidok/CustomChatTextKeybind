package me.unidok.customchattextkeybind;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.ChatScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mod implements ClientModInitializer {
    public final static Logger LOGGER = LoggerFactory.getLogger("CustomChatTextKeybind");

    @Override
    public void onInitializeClient() {
        Config.initialize();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (Config.KEY_BINDING.wasPressed() && client.player != null && client.currentScreen == null) {
                client.setScreen(new ChatScreen(Config.getChatText()));
            }
        });
    }
}
