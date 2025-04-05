package io.github.u10000je.oreGen.controller;

import io.github.u10000je.oreGen.OreGen;
import io.github.u10000je.oreGen.event.OreGenEvent;
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

public class FlowController implements Listener {

    @EventHandler
    public void onFlow(BlockFromToEvent e) {
        Block source = e.getBlock();
        Block create = e.getToBlock();
        if(source.getType() != Material.WATER) {
            return;
        }
        Set<Material> touchMaterials = getMaterials(OreGen.getSettings());
        Material touchMaterial = getTouchMaterialNearBy(create, touchMaterials);
        if(touchMaterial == null) {
            return;
        }
        ConfigurationSection touchMaterialConfig = getTouchMaterialConfig(touchMaterial);
        if(touchMaterialConfig == null) {
            return;
        }
        Material createdMaterial = getCreatedMaterial(touchMaterialConfig);
        OreGenEvent oreGenEvent = new OreGenEvent(createdMaterial, create.getLocation());
        Bukkit.getPluginManager().callEvent(oreGenEvent);
        if(oreGenEvent.isCancelled()) {
            return;
        }
        e.setCancelled(true);
        create.setType(createdMaterial);
    }

    @Nullable
    private static Material getMaterialByString(String val) {
        Material material = Material.getMaterial(val);
        if(material == null) {
            OreGen.getServerInstance().getLogger().info(val + " is not valid Material Type.");
        }
        return material;
    }

    private static Set<Material> getMaterials(ConfigurationSection config) {
        Set<String> strings = config.getKeys(false);
        HashSet<Material> materials = new HashSet<>();
        for(String block : strings) {
            Material material = getMaterialByString(block);
            if(material == null) {
                continue;
            }
            materials.add(material);
        }
        return materials;
    }

    @Nullable
    private static Material getTouchMaterialNearBy(Block target, Set<Material> touchMaterials) {
        final BlockFace[] blockFaces = {BlockFace.DOWN, BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH};
        for(BlockFace blockFace : blockFaces) {
            Block relative = target.getRelative(blockFace);
            Material material = relative.getType();
            if(touchMaterials.contains(material)) {
                return material;
            }
        }
        return null;
    }

    @Nullable
    private static ConfigurationSection getTouchMaterialConfig(Material touchMaterial) {
        ConfigurationSection touchMaterialConfig;
        String key = touchMaterial.name();
        touchMaterialConfig = OreGen.getSettings().getConfigurationSection(key);
        if(touchMaterialConfig == null) {
            OreGen.getServerInstance().getLogger().info("The key " + key + "does not exist in settings.yml.");
        }
        return touchMaterialConfig;
    }

    private static Material getCreatedMaterial(ConfigurationSection touchMaterialConfig) {
        int val = 0;
        int randNum = (int)(Math.random()*100_000);
        Set<Material> createdMaterials = getMaterials(touchMaterialConfig);
        for(Material createdMaterial : createdMaterials) {
            String key = createdMaterial.name();
            int pos = (int)(touchMaterialConfig.getDouble(key)*1000);
            if(val <= randNum && randNum < val+pos) {
                return createdMaterial;
            }
            else {
                val += pos;
            }
        }
        return Material.STONE;
    }

}
