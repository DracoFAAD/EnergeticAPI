package me.dracofaad.energeticapi;

import me.dracofaad.energeticapi.Classes.Energy.EnergeticBlock;
import me.dracofaad.energeticapi.Classes.Energy.EnergeticItem;
import me.dracofaad.energeticapi.Classes.Interfaces.ICustomType;
import me.dracofaad.energeticapi.ExampleItems.ExampleEnergyItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

public class EnergeticGarbageCollection implements Listener {
    @EventHandler
    public void OnItemDamaged(EntityDamageEvent event) {
        if (event.getEntity() instanceof Item) {
            for (EnergeticItem item : EnergeticItemHandler.getInstance().ItemInstances) {
                if (item.IsItem(((Item) event.getEntity()).getItemStack())) {
                    item.deleteInstance();
                    return;
                }
            }
        }
    }

    @EventHandler
    public void OnItemDuped(InventoryClickEvent event) {
        if (event.getAction() == InventoryAction.CLONE_STACK) {
            for (EnergeticItemHandler.RegisteredEnergeticItemClass clazz : EnergeticItemHandler.getInstance().ItemClasses) {
                EnergeticItem item = EnergeticItem.class.cast(clazz.aClass);
                if (item.IsType(event.getCurrentItem())) {
                    event.setCancelled(true);
                    return;
                }
            }

            for (EnergeticItemHandler.RegisteredEnergeticBlockItemClass clazz : EnergeticItemHandler.getInstance().BlockItemClasses) {
                EnergeticBlock item = EnergeticBlock.class.cast(clazz.aClass);
                if (item.IsBlockItem(event.getCurrentItem())) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    public void CheckBlockTypes() {
        Iterator<EnergeticBlock.BlockData> energeticBlockIterator = EnergeticAPI.getEnergeticAPI().blockInstancesData.Blocks.iterator();
        boolean changed = false;
        while (energeticBlockIterator.hasNext()) {
            EnergeticBlock.BlockData energeticBlock = energeticBlockIterator.next();

            Block block = energeticBlock.Block.getLocation().getBlock();

            if (energeticBlock.currentInstance instanceof ICustomType) {
                ICustomType iCustomType = (ICustomType) energeticBlock.currentInstance;

                if (iCustomType.checkType(block)) {
                    continue;
                }
            } else {
                Material normalMaterial = energeticBlock.currentInstance.getBlockMaterial();

                if (normalMaterial == block.getType()) {
                    continue;
                }
            }

            changed = true;
            energeticBlockIterator.remove();
        }

        if (changed) {
            EnergeticAPI.getEnergeticAPI().blockInstancesData.saveData();
        }
    }
}
