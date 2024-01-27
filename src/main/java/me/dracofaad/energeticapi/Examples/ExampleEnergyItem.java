package me.dracofaad.energeticapi.Examples;

import me.dracofaad.energeticapi.Classes.Energy.EnergeticItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ExampleEnergyItem extends EnergeticItem {
    public ExampleEnergyItem(ItemStack itemStack) throws RuntimeException {
        super(itemStack);
    }

    public ExampleEnergyItem(float energy) {
        super(energy, 1000);
    }

    @Override
    public void onRightClick(PlayerInteractEvent event) {
        subtractEnergy(10);
        Bukkit.broadcastMessage("Right Clicked!");
    }

    @Override
    public void onLeftClick(PlayerInteractEvent event) {
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
    public void onEnergyUpdated() {
        Bukkit.broadcastMessage("Updated! " + Energy);
    }
}