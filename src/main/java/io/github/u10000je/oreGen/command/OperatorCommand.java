package io.github.u10000je.oreGen.command;

import io.github.u10000je.colorUtil.ColorUtil;
import io.github.u10000je.oreGen.OreGen;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OperatorCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, @NotNull String[] args) {
        if(!cmd.getName().equals("oregen")) {
            return false;
        }
        Audience audience = sender;
        if(args.length == 0) {
            audience.sendMessage(ColorUtil.translateColorCodes('&', "&a[OreGen] /oregen reload - reload config.yml, setting.yml"));
        }
        else if(args[0].equals("reload")) {
            OreGen.getServerInstance().reloadConfig();
            OreGen.unregisterEvents();
            OreGen.registerEvents();
            OreGen.loadSettings();
            audience.sendMessage(ColorUtil.translateColorCodes('&', "&a[OreGen] config.yml, setting.yml reloaded."));
        }
        else {
            audience.sendMessage(ColorUtil.translateColorCodes('&', "&c[OreGen] unknown command. use /oregen"));
        }
        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, @NotNull String[] args) {
        if(!cmd.getName().equals("oregen")) {
            return null;
        }
        if(args.length == 1) {
            return List.of("reload");
        }
        return null;
    }
}
