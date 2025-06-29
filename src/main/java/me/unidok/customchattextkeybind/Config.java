package me.unidok.customchattextkeybind;

import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Config {
    private static final File file = new File(MinecraftClient.getInstance().runDirectory, "config/CustomChatTextKeybind.txt");
    private static String text;

    public static void setChatText(String text) {
        Config.text = text;

        try {
            Files.writeString(file.toPath(), text);
        } catch (IOException e) {
            Mod.LOGGER.error(e.toString());
        }
    }

    public static String getChatText() {
        return text;
    }

    public static void load() throws IOException, IllegalStateException {
        if (file.createNewFile()) {
            setChatText("\\");
            return;
        }

        String text = Files.readString(file.toPath());

        if (text.length() > 256) {
            Config.text = "\\";
            throw new IllegalStateException("The text length cannot be more than 256.");
        }

        Config.text = text;
    }
}
