package com.nirvana.xin;

import com.nirvana.purist.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Nirvana on 2018/1/24.
 */
public class XinConfiguration {

    private static final XinConfiguration INSTANCE = new XinConfiguration();

    private Map<String, String> configs = new ConcurrentHashMap<>();

    private XinConfiguration() {}

    public static XinConfiguration getInstance() {
        return INSTANCE;
    }

    public String getConfig(Config config) {
        String value = configs.get(config.key);
        if (StringUtils.isBlank(value)) {
            value = config.getDefaultValue();
        }
        return value;
    }

    public void checkAfterConfiguration() {
        for (Config config : Config.values()) {
            if (config.required && StringUtils.isBlank(getConfig(config))) {
                throw new ConfigurationException("config : \"" + config.getKey() + "\" not present.");
            }
        }
    }

    public enum Config {

        DATA_BASE_DIR("data_dir", null, "the directory that message files store in.", true),

        MSG_FILE_MAX_SIZE("msg_file_max_size", "1024m", "max message file size.", true);

        private String key;

        private String defaultValue;

        private String description;

        private Boolean required;

        Config(String key, String defaultValue, String description, boolean required) {
            this.key = key;
            this.defaultValue = defaultValue;
            this.description = description;
            this.required = required;
        }

        public String getKey() {
            return key;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public String getDescription() {
            return description;
        }

        public Boolean getRequired() {
            return required;
        }
    }

}
