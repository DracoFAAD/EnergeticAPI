package me.dracofaad.energeticapi;

import me.dracofaad.energeticapi.Classes.Energy.EnergeticBlock;
import me.dracofaad.energeticapi.ExampleItems.ExampleEnergyBlock;
import me.dracofaad.energeticapi.ExampleItems.ExampleEnergyContainerItem;
import me.dracofaad.energeticapi.ExampleItems.ExampleEnergyItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EnergeticCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            return false;
        }
        if (args[0].equals("example") && args.length > 1) {
            if (!(sender instanceof Player)) return false;
            if (args[1].equals("item")) {
                ExampleEnergyItem item = new ExampleEnergyItem(1000f);
                ((Player) sender).getInventory().addItem(item.getItem());

                ExampleEnergyContainerItem cItem = new ExampleEnergyContainerItem(2000f);
                ((Player) sender).getInventory().addItem(cItem.getItem());
            }

            if (args[1].equals("block")) {
                ExampleEnergyBlock block = new ExampleEnergyBlock(new EnergeticBlock.BlockData(1000, 1000, null, null, false));
                ((Player) sender).getInventory().addItem(block.getBlockItem());
            }
        }
        return false;
    }
}
