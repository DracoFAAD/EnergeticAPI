package me.dracofaad.energeticapi.Classes.Energy;

import me.dracofaad.energeticapi.EnergeticAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

abstract class EnergeticBase implements Serializable {
    protected float Energy = 0;
    protected float MaxEnergy = 0;
    public String UniqueID = "";
    public String UniqueInstanceID = "";

    public EnergeticBase() {

    }

    public EnergeticBase(float energy, float maxEnergy) {
        this.Energy = energy;
        this.MaxEnergy = maxEnergy;
    }

    public float getEnergy() {
        return Energy;
    }
    public abstract void setEnergy(float Energy);

    public float getMaxEnergy() {
        return Energy;
    }
    public abstract void setMaxEnergy(float MaxEnergy);

    public void subtractEnergy(float value) {
        setEnergy(Energy - value);
    }

    public void addEnergy(float value) {
        setEnergy(Energy + value);
    }

    public static <T, Z> @Nullable Z getData(@NotNull String key, @NotNull PersistentDataType<T, Z> DataType, ItemStack Item) {
        return Item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey("energeticapi", key.toLowerCase()), DataType);
    }

    public static <T, Z> void setData(@NotNull String key, @NotNull PersistentDataType<T, Z> DataType, @NotNull Z value, ItemStack Item) {
        ItemMeta itemMeta = Item.getItemMeta();
        PersistentDataContainer ItemData = itemMeta.getPersistentDataContainer();
        ItemData.set(new NamespacedKey("energeticapi", key.toLowerCase()), DataType, value);
        Item.setItemMeta(itemMeta);
    }
}
