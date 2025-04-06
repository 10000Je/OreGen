package io.github.u10000je.oreGen.controller;

import io.github.u10000je.oreGen.OreGen;
import io.github.u10000je.oreGen.data.ItemsAdderMaterial;
import io.github.u10000je.oreGen.event.OreGenEvent;
import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class ItemsAdderFlowController implements Listener {

    @EventHandler
    public void onFlow(BlockFromToEvent e) {
        Block source = e.getBlock();
        Block create = e.getToBlock();
        if(source.getType() != Material.WATER) {
            return;
        }
        Set<ItemsAdderMaterial> touchMaterials = getItemsAdderMaterials(OreGen.getSettings());
        ItemsAdderMaterial touchMaterial = getTouchMaterialNearBy(create, touchMaterials);
        if(touchMaterial == null) {
            return;
        }
        ConfigurationSection touchMaterialConfig = getTouchMaterialConfig(touchMaterial);
        if(touchMaterialConfig == null) {
            return;
        }
        ItemsAdderMaterial createdMaterial = getCreatedMaterial(touchMaterialConfig);
        OreGenEvent oreGenEvent = new OreGenEvent(createdMaterial, create.getLocation());
        Bukkit.getPluginManager().callEvent(oreGenEvent);
        if(oreGenEvent.isCancelled()) {
            return;
        }
        e.setCancelled(true);
        if(createdMaterial.isCustomBlock()) {
            createdMaterial.getCustomBlock().place(create.getLocation());
        }
        else {
            create.setType(createdMaterial.getMaterial());
        }
    }

    @Nullable
    private static Material getMaterialByString(String val) {
        Material material = Material.getMaterial(val);
        if(material == null) {
            OreGen.getServerInstance().getLogger().info(val + " is not valid Material Type.");
        }
        return material;
    }

    @Nullable
    private static CustomBlock getCustomBlockByString(String val) {
        CustomBlock customBlock = CustomBlock.getInstance(val);
        if(customBlock == null) {
            OreGen.getServerInstance().getLogger().info(val + " is not valid CustomBlock.");
        }
        return customBlock;
    }

    private static Set<ItemsAdderMaterial> getItemsAdderMaterials(ConfigurationSection config) {
        Set<String> strings = config.getKeys(false);
        HashSet<ItemsAdderMaterial> itemsAdderMaterials = new HashSet<>();
        for(String block : strings) {
            if(block.startsWith("CUSTOM_")) {
                CustomBlock customBlock = getCustomBlockByString(block.substring(7));
                if(customBlock == null) {
                    continue;
                }
                itemsAdderMaterials.add(new ItemsAdderMaterial(customBlock));
            }
            else {
                Material material = getMaterialByString(block);
                if(material == null) {
                    continue;
                }
                itemsAdderMaterials.add(new ItemsAdderMaterial(material));
            }
        }
        return itemsAdderMaterials;
    }

    @Nullable
    private static ItemsAdderMaterial getTouchMaterialNearBy(Block create, Set<ItemsAdderMaterial> touchMaterials) {
        final BlockFace[] blockFaces = {BlockFace.DOWN, BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH};
        for(BlockFace blockFace : blockFaces) {
            Block relative = create.getRelative(blockFace);
            CustomBlock customBlock = CustomBlock.byAlreadyPlaced(relative);
            ItemsAdderMaterial itemsAdderMaterial;
            if(customBlock == null) {
                itemsAdderMaterial = new ItemsAdderMaterial(relative.getType());
            }
            else {
                itemsAdderMaterial = new ItemsAdderMaterial(customBlock);
            }
            if(touchMaterials.contains(itemsAdderMaterial)) {
                return itemsAdderMaterial;
            }
        }
        return null;
    }

    @Nullable
    private static ConfigurationSection getTouchMaterialConfig(ItemsAdderMaterial touchMaterial) {
        ConfigurationSection touchMaterialConfig;
        String key;
        if(touchMaterial.isCustomBlock()) {
            key = "CUSTOM_" + touchMaterial.getCustomBlock().getId();
        }
        else {
            key = touchMaterial.getMaterial().name();
        }
        touchMaterialConfig = OreGen.getSettings().getConfigurationSection(key);
        if(touchMaterialConfig == null) {
            OreGen.getServerInstance().getLogger().info("The key " + key + "does not exist in settings.yml.");
        }
        return touchMaterialConfig;
    }

    private static ItemsAdderMaterial getCreatedMaterial(ConfigurationSection touchMaterialConfig) {
        int val = 0;
        int randNum = (int)(Math.random()*100_000);
        Set<ItemsAdderMaterial> createdMaterials = getItemsAdderMaterials(touchMaterialConfig);
        for(ItemsAdderMaterial createdMaterial : createdMaterials) {
            String key;
            if(createdMaterial.isCustomBlock()) {
                key = "CUSTOM_" + createdMaterial.getCustomBlock().getId();
            }
            else {
                key = createdMaterial.getMaterial().name();
            }
            int pos = (int)(touchMaterialConfig.getDouble(key)*1000);
            if(val <= randNum && randNum < val+pos) {
                return createdMaterial;
            }
            else {
                val += pos;
            }
        }
        return new ItemsAdderMaterial(Material.STONE);
    }

}
