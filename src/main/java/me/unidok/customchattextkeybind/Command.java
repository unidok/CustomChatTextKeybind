package me.unidok.customchattextkeybind;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class Command {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("customchattextkeybind")
                .then(
                        ClientCommandManager.literal("reloadConfig")
                                .executes((context) -> {
                                    try {
                                        Config.load();
                                    } catch (Exception e) {
                                        context.getSource().sendFeedback(
                                                Text.translatable(
                                                        "commands.customchattextkeybind.reload_config.failed",
                                                        Text.literal(e.toString()).formatted(Formatting.WHITE)
                                                ).formatted(Formatting.RED)
                                        );
                                        return 1;
                                    }
                                    context.getSource().sendFeedback(Text.translatable("commands.customchattextkeybind.reload_config.success"));
                                    return 1;
                                })
                )
                .then(
                        ClientCommandManager.literal("setText")
                                .then(
                                        ClientCommandManager.argument("text", StringArgumentType.greedyString())
                                                .executes((context) -> {
                                                    String text = StringArgumentType.getString(context, "text");
                                                    Config.setChatText(text);
                                                    context.getSource().sendFeedback(
                                                            Text.translatable(
                                                                    "commands.customchattextkeybind.set_text.success",
                                                                    Text.literal(text).formatted(Formatting.GRAY)
                                                            )
                                                    );
                                                    return 1;
                                                })
                                )
                )
        ));
    }
}
