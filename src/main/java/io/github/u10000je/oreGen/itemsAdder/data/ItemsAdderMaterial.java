package io.github.u10000je.oreGen.itemsAdder.data;

import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Material;

import java.util.Objects;

public class ItemsAdderMaterial {

    private final boolean hasCustomBlock;
    private Material material;
    private CustomBlock customBlock;

    public ItemsAdderMaterial(CustomBlock customBlock) {
        this.hasCustomBlock = true;
        this.customBlock = customBlock;
    }

    public ItemsAdderMaterial(Material material) {
        this.hasCustomBlock = false;
        this.material = material;
    }

    public boolean isCustomBlock() {
        return this.hasCustomBlock;
    }

    public CustomBlock getCustomBlock() {
        return this.customBlock;
    }

    public Material getMaterial() {
        return this.material;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof ItemsAdderMaterial)) {
            return false;
        }
        ItemsAdderMaterial itemsAdderMaterial = (ItemsAdderMaterial)object;
        if(itemsAdderMaterial.isCustomBlock()) {
            return Objects.equals(this.customBlock, itemsAdderMaterial.getCustomBlock());
        }
        else {
            return Objects.equals(this.material, itemsAdderMaterial.getMaterial());
        }
    }

    @Override
    public int hashCode() {
        if(this.hasCustomBlock) {
            return this.customBlock.hashCode();
        }
        else {
            return this.material.hashCode();
        }
    }

}
