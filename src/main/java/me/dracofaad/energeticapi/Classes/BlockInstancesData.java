package me.dracofaad.energeticapi.Classes;

import me.dracofaad.energeticapi.Classes.Energy.EnergeticBlock;
import me.dracofaad.energeticapi.EnergeticAPI;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.HashSet;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class BlockInstancesData implements Serializable {
    private static transient final String FilePath = EnergeticAPI.getEnergeticAPI().getDataFolder().getAbsolutePath() + "/Blocks.data";

    public final HashSet<EnergeticBlock.BlockData> Blocks;

    public BlockInstancesData(HashSet<EnergeticBlock.BlockData> Blocks) {
        this.Blocks = Blocks;
    }

    public BlockInstancesData(BlockInstancesData loadedData) {
        this.Blocks = loadedData.Blocks;
    }

    public boolean saveData() {
        try {
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(FilePath)));
            out.writeObject(this);
            out.close();
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public static BlockInstancesData loadData() {
        try {
            File file = new File(FilePath);
            file.createNewFile();
            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(FilePath)));
            BlockInstancesData data = (BlockInstancesData) in.readObject();
            in.close();
            return data;
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
