package me.unidok.customchattextkeybind;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mod implements ClientModInitializer {
    public final static Logger LOGGER = LoggerFactory.getLogger("CustomChatTextKeybind");

    public final static KeyBinding KEY_BINDING = new KeyBinding(
            "key.customchattextkeybind.open_chat",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_BACKSLASH,
            "category.customchattextkeybind"
    );

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(KEY_BINDING);

        try {
            Config.load();
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }

        Command.register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KEY_BINDING.wasPressed() && client.player != null && client.currentScreen == null) {
                client.setScreen(new ChatScreen(Config.getChatText()));
            }
        });
    }
}
