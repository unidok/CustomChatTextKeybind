package me.unidok.customchattextkeybind;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Config {
    public final static KeyBinding KEY_BINDING = new KeyBinding(
            "key.customchattextkeybind.open_chat",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_BACKSLASH,
            "category.customchattextkeybind"
    );

    private static String text;

    public static String getChatText() {
        return text;
    }

    public static void initialize() {
        KeyBindingHelper.registerKeyBinding(KEY_BINDING);

        final var file = new File(MinecraftClient.getInstance().runDirectory, "config/CustomChatTextKeybind.txt");

        try {
            if (file.createNewFile()) {
                Files.writeString(file.toPath(), "\\");
            }

            text = Files.readString(file.toPath());

            if (text.length() > 256) {
                text = text.substring(0, 256);
                Mod.LOGGER.error("The text length cannot be more than 256.");
            }
        } catch (IOException e) {
            Mod.LOGGER.error(e.toString());
        }
    }
}
