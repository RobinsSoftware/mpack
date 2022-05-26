package ca.robinssoftware.mpack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    final ArrayList<Overlay> overlays;
    final ArrayList<Package> loaded;
    final HashMap<String, Package> nameMap;
    final Command command;
    final TabComplete tabComplete;
    final Language language;

    public Main() {
        command = new Command(this);
        tabComplete = new TabComplete(this);
        language = new Language(this);
        overlays = new ArrayList<>();
        loaded = new ArrayList<>();
        nameMap = new HashMap<>();
        
        // load overlays & packages
        File file = new File(getDataFolder().getPath() + "/overlay.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            saveResource("overlay.yml", false);
        }

        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(file);

            for(String str : config.getStringList("overlays")) {
                overlays.add(new Overlay(str));
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        File directory = new File(getDataFolder().getPath() + "/packagedata");
        directory.mkdirs();
        
        File[] directoryContents = new File(getDataFolder().getPath() + "/packagedata").listFiles();
        
        for (File f : directoryContents) {
            try {
                Package pkg = new Package(this, f);
                loaded.add(pkg);
                nameMap.put(pkg.getName(), pkg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onEnable() {
        getCommand("mpack").setExecutor(command);
        getCommand("mpack").setTabCompleter(tabComplete);
        getCommand("mpack").setAliases(List.of("mp", "pkg", "package"));
    }

    @Override
    public void onDisable() {

    }

}
