package me.dracofaad.energeticapi;

import me.dracofaad.energeticapi.Classes.Energy.EnergeticBlock;
import me.dracofaad.energeticapi.Classes.Energy.EnergeticItem;
import me.dracofaad.energeticapi.Classes.Interfaces.ITickable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

class EnergeticTickHandler {
    EnergeticGarbageCollection energeticGarbageCollection;

    public EnergeticTickHandler(EnergeticGarbageCollection energeticGarbageCollection) {
        this.energeticGarbageCollection = energeticGarbageCollection;
    }

    BukkitRunnable bukkitRunnable = new BukkitRunnable() {
        @Override
        public void run() {
            //Block Garbage Collection

            energeticGarbageCollection.CheckBlockTypes();

            //Tick items / Create instances
            for (Player player : Bukkit.getOnlinePlayers()) {
                InvLoop:
                for (ItemStack itemStack : player.getInventory()) {
                    if (itemStack == null) continue;
                    for (EnergeticItem item : EnergeticItemHandler.getInstance().ItemInstances) {
                        if (item.IsItem(itemStack)) {
                            if (item instanceof ITickable) {
                                item.setItem(itemStack);
                                ((ITickable) item).tick();
                            }
                            continue InvLoop;
                        }
                    }

                    for (EnergeticItemHandler.RegisteredEnergeticItemClass ItemClass : EnergeticItemHandler.getInstance().ItemClasses) {
                        try {
                            Boolean IsType = (Boolean) ItemClass.aClass.getMethod("IsStaticType", ItemStack.class, String.class).invoke(null, itemStack, EnergeticItemHandler.getInstance().GetRegisteredUniqueID(ItemClass.aClass));
                            if (IsType) {
                                Constructor<? extends EnergeticItem> constructor = ItemClass.aClass.getConstructor(ItemStack.class);
                                constructor.newInstance(itemStack);
                                continue InvLoop;
                            }
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        } catch (InstantiationException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            //Tick blocks
            for (EnergeticBlock.BlockData blockData : EnergeticAPI.getEnergeticAPI().blockInstancesData.Blocks) {
                if (blockData.currentInstance instanceof ITickable) {
                    ((ITickable) blockData).tick();
                }
            }
        }
    };

    public void startTicking() {
        bukkitRunnable.runTaskTimer(EnergeticAPI.getEnergeticAPI(), 1, 1);
    }

    public void stopTicking() {
        bukkitRunnable.cancel();
    }
}
