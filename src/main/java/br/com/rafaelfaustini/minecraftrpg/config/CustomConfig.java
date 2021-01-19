package br.com.rafaelfaustini.minecraftrpg.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import br.com.rafaelfaustini.minecraftrpg.MinecraftRpg;
import br.com.rafaelfaustini.minecraftrpg.utils.ExceptionUtil;

public class CustomConfig {
    private File file;
    private FileConfiguration fileConfig;

    public CustomConfig(String name) {
        createConfig(name);
    }

    public FileConfiguration getConfig() {
        return fileConfig;
    }

    public void save() {
        try {
            fileConfig.save(file);
        } catch (IOException e) {
            ExceptionUtil.tryException(e);
        }
    }

    public void reload() {
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }

    public void set(String section, Object value) {
        try {
            fileConfig.set(section, value);
            fileConfig.save(file);
        } catch (IOException e) {
            ExceptionUtil.tryException(e);
        }
    }

    private void createConfig(String name) {
        try {
            Plugin plugin = MinecraftRpg.getPlugin(MinecraftRpg.class);
            System.out.println(plugin);
            file = new File(plugin.getDataFolder(), name);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                plugin.saveResource(name, false);
            }

            fileConfig = new YamlConfiguration();

            fileConfig.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            ExceptionUtil.tryException(e);
        }
    }

}
