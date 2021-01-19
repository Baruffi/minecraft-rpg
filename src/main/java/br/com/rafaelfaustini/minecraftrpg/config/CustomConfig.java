package br.com.rafaelfaustini.minecraftrpg.config;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import br.com.rafaelfaustini.minecraftrpg.MinecraftRpg;
import br.com.rafaelfaustini.minecraftrpg.utils.LoggingUtil;

public class CustomConfig {
    private File file;
    private FileConfiguration fileConfig;

    public CustomConfig(String name) {
        createConfig(name);
    }

    public Integer getInteger(String path) {
        return Integer.valueOf(fileConfig.getInt(path));
    }

    public List<Integer> getIntegerList(String path) {
        return fileConfig.getIntegerList(path);
    }

    public String getString(String path) {
        return fileConfig.getString(path);
    }

    public List<String> getStringList(String path) {
        return fileConfig.getStringList(path);
    }

    public Set<String> getSectionKeys(String path) {
        return fileConfig.getConfigurationSection(path).getKeys(false);
    }

    public void save() {
        try {
            fileConfig.save(file);
        } catch (IOException e) {
            LoggingUtil.loadException(e.getMessage(), e);
        }
    }

    public void reload() {
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }

    public void set(String section, String value) {
        try {
            fileConfig.set(section, value);
            fileConfig.save(file);
        } catch (IOException e) {
            LoggingUtil.loadException(e.getMessage(), e);
        }
    }

    private void createConfig(String name) {
        try {
            Plugin plugin = MinecraftRpg.getPlugin(MinecraftRpg.class);

            LoggingUtil.info(plugin.toString());

            file = new File(plugin.getDataFolder(), name);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                plugin.saveResource(name, false);
            }

            fileConfig = new YamlConfiguration();

            fileConfig.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            LoggingUtil.loadException(e.getMessage(), e);
        }
    }
}
