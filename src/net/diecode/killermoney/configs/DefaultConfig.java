package net.diecode.killermoney.configs;

import net.diecode.killermoney.Logger;
import net.diecode.killermoney.enums.MessageMethod;
import net.diecode.killermoney.functions.MessageHandler;
import net.diecode.killermoney.managers.ConfigManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class DefaultConfig extends ConfigManager {

    private static DefaultConfig instance;

    private static boolean checkUpdate;
    private static int decimalPlaces;
    private static MessageMethod messageMethod;
    private static boolean hookMobArena;
    private static boolean disableRewardsInArena;
    private static boolean hookMineChart;
    private static ArrayList<String> enabledGraphs = new ArrayList<>();
    private static boolean antiFarmingSpawner;
    private static boolean antiFarmingSpawnerEgg;
    private static int limitResetTime;
    private static boolean reachedLimitMessage;
    private static ArrayList<GameMode> allowedGameModes = new ArrayList<>();

    private static boolean moneyItemDropEnabled;
    private static String moneyItemName;
    private static Material moneyItemMaterial = Material.GOLD_INGOT;

    private static HashMap<String, Double> moneyMultipliers = new HashMap<>();
    private static HashMap<String, Double> limitMultipliers = new HashMap<>();
    private static HashMap<String, Double> moneyDiscounters = new HashMap<>();

    public DefaultConfig(String fileName) {
        super(fileName);

        instance = this;

        load();
    }

    @Override
    public void load() {
        reload();

        // Check update
        checkUpdate = getConfig().getBoolean("Check-update");

        // Money decimal places
        decimalPlaces = getConfig().getInt("Global-settings.Money.Decimal-places");

        // Message method value
        try {
            messageMethod = MessageMethod.valueOf(getConfig().getString("Global-settings.General.Message-method").toUpperCase());
        } catch (Exception e) {
            messageMethod = MessageMethod.CHAT;

            Logger.warning("Invalid message method value. Using default \"CHAT\" value.");
        }

        if (messageMethod == MessageMethod.ACTION_BAR) {
            MessageHandler.initActionBar();
        }

        // Mobarena support
        hookMobArena = getConfig().getBoolean("Hook.MobArena.Enabled");

        // Disable reward in mobarena
        disableRewardsInArena = getConfig().getBoolean("Hook.MobArena.Disable-rewards-in-arena");

        // Farming settings
        antiFarmingSpawner = getConfig().getBoolean("Global-settings.Farming.Disable-spawner-farming");
        antiFarmingSpawnerEgg = getConfig().getBoolean("Global-settings.Farming.Disable-spawner-egg-farming");

        // Limit reset time
        limitResetTime = getConfig().getInt("Global-settings.Limit.Reset-time");

        if (limitResetTime < 1) {
            limitResetTime = 24;
        }

        // Limit reached settings
        reachedLimitMessage = getConfig().getBoolean("Global-settings.Limit.Reached-limit-message");

        // Allowed gamemodes
        String[] splitted = getConfig().getString("Global-settings.General.Allowed-gamemodes").replace(" ", "").split(",");

        for (String s : splitted) {
            try {
                GameMode gamemode = GameMode.valueOf(s.toUpperCase());

                if (!allowedGameModes.contains(gamemode)) {
                    allowedGameModes.add(gamemode);
                }
            } catch (Exception e) {
                Logger.warning("Invalid gamemode type: " + s.toUpperCase());
            }
        }

        // Money Item drop settings
        moneyItemDropEnabled = getConfig().getBoolean("Global-settings.Money.Item-drop.Enabled");
        moneyItemName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Global-settings.Money.Item-drop.Item-name"));

        try {
            moneyItemMaterial = Material.valueOf(getConfig().getString("Global-settings.Money.Item-drop.Item-material"));
        } catch (Exception e) {
            Logger.warning("Invalid money item material. Using default: " + moneyItemMaterial.name());
        }

        Set<String> perms = getConfig().getConfigurationSection("Configurable-permissions.Money-multipliers")
                .getKeys(false);

        for (String s : perms) {
            try {
                double value = getConfig().getDouble("Configurable-permissions.Money-multipliers." + s);

                moneyMultipliers.put(s, value);
            } catch (Exception e) {
                Logger.warning("Invalid money multiplier value at: " + s);
            }
        }

        perms = getConfig().getConfigurationSection("Configurable-permissions.Limit-multipliers").getKeys(false);

        for (String s : perms) {
            try {
                double value = getConfig().getDouble("Configurable-permissions.Limit-multipliers." + s);

                limitMultipliers.put(s, value);
            } catch (Exception e) {
                Logger.warning("Invalid limit multiplier value at: " + s);
            }
        }
        
        // Get the Discounters section from config
        perms = getConfig().getConfigurationSection("Configurable-permissions.Money-discounters").getKeys(false);
        for (String s : perms) {
            try {
                double value = getConfig().getDouble("Configurable-permissions.Money-discounters." + s);
                
                moneyDiscounters.put(s, value);
            } catch (Exception e) {
                Logger.warning("Invalid money discounter value at: " + s);
            }
        }

        hookMineChart = getConfig().getBoolean("Hook.MineChart.Enabled");

        for (String s : getConfig().getConfigurationSection("Hook.MineChart.Graphs").getKeys(false)) {
            if (getConfig().getBoolean("Hook.MineChart.Graphs." + s)) {
                enabledGraphs.add(s);
            }
        }
    }

    public static DefaultConfig getInstance() {
        return instance;
    }

    public static boolean isCheckUpdate() {
        return checkUpdate;
    }

    public static int getDecimalPlaces() {
        return decimalPlaces;
    }

    public static MessageMethod getMessageMethod() {
        return messageMethod;
    }

    public static boolean isHookMobArena() {
        return hookMobArena;
    }

    public static boolean isDisableRewardsInArena() {
        return disableRewardsInArena;
    }

    public static boolean isHookMineChart() {
        return hookMineChart;
    }

    public static ArrayList<String> getEnabledGraphs() {
        return enabledGraphs;
    }

    public static boolean isAntiFarmingSpawner() {
        return antiFarmingSpawner;
    }

    public static boolean isAntiFarmingSpawnerEgg() {
        return antiFarmingSpawnerEgg;
    }

    public static int getLimitResetTime() {
        return limitResetTime;
    }

    public static boolean isReachedLimitMessage() {
        return reachedLimitMessage;
    }

    public static ArrayList<GameMode> getAllowedGameModes() {
        return allowedGameModes;
    }

    public static boolean isMoneyItemDropEnabled() {
        return moneyItemDropEnabled;
    }

    public static String getMoneyItemName() {
        return moneyItemName;
    }

    public static Material getMoneyItemMaterial() {
        return moneyItemMaterial;
    }

    public static HashMap<String, Double> getMoneyMultipliers() {
        return moneyMultipliers;
    }

    public static HashMap<String, Double> getLimitMultipliers() {
        return limitMultipliers;
    }
    
    public static HashMap<String, Double> getMoneyDiscounters() {
        return moneyDiscounters;
    }
}
