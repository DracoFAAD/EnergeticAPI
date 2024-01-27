package me.dracofaad.energeticapi.ExampleItems;

import me.dracofaad.energeticapi.Classes.Energy.EnergeticBlock;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ExampleEnergyBlock extends EnergeticBlock {
    public ExampleEnergyBlock(BlockData blockData) {
        super(blockData);
    }

    @Override
    public void rightClick(PlayerInteractEvent e) {
        Bukkit.broadcastMessage("Test");
    }

    @Override
    public void leftClick(PlayerInteractEvent e) {

    }

    @Override
    public Material getBlockMaterial() {
        return Material.STONE;
    }

    @Override
    public int getBreakingTime(PlayerInteractEvent interactEvent) {
        return 0;
    }

    @Override
    public ItemStack getDisplayBlockItem() {
        ItemStack displayItem = new ItemStack(Material.BREAD);
        ItemMeta displayItemMeta = displayItem.getItemMeta();
        displayItemMeta.setDisplayName(ChatColor.RED + "Example Block");
        displayItem.setItemMeta(displayItemMeta);
        return displayItem;
    }
}
