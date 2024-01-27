package me.dracofaad.energeticapi.Classes.Interfaces;

import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IItemContainer extends InventoryHolder {
    List<ItemStack> getItems();
}
