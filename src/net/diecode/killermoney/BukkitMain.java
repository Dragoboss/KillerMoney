package net.diecode.killermoney;

import com.garbagemule.MobArena.MobArenaHandler;
import net.diecode.killermoney.commands.KMCommand;
import net.diecode.killermoney.configs.DefaultConfig;
import net.diecode.killermoney.configs.EntitiesConfig;
import net.diecode.killermoney.configs.LangConfig;
import net.diecode.killermoney.functions.*;
import net.diecode.killermoney.managers.EntityManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitMain extends JavaPlugin {

    private static BukkitMain instance;
    private static DefaultConfig defaultConfig;
    private static LangConfig langConfig;
    private static EntitiesConfig entitiesConfig;
    private static Metrics metrics;

    private static Economy economy;
    private static MobArenaHandler mobArenaHandler;
    private static Updater updater;
    private static MineChartGraphs mineChartGraphs;

    private void initMetrics() {
        metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.SimplePie("used_message_method") {
            @Override
            public String getValue() {
                return DefaultConfig.getMessageMethod().name().toUpperCase();
            }
        });

        metrics.addCustomChart(new Metrics.SimplePie("used_money_item_drop") {
            @Override
            public String getValue() {
                return DefaultConfig.isMoneyItemDropEnabled() ? "Enabled" : "Disabled";
            }
        });
    }

    private boolean initEconomy() {
        RegisteredServiceProvider<Economy> economyProvider =
                Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    private void hookMobArena() {
        if (Bukkit.getPluginManager().getPlugin("MobArena") != null) {
            mobArenaHandler = new MobArenaHandler();

            Logger.info("MobArena hooked");
        } else {
            Logger.info("MobArena not found");
        }
    }

    public void hookMineChart() {
        if ((Bukkit.getPluginManager().getPlugin("MineChart") != null)
                && !DefaultConfig.getEnabledGraphs().isEmpty()) {
            mineChartGraphs = new MineChartGraphs();
        }
    }


    @Override
    public void onEnable() {
        instance = this;
        defaultConfig = new DefaultConfig("config.yml");
        langConfig = new LangConfig("lang.yml");
        entitiesConfig = new EntitiesConfig("entities.yml");
        updater = new Updater();

        initMetrics();

        if (!initEconomy()) {
            Logger.warning("Vault or economy plugin not found! Money reward disabled.");

            return;
        }

        if (DefaultConfig.isHookMobArena()) {
            hookMobArena();
        }

        if (DefaultConfig.isHookMineChart()) {
            hookMineChart();
        }

        getCommand("km").setExecutor(new KMCommand());

        Bukkit.getPluginManager().registerEvents(new EntityManager(), this);
        Bukkit.getPluginManager().registerEvents(new MoneyHandler(), this);
        Bukkit.getPluginManager().registerEvents(new CItemHandler(), this);
        Bukkit.getPluginManager().registerEvents(new CCommandHandler(), this);
        Bukkit.getPluginManager().registerEvents(new MessageHandler(), this);
        Bukkit.getPluginManager().registerEvents(new CashTransferHandler(), this);
        Bukkit.getPluginManager().registerEvents(new AntiFarmingHandler(), this);
        Bukkit.getPluginManager().registerEvents(new LimitHandler(), this);
        Bukkit.getPluginManager().registerEvents(new MultiplierHandler(), this);
        Bukkit.getPluginManager().registerEvents(new LoseCashHandler(), this);
        Bukkit.getPluginManager().registerEvents(updater, this);

        if (DefaultConfig.isCheckUpdate()) {
            getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
                @Override
                public void run() {
                    if (!Updater.isUpdateAvailable()) {
                        updater.query();
                    }
                }
            }, 20L, 20L * 60 * 60 * 24);
        }
    }

    @Override
    public void onDisable() {
        instance = null;
        defaultConfig = null;
        langConfig = null;
        entitiesConfig = null;
        updater = null;
    }

    public static BukkitMain getInstance() {
        return instance;
    }

    public static Economy getEconomy() {
        return economy;
    }

    public static MobArenaHandler getMobArenaHandler() {
        return mobArenaHandler;
    }
}
