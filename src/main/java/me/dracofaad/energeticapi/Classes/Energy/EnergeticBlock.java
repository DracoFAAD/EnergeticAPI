package me.dracofaad.energeticapi.Classes.Energy;

import me.dracofaad.energeticapi.EnergeticAPI;
import me.dracofaad.energeticapi.EnergeticItemHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Objects;

public abstract class EnergeticBlock extends EnergeticBase implements Serializable {
    BlockData blockData = null;

    public EnergeticBlock(BlockData blockData) {
        this.UniqueID = EnergeticItemHandler.getInstance().getUniqueIDOfBlock(this.getClass());
        this.UniqueInstanceID = EnergeticItemHandler.getInstance().getUniqueInstanceID();
        EnergeticItemHandler.getInstance().BlockItemInstances.add(this);

        this.blockData = blockData;
        this.blockData.currentInstance = this;

        if (!blockData.DontAddToData) {
            EnergeticAPI.getEnergeticAPI().blockInstancesData.Blocks.add(blockData);
        }
    }

    @Override
    public void setEnergy(float Energy) {
        this.Energy = Energy;
        this.blockData.Energy = Energy;
    }

    @Override
    public void setMaxEnergy(float MaxEnergy) {
        this.MaxEnergy = MaxEnergy;
        this.blockData.MaxEnergy = MaxEnergy;
    }

    public abstract void onRightClick(PlayerInteractEvent e);
    public abstract void onLeftClick(PlayerInteractEvent e);
    public abstract Material getBlockMaterial();
    public abstract int getBreakingTime(PlayerInteractEvent interactEvent);

    public void prePlaceBlock(PlayerInteractEvent e) {
        // Needs to be overridden
    }

    public void postPlaceBlock(PlayerInteractEvent e) {
        // Needs to be overridden
    }

    public void postBreakBlock(PlayerInteractEvent e) {
        // Needs to be overridden
    }

    public ItemStack getBlockItem() {
        ItemStack itemStack = getDisplayBlockItem();
        setData("UniqueID", PersistentDataType.STRING, UniqueID, itemStack);
        setData("UniqueInstanceID", PersistentDataType.STRING, UniqueInstanceID, itemStack);
        return itemStack;
    }

    public abstract ItemStack getDisplayBlockItem();

    public static boolean IsEnergeticBlock(Block block) {
        for (BlockData energeticBlock : EnergeticAPI.getEnergeticAPI().blockInstancesData.Blocks) {
            if (energeticBlock.Block == null) continue;
            if (energeticBlock.Block.getLocation().equals(block.getLocation())) return true;
        }

        return false;
    }

    public boolean IsBlock(Block block) {
        if (blockData.Block == null) return false;

        return blockData.Block.IsLocation(block.getLocation());
    }

    public static boolean IsEnergeticBlockItem(ItemStack blockItem) {
        for (BlockData energeticBlock : EnergeticAPI.getEnergeticAPI().blockInstancesData.Blocks) {
            if (energeticBlock.Item == null) continue;
            if (getData("UniqueID", PersistentDataType.STRING, blockItem) == null) continue;
            if (getData("UniqueID", PersistentDataType.STRING, energeticBlock.Item) == getData("UniqueID", PersistentDataType.STRING, blockItem)) return true;
        }

        return false;
    }

    public boolean IsBlockItem(ItemStack blockItem) {
        return Objects.equals(getData("UniqueInstanceID", PersistentDataType.STRING, blockItem), UniqueInstanceID) && Objects.equals(getData("UniqueID", PersistentDataType.STRING, blockItem), UniqueID);
    }

    public static class BlockData implements Serializable {
        public float Energy;

        public float MaxEnergy;
        public transient ItemStack Item = null;
        public SLocation Block = null;

        public EnergeticBlock currentInstance;

        public boolean DontAddToData;

        public BlockData(float Energy, float MaxEnergy, @Nullable ItemStack Item, @Nullable Block block, boolean DontAddToData) {
            if (Energy != -1) {
                this.Energy = Energy;
                this.MaxEnergy = MaxEnergy;
            }

            if (Item != null) {
                this.Item = Item;
            }

            if (block != null) {
                this.Block = new SLocation(block.getLocation());
            }

            this.DontAddToData = DontAddToData;
        }
    }

    public static class SLocation implements Serializable {
        public double X;
        public double Y;
        public double Z;
        public String worldName;

        public SLocation(Location location) {
            X = location.getX();
            Y = location.getY();
            Z = location.getZ();
            worldName = location.getWorld().getName();
        }

        public boolean IsLocation(Location location) {
            return location.getX() == X && location.getY() == Y && location.getZ() == Z;
        }

        public Location getLocation() {
            return new Location(Bukkit.getWorld(worldName), X, Y, Z);
        }
    }
}
