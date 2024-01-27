package me.dracofaad.energeticapi;

import me.dracofaad.energeticapi.Classes.Energy.EnergeticBlock;
import me.dracofaad.energeticapi.Classes.Energy.EnergeticItem;
import me.dracofaad.energeticapi.Classes.Interfaces.IItemContainer;
import me.dracofaad.energeticapi.ExampleItems.ExampleEnergyItem;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.guieffect.qual.UI;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class EnergeticItemHandler implements Listener {

    private static EnergeticItemHandler instance;

    public EnergeticItemHandler() {
        if (instance != null) {
            Bukkit.getConsoleSender().sendMessage("EnergeticItemHandler initialization tried! Please refrain from initializing.");
            return;
        }

        instance = this;
    }

    public List<RegisteredEnergeticItemClass> ItemClasses = new ArrayList<>();
    public List<RegisteredEnergeticBlockItemClass> BlockItemClasses = new ArrayList<>();
    public List<EnergeticItem> ItemInstances = new ArrayList<>();
    public List<EnergeticBlock> BlockItemInstances = new ArrayList<>();

    public void registerItemClass(Class<? extends EnergeticItem> ItemClass, JavaPlugin plugin) {
        Bukkit.getConsoleSender().sendMessage("Registered Item " + ItemClass.getName());
        ItemClasses.add(new RegisteredEnergeticItemClass(ItemClass, plugin.getName() + ItemClass.getName()));
    }

    public static EnergeticItemHandler getInstance() {
        return instance;
    }

    public String getUniqueInstanceID() {
        boolean found = false;
        String foundUUID = "";

        Loop:
        while (found == false) {
            String uuid = UUID.randomUUID().toString();
            for (EnergeticItem item : ItemInstances) {
                if (Objects.equals(item.UniqueInstanceID, uuid)) {
                    continue Loop;
                }
            }

            for (EnergeticBlock blockItem : BlockItemInstances) {
                if (Objects.equals(blockItem.UniqueInstanceID, uuid)) {
                    continue Loop;
                }
            }

            found = true;
            foundUUID = uuid;
        }
        return foundUUID;
    }

    public String getUniqueIDOfBlock(Class<? extends EnergeticBlock> clazz) {
        for (RegisteredEnergeticBlockItemClass registeredEnergeticBlockItemClass : BlockItemClasses) {
            if (registeredEnergeticBlockItemClass.aClass == clazz) {
                return registeredEnergeticBlockItemClass.UID;
            }
        }

        return null;
    }

    public void registerBlockClass(Class<? extends EnergeticBlock> BlockClass, JavaPlugin plugin) {
        Bukkit.getConsoleSender().sendMessage("Registered Block Item " + BlockClass.getName());
        BlockItemClasses.add(new RegisteredEnergeticBlockItemClass(BlockClass, plugin.getName() + BlockClass.getName()));
    }

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        if (!event.hasItem()) return;
        if (!event.getItem().hasItemMeta()) return;

        for (EnergeticItem item : ItemInstances) {
            if (!item.IsItem(event.getItem())) continue;

            item.setItem(event.getItem());

            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK ) {
                item.leftClick(event);
            }

            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                item.rightClick(event);
            }
            return;
        }
    }


    public String GetRegisteredUniqueID(Class<? extends EnergeticItem> clazz) {
        for (RegisteredEnergeticItemClass registeredEnergeticItemClass : ItemClasses) {
            if (registeredEnergeticItemClass.aClass == clazz) {
                return registeredEnergeticItemClass.UID;
            }
        }
        return "";
    }

    public static class RegisteredEnergeticItemClass {
        Class<? extends EnergeticItem> aClass;
        String UID;

        public RegisteredEnergeticItemClass(Class<? extends EnergeticItem> aClass, String UID) {
            this.aClass = aClass;
            this.UID = UID;
        }
    }

    public static class RegisteredEnergeticBlockItemClass {
        Class<? extends EnergeticBlock> aClass;
        String UID;

        public RegisteredEnergeticBlockItemClass(Class<? extends EnergeticBlock> aClass, String UID) {
            this.aClass = aClass;
            this.UID = UID;
        }
    }


}