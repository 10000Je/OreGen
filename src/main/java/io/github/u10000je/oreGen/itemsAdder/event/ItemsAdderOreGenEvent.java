package io.github.u10000je.oreGen.itemsAdder.event;

import io.github.u10000je.oreGen.itemsAdder.data.ItemsAdderMaterial;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ItemsAdderOreGenEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final ItemsAdderMaterial createdMaterial;
    private final Location createdLocation;

    public ItemsAdderOreGenEvent(ItemsAdderMaterial createdMaterial, Location createdLocation) {
        this.cancelled = false;
        this.createdMaterial = createdMaterial;
        this.createdLocation = createdLocation;
    }

    public ItemsAdderMaterial getCreatedMaterial() {
        return this.createdMaterial;
    }

    public Location getCreatedLocation() {
        return this.createdLocation;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
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
