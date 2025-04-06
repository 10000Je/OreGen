package io.github.u10000je.oreGen;

import io.github.u10000je.oreGen.command.OperatorCommand;
import io.github.u10000je.oreGen.controller.ItemsAdderFlowController;
import io.github.u10000je.oreGen.controller.FlowController;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class OreGen extends JavaPlugin {

    private static OreGen serverInstance;
    private static YamlConfiguration settings;
    private static Listener controller;

    @Override
    public void onEnable() {
        serverInstance = this;
        saveDefaultConfig();
        loadSettings();
        setCommandExecutors();
        registerEvents();
    }

    @Override
    public void onDisable() {
        saveConfig();
        saveSettings();
        serverInstance = null;
        settings = null;
        controller = null;
    }

    public static OreGen getServerInstance() {
        return serverInstance;
    }

    public static YamlConfiguration getSettings() {
        return settings;
    }

    public static Listener getController() {
        return controller;
    }

    public static void saveSettings() {
        File file = new File(serverInstance.getDataFolder() + "/settings.yml");
        try {
            settings.save(file);
        }
        catch (IOException e) {
            serverInstance.getLogger().info("cannot save settings.yml file.");
        }
    }

    public static void loadSettings() {
        File file = new File(serverInstance.getDataFolder() + "/settings.yml");
        if(file.exists()) {
            settings = YamlConfiguration.loadConfiguration(file);
            return;
        }
        InputStream inputStream = serverInstance.getResource("settings.yml");
        if(inputStream == null) {
            serverInstance.getLogger().info("settings.yml does not exist in jar file.");
            return;
        }
        try {
            Files.copy(inputStream, file.toPath());
            settings = YamlConfiguration.loadConfiguration(file);
        }
        catch (IOException e) {
            serverInstance.getLogger().info("cannot create settings.yml file.");
        }
    }

    public static void registerEvents() {
        if(serverInstance.getConfig().getBoolean("USE_ITEMS_ADDER")) {
            if(!serverInstance.getServer().getPluginManager().isPluginEnabled("ItemsAdder")) {
                serverInstance.getLogger().info("ItemsAdder plugin is disabled. you can't use USE_ITEMS_ADDER option");
                controller = new FlowController();
            }
            else {
                controller = new ItemsAdderFlowController();
            }
        }
        else {
            controller = new FlowController();
        }
        serverInstance.getServer().getPluginManager().registerEvents(controller, serverInstance);
    }

    public static void unregisterEvents() {
        HandlerList.unregisterAll(controller);
        controller = null;
    }


    private static void setCommandExecutors() {
        PluginCommand oregen = serverInstance.getCommand("oregen");
        if(oregen == null) {
            serverInstance.getLogger().info("command oregen is not defined.");
            return;
        }
        oregen.setExecutor(new OperatorCommand());
        oregen.setTabCompleter(new OperatorCommand());
    }


}
