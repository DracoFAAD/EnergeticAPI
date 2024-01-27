package me.dracofaad.energeticapi;

import me.dracofaad.energeticapi.Classes.Energy.EnergeticBlock;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageAbortEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnergeticBlockHandler implements Listener {
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            for (EnergeticBlock.BlockData blockData : EnergeticAPI.getEnergeticAPI().blockInstancesData.Blocks) {
                if (blockData.currentInstance.IsBlock(event.getClickedBlock())) {
                    if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK ) {
                        blockData.currentInstance.leftClick(event);
                        if (!event.isCancelled()) {
                            event.getClickedBlock().setType(Material.AIR);
                            event.getClickedBlock().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), blockData.currentInstance.getBlockItem());
                            blockData.currentInstance.postBreakBlock(event);
                        }
                    }

                    if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        blockData.currentInstance.rightClick(event);
                    }
                    return;
                }

                if (event.getItem() != null) {
                    if (blockData.currentInstance.IsBlockItem(event.getItem())) {
                        event.setCancelled(true);
                        BlockFace face = event.getBlockFace();
                        Location locationToPlaceBlock = event.getClickedBlock().getLocation().add(face.getModX(), face.getModY(), face.getModZ());

                        blockData.currentInstance.prePlaceBlock(event);

                        Material blockMaterial = blockData.currentInstance.getBlockMaterial();
                        Block block = event.getClickedBlock().getWorld().getBlockAt(locationToPlaceBlock);
                        block.setType(blockMaterial);

                        blockData.Block = new EnergeticBlock.SLocation(block.getLocation());

                        blockData.currentInstance.postPlaceBlock(event);

                        EnergeticAPI.getEnergeticAPI().blockInstancesData.saveData();

                        event.getItem().setType(Material.AIR);
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void PlayerStopBreakBlockEvent(BlockDamageAbortEvent event) {

    }
}
