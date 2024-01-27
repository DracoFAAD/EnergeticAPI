package me.dracofaad.energeticapi.ExampleItems;

import me.dracofaad.energeticapi.Classes.Energy.EnergeticItem;
import me.dracofaad.energeticapi.Classes.Interfaces.IItemContainer;
import me.dracofaad.energeticapi.EnergeticAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ExampleEnergyContainerItem extends EnergeticItem implements InventoryHolder {
    public ExampleEnergyContainerItem(ItemStack itemStack) throws RuntimeException {
        super(itemStack);
    }

    public ExampleEnergyContainerItem(float energy) {
        super(energy, 2000);
    }

    @Override
    public void rightClick(PlayerInteractEvent event) {
        subtractEnergy(10);

        event.getPlayer().openInventory(getInventory());
    }

    @Override
    public void leftClick(PlayerInteractEvent event) {
        addEnergy(10);
    }

    @Override
    public ItemStack createItem() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Example Container Item");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public JavaPlugin getPlugin() {
        return EnergeticAPI.getEnergeticAPI();
    }

    @Override
    public void energyUpdated() {
        Bukkit.broadcastMessage("Updated 2! " + Energy);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return Bukkit.createInventory(this, 3*9, "Example Container Item");
    }
}
