package net.diecode.killermoney.functions;

import java.math.BigDecimal;
import java.util.Map;
import net.diecode.killermoney.BukkitMain;
import net.diecode.killermoney.Utils;
import net.diecode.killermoney.configs.DefaultConfig;
import net.diecode.killermoney.enums.KMPermission;
import net.diecode.killermoney.enums.LanguageString;
import net.diecode.killermoney.events.*;
import net.diecode.killermoney.managers.EconomyManager;
import net.diecode.killermoney.managers.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.diecode.killermoney.objects.LoseCashProperties;
import org.bukkit.entity.Player;

public class LoseCashHandler implements Listener {
    private static BigDecimal lostMoney = BigDecimal.ZERO;
    private static BigDecimal money;

    @EventHandler
    public void onLoseCash(KMLoseCashProcessorEvent e) {
        System.out.print("KM.Debug #4b - Processor passed");
        if (e.isCancelled()) {
            return;
        }
        
        LoseCashProperties loseCashProperties = e.getLoseCashProperties();
        if (loseCashProperties.getMethod().equals("AMOUNT")) {
            System.out.print("KM.Debug #4c - Sending to LoseCashEvent - Amount");
            Bukkit.getPluginManager().callEvent(new KMLoseCashEvent(loseCashProperties, e.getAmount(),
                e.getVictim()));
        } else if (loseCashProperties.getMethod().equals("PERCENT")) {
            System.out.print("KM.Debug #4c - Sending to LoseCashEvent - Percent");
            Bukkit.getPluginManager().callEvent(new KMLoseCashEvent(loseCashProperties, e.getAmount(),
                e.getVictim()));
        }
    }

    @EventHandler
    public void onLoseCash(KMLoseCashEvent e) {
        System.out.print("KM.Debug #5b - Event passed");
        if (e.isCancelled()) {
            return;
        }
        
        // Withdraw money
        EconomyManager.withdraw(e.getVictim(), e.getAmount());

        // Send money losing message to player
        if (e.getVictim() != null && e.getVictim().isOnline()) {
            String message = LanguageManager.cGet(LanguageString.GENERAL_YOU_DIED, e.getAmount().doubleValue());
        }
        
        lostMoney = lostMoney.add(e.getAmount());
    }
    
    public static void process(LoseCashProperties lcp, Player victim) {
        System.out.print("KM.Debug #2 - LoseCashHandler.process");
        if (BukkitMain.getEconomy() == null) {
            return;
        }

        if (!lcp.chanceGen()) {
            return;
        }
        
        if ("AMOUNT".equals(lcp.getMethod())) {
            System.out.print("KM.Debug #3a - Found Amount method");
            money = new BigDecimal(Utils.randomNumber(lcp.minMoney(),
                lcp.maxMoney())).setScale(DefaultConfig.getDecimalPlaces(), BigDecimal.ROUND_HALF_EVEN);

            // Check for permission if necessary and multiply with discounters if present
            if (money.doubleValue() > 0) {
                    if ((lcp.getPermission() != null && !lcp.getPermission().isEmpty())
                            && (!victim.hasPermission(lcp.getPermission()))) {
                        return;
                    }
                    System.out.print("KM.Debug #3a.2 hasPerm");
                    // Use discounters ( Permission based )
                    money = money.multiply(new BigDecimal(getMoneyDiscounter(victim)));
            }
        } else if ("PERCENT".equals(lcp.getMethod())){
            System.out.print("KM.Debug #3b - Found Percent method");
            BigDecimal victimsMoney = new BigDecimal(BukkitMain.getEconomy().getBalance(victim));
            money = victimsMoney.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_EVEN).multiply(
                    new BigDecimal(lcp.getPercent())).setScale(DefaultConfig.getDecimalPlaces(), BigDecimal.ROUND_HALF_EVEN);
            
            if (money.doubleValue() > 0) {
                if ((lcp.getPermission() != null && !lcp.getPermission().isEmpty())
                        && (!victim.hasPermission(lcp.getPermission()))) {
                    return;
                }

                System.out.print("KM.Debug #3b.2 hasPerm");
                // Use discounters ( Permission based )
                money = money.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_EVEN).multiply(
                    new BigDecimal(getMoneyDiscounter(victim))).setScale(DefaultConfig.getDecimalPlaces(), BigDecimal.ROUND_HALF_EVEN);
            }
            
            if (lcp.getMaxAmount() != 0 && lcp.getMaxAmount() < money.intValue()) {
                money = new BigDecimal(lcp.getMaxAmount()).setScale(DefaultConfig.getDecimalPlaces(),
                    BigDecimal.ROUND_HALF_EVEN);
            }
        }
        
        Bukkit.getPluginManager().callEvent(new KMLoseCashProcessorEvent(lcp, victim, money));
    }

    private static double getMoneyDiscounter(Player p) {
        System.out.print("KM.Debug #3c - Checking getMoneyDiscounter");
        if (p != null) {
            for (Map.Entry<String, Double> discounter : DefaultConfig.getMoneyDiscounters().entrySet()) {
                if (p.hasPermission(KMPermission.MONEY_DISCOUNTER.get() + "." + discounter.getKey())) {
                    return discounter.getValue();
                }
            }
        }

        return 100;
    }
}
