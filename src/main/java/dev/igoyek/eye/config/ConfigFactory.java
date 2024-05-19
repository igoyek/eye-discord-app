package dev.igoyek.eye.config;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ConfigFactory {

    public <T extends OkaeriConfig> T produceConfig(@NotNull Class<T> type, @NotNull File file) {
        return ConfigManager.create(type, initializer -> initializer
                .withConfigurer(new YamlSnakeYamlConfigurer())
                .withBindFile(file)
                .saveDefaults()
                .load(true));
    }
}
