package io.github.u10000je.oreGen.event;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class OreGenEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final Material createdMaterial;
    private final Location createdLocation;

    public OreGenEvent(Material createdMaterial, Location createdLocation) {
        this.createdMaterial = createdMaterial;
        this.createdLocation = createdLocation;
        this.cancelled = false;
    }

    public Material getCreatedMaterial() {
        return this.createdMaterial;
    }

    public Location getCreatedLocation() {
        return this.createdLocation;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlers;
    }

}
