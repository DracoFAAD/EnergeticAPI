package me.dracofaad.energeticapi.Classes.Energy;

import me.dracofaad.energeticapi.EnergeticItemHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public abstract class EnergeticItem extends EnergeticBase {

    private ItemStack Item;

    public EnergeticItem(ItemStack itemStack) throws RuntimeException {
        if (!IsEnergeticItem(itemStack)) {
            throw new RuntimeException("EnergeticItem initialized with an itemstack that isnt energetic.");
        }

        SetUniqueID(EnergeticItemHandler.getInstance().GetRegisteredUniqueID(getClass()));

        if (!IsType(itemStack)) {
            throw new RuntimeException("EnergeticItem initialized with an itemstack that isnt the same class.");
        }

        Item = itemStack;
        Energy = getData("Energy", PersistentDataType.FLOAT, itemStack);
        MaxEnergy = getData("MaxEnergy", PersistentDataType.FLOAT, itemStack);

        newInstance();
        setData("UniqueInstanceID", PersistentDataType.STRING, UniqueInstanceID, itemStack);
    }

    public EnergeticItem(float energy, float maxEnergy) {
        super(energy, maxEnergy);
        SetUniqueID(EnergeticItemHandler.getInstance().GetRegisteredUniqueID(getClass()));
        newInstance();
        setItem();
    }

    private void newInstance() {
        SetUniqueInstanceID(EnergeticItemHandler.getInstance().getUniqueInstanceID());
        EnergeticItemHandler.getInstance().ItemInstances.add(this);
    }

    private void setItem() {
        Item = createItem();

        setData("Energy", PersistentDataType.FLOAT, Energy, Item);
        setData("MaxEnergy", PersistentDataType.FLOAT, MaxEnergy, Item);
        setData("UniqueID", PersistentDataType.STRING, UniqueID, Item);
        setData("UniqueInstanceID", PersistentDataType.STRING, UniqueInstanceID, Item);
    }

    public void deleteInstance() {
        EnergeticItemHandler.getInstance().ItemInstances.remove(this);
    }

    @Override
    public void setEnergy(float Energy) {
        if (Energy < 0) Energy = 0;
        if (Energy > MaxEnergy) Energy = MaxEnergy;
        this.Energy = Energy;
        setData("Energy", PersistentDataType.FLOAT, Energy, getItem());
        onEnergyUpdated();
    }

    @Override
    public void setMaxEnergy(float MaxEnergy) {
        this.MaxEnergy = MaxEnergy;
        setData("MaxEnergy", PersistentDataType.FLOAT, MaxEnergy, Item);
    }

    public static boolean IsEnergeticItem(ItemStack itemStack) {
        return Boolean.TRUE.equals(getData("Energy", PersistentDataType.FLOAT, itemStack) != null) && Boolean.TRUE.equals(getData("MaxEnergy", PersistentDataType.FLOAT, itemStack) != null) && Boolean.TRUE.equals(getData("UniqueID", PersistentDataType.STRING, itemStack) != null);
    }

    public boolean IsItem(ItemStack itemStack) {
        return Objects.equals(getData("UniqueInstanceID", PersistentDataType.STRING, itemStack), UniqueInstanceID);
    }

    public boolean IsType(ItemStack itemStack) {
        return Objects.equals(getData("UniqueID", PersistentDataType.STRING, itemStack), UniqueID);
    }

    public static boolean IsStaticType(ItemStack itemStack, String ID) {
        return Objects.equals(getData("UniqueID", PersistentDataType.STRING, itemStack), ID);
    }

    public boolean HasEnergyLeft() {
        return Energy == 0;
    }

    public abstract void onRightClick(PlayerInteractEvent event);
    public abstract void onLeftClick(PlayerInteractEvent event);

    public abstract ItemStack createItem();

    public ItemStack getItem() {
        return Item;
    }

    public void setItem(ItemStack item) {
        Item = item;
    }

    public void SetUniqueID(String id) {
        UniqueID = id;
    }

    public void SetUniqueInstanceID(String id) {
        UniqueInstanceID = id;
    }

    public abstract void onEnergyUpdated();
}