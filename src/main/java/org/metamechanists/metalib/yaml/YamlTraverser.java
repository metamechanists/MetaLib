package org.metamechanists.metalib.yaml;

import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class YamlTraverser {

    private final Plugin plugin;
    private final ConfigurationSection section;

    public YamlTraverser(Plugin plugin, ConfigurationSection section) {
        this.plugin = plugin;
        this.section = section;
    }

    public YamlTraverser(Plugin plugin, File file) {
        this.plugin = plugin;
        this.section = YamlConfiguration.loadConfiguration(file);
    }

    public YamlTraverser(Plugin plugin, String path) {
        this.plugin = plugin;
        this.section = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), path));
    }

    private void logMissingKey(String key) {
        plugin.getLogger().severe("Could not find " + section.getName() + "." + section.getCurrentPath() + "." + key);
    }

    public YamlTraverser getSection(String key, boolean throwsError) {
        if (throwsError) {
            try {
                section.getConfigurationSection(key);
            } catch (Exception e) {
                logMissingKey(key);
                e.printStackTrace();
            }
        }
        return new YamlTraverser(plugin, section.getConfigurationSection(key));
    }
    public YamlTraverser getSection(String key) {
        return getSection(key, true);
    }

    public Set<Pair<String, YamlTraverser>> getSections() {
        Set<Pair<String, YamlTraverser>> set = new HashSet<>();
        for (String key : section.getKeys(false)) {
             set.add(new Pair<>(key, new YamlTraverser(plugin, section.getConfigurationSection(key))));
        }
        return set;
    }

    public String name() {
        return section.getName();
    }
    @SuppressWarnings("unchecked")
    public <T> T get(String key, boolean throwsError) {
        if (throwsError) {
            try {
                T unused = (T) section.get(key);
            } catch (ClassCastException e) {
                logMissingKey(key);
                e.printStackTrace();
            }
        }
        return (T) section.get(key);
    }
    public <T> T get(String key, T defaultValue) {
        T value = get(key, false);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }
    public <T> T get(String key) {
        return get(key, true);
    }

    public <T> void set(String key, T value) {
        section.set(key, value);
    }

    // public String getString(String key, boolean throwsError) {
    //     if (throwsError) {
    //         try {
    //             section.getString(key);
    //             String s = (String) section.get(key);
    //         } catch (Exception e) {
    //             logMissingKey(key);
    //             e.printStackTrace();
    //         }
    //     }
    //     return section.getString(key);
    // }
    // public String getString(String key) {
    //     return getString(key, true);
    // }

    // public Boolean getBoolean(String key, boolean throwsError) {
    //     if (throwsError) {
    //         try {
    //             section.get(key);
    //         } catch (Exception e) {
    //             logMissingKey(key);
    //             e.printStackTrace();
    //         }
    //     }
    //     return section.getBoolean(key);
    // }
    // public Boolean getBoolean(String key) {
    //     return getBoolean(key, true);
    // }
    // public List<Boolean> getBooleanList(String key, boolean throwsError) {
    //     if (throwsError) {
    //         try {
    //             section.getBooleanList(key);
    //         } catch (Exception e) {
    //             logMissingKey(key);
    //             e.printStackTrace();
    //         }
    //     }
    //     return section.getBooleanList(key);
    // }
    // public List<Boolean> getBooleanList(String key) {
    //     return getBooleanList(key, true);
    // }

    // public Integer getInt(String key, boolean throwsError) {
    //     if (throwsError) {
    //         try {
    //             section.getInt(key);
    //         } catch (Exception e) {
    //             logMissingKey(key);
    //             e.printStackTrace();
    //         }
    //     }
    //     return section.getInt(key);
    // }
    // public Integer getInt(String key) {
    //     return getInt(key, true);
    // }

    // public double getDouble(String key, boolean throwsError) {
    //     if (throwsError) {
    //         try {
    //             section.getDouble(key);
    //         } catch (Exception e) {
    //             logMissingKey(key);
    //             e.printStackTrace();
    //         }
    //     }
    //     return section.getDouble(key);
    // }
    // public double getDouble(String key) {
    //     return getDouble(key, true);
    // }

    // public long getLong(String key, boolean throwsError) {
    //     if (throwsError) {
    //         try {
    //             section.getLong(key);
    //         } catch (Exception e) {
    //             logMissingKey(key);
    //             e.printStackTrace();
    //         }
    //     }
    //     return section.getLong(key);
    // }
    // public long getLong(String key) {
    //     return getLong(key, true);
    // }

    // public Color getColor(String key, boolean throwsError) {
    //     if (throwsError) {
    //         try {
    //             section.getColor(key);
    //         } catch (Exception e) {
    //             logMissingKey(key);
    //             e.printStackTrace();
    //         }
    //     }
    //     return section.getColor(key);
    // }
    // public Color getColor(String key) {
    //     return getColor(key, true);
    // }

    // public ItemStack getItemStack(String key, boolean throwsError) {
    //     if (throwsError) {
    //         try {
    //             section.getItemStack(key);
    //         } catch (Exception e) {
    //             logMissingKey(key);
    //             e.printStackTrace();
    //         }
    //     }
    //     return section.getItemStack(key);
    // }
    // public ItemStack getItemStack(String key) {
    //     return getItemStack(key, true);
    // }

    // public Location getLocation(String key, boolean throwsError) {
    //     if (throwsError) {
    //         try {
    //             section.getLocation(key);
    //         } catch (Exception e) {
    //             logMissingKey(key);
    //             e.printStackTrace();
    //         }
    //     }
    //     return section.getLocation(key);
    // }
    // public Location getLocation(String key) {
    //     return getLocation(key, true);
    // }

    // public Vector getVector(String key, boolean throwsError) {
    //     if (throwsError) {
    //         try {
    //             section.getLocation(key);
    //         } catch (Exception e) {
    //             logMissingKey(key);
    //             e.printStackTrace();
    //         }
    //     }
    //     return section.getVector(key);
    // }
    // public Vector getVector(String key) {
    //     return getVector(key, true);
    // }

    // public OfflinePlayer getOfflinePlayer(String key, boolean throwsError) {
    //     if (throwsError) {
    //         try {
    //             section.getOfflinePlayer(key);
    //         } catch (Exception e) {
    //             logMissingKey(key);
    //             e.printStackTrace();
    //         }
    //     }
    //     return section.getOfflinePlayer(key);
    // }
    // public OfflinePlayer getOfflinePlayer(String key) {
    //     return getOfflinePlayer(key, true);
    // }

    // public List<Byte> getByteList(String key, boolean throwsError) {
    //     if (throwsError) {
    //         try {
    //             section.getByteList(key);
    //         } catch (Exception e) {
    //             logMissingKey(key);
    //             e.printStackTrace();
    //         }
    //     }
    //     return section.getByteList(key);
    // }
    // public List<Byte> getByteList(String key) {
    //     return getByteList(key, true);
    // }

    // public List<Character> getCharacterList(String key, boolean throwsError) {
    //     if (throwsError) {
    //         try {
    //             section.getCharacterList(key);
    //         } catch (Exception e) {
    //             logMissingKey(key);
    //             e.printStackTrace();
    //         }
    //     }
    //     return section.getCharacterList(key);
    // }
    // public List<Character> getCharacterList(String key) {
    //     return getCharacterList(key, true);
    // }

    // public List<Double> getDoubleList(String key, boolean throwsError) {
    //     if (throwsError) {
    //         try {
    //             section.getDoubleList(key);
    //         } catch (Exception e) {
    //             logMissingKey(key);
    //             e.printStackTrace();
    //         }
    //     }
    //     return section.getDoubleList(key);
    // }
    // public List<Double> getDoubleList(String key) {
    //     return getDoubleList(key, true);
    // }

    // public List<Long> getLongList(String key, boolean throwsError) {
    //     if (throwsError) {
    //         try {
    //             section.getLongList(key);
    //         } catch (Exception e) {
    //             logMissingKey(key);
    //             e.printStackTrace();
    //         }
    //     }
    //     return section.getLongList(key);
    // }
    // public List<Long> getLongList(String key) {
    //     return getLongList(key, true);
    // }
}
