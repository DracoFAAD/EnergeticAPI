package me.dracofaad.energeticapi;

import me.dracofaad.energeticapi.Classes.BlockInstancesData;
import me.dracofaad.energeticapi.ExampleItems.ExampleEnergyBlock;
import me.dracofaad.energeticapi.ExampleItems.ExampleEnergyContainerItem;
import me.dracofaad.energeticapi.ExampleItems.ExampleEnergyItem;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

public final class EnergeticAPI extends JavaPlugin {

    public EnergeticItemHandler energeticItemHandler;
    public EnergeticBlockHandler energeticBlockHandler;
    private EnergeticTickHandler energeticTickHandler;
    private EnergeticGarbageCollection energeticGarbageCollection;

    public BlockInstancesData blockInstancesData;

    private static EnergeticAPI instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        blockInstancesData = BlockInstancesData.loadData();

        if (blockInstancesData == null) {
            blockInstancesData = new BlockInstancesData(new HashSet<>());
            blockInstancesData.saveData();
        }

        energeticItemHandler = new EnergeticItemHandler();
        energeticBlockHandler = new EnergeticBlockHandler();
        energeticGarbageCollection = new EnergeticGarbageCollection();
        energeticTickHandler = new EnergeticTickHandler(energeticGarbageCollection);

        energeticTickHandler.startTicking();

        getServer().getPluginManager().registerEvents(energeticGarbageCollection, this);
        getServer().getPluginManager().registerEvents(energeticItemHandler, this);
        getServer().getPluginManager().registerEvents(energeticBlockHandler, this);

        EnergeticItemHandler.getInstance().registerItemClass(ExampleEnergyItem.class, this);
        EnergeticItemHandler.getInstance().registerItemClass(ExampleEnergyContainerItem.class, this);

        EnergeticItemHandler.getInstance().registerBlockClass(ExampleEnergyBlock.class, this);

        getCommand("energeticapi").setExecutor(new EnergeticCommand());
    }

    public static EnergeticAPI getEnergeticAPI() {
        if (instance == null) {
            instance = (EnergeticAPI) Bukkit.getPluginManager().getPlugin("EnergeticAPI");
        }
        return instance;
    }
}
