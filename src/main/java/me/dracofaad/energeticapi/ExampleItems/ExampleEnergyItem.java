package me.dracofaad.energeticapi.ExampleItems;

import me.dracofaad.energeticapi.Classes.Energy.EnergeticItem;
import me.dracofaad.energeticapi.Classes.Interfaces.IItemContainer;
import me.dracofaad.energeticapi.Classes.Interfaces.ITickable;
import me.dracofaad.energeticapi.EnergeticAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ExampleEnergyItem extends EnergeticItem {

    public ExampleEnergyItem(ItemStack itemStack) throws RuntimeException {
        super(itemStack);
    }

    public ExampleEnergyItem(float energy) {
        super(energy, 1000);
    }

    @Override
    public void rightClick(PlayerInteractEvent event) {
        subtractEnergy(10);
        Bukkit.broadcastMessage("Right Clicked!");
    }

    @Override
    public void leftClick(PlayerInteractEvent event) {
        Bukkit.broadcastMessage("Left Clicked!");
    }

    @Override
    public ItemStack createItem() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Example Energy Item");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public JavaPlugin getPlugin() {
        return EnergeticAPI.getEnergeticAPI();
    }

    @Override
    public void energyUpdated() {
        Bukkit.broadcastMessage("Updated! " + Energy);
    }
}
