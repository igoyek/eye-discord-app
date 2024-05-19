package dev.igoyek.eye.config

import eu.okaeri.configs.ConfigManager
import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer
import java.io.File

class ConfigFactory {

    fun <T : OkaeriConfig> produceConfig(type: Class<T>, file: File): T {
        return ConfigManager.create(type) { initializer ->
            initializer
                .withConfigurer(YamlSnakeYamlConfigurer())
                .withBindFile(file)
                .saveDefaults()
                .load(true)
        }
    }
}