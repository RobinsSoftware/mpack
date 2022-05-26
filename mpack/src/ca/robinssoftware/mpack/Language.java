package ca.robinssoftware.mpack;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class Language {

    Language(Main main) {
        File file = new File(main.getDataFolder(), "/language.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            main.saveResource("language.yml", false);
        }

        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(file);

            for (Fields field : Fields.values()) {
                field.label = config.getString(field.name());
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String get(Fields field, String... replacements) {
        String str = field.toString();

        for (String replacement : replacements) {
            str = str.replaceAll("%s", replacement);
        }

        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static enum Fields {
        PREFIX, COMMAND_NOT_FOUND, NO_PERMISSION, INSUFFICIENT_ARGS, VERSION, SYNC_FAILED, SYNC, SYNC_FINISHED,
        SYNC_OVERLAY;

        String label;

        @Override
        public String toString() {
            return label;
        }
    }

}
