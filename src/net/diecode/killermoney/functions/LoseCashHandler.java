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
        if (e.isCancelled()) {
            return;
        }
        
        LoseCashProperties loseCashProperties = e.getLoseCashProperties();
        if (loseCashProperties.getMethod().equals("AMOUNT")) {
            Bukkit.getPluginManager().callEvent(new KMLoseCashEvent(loseCashProperties, e.getAmount(),
                e.getVictim(), e.getDiscount()));
        } else if (loseCashProperties.getMethod().equals("PERCENT")) {
            Bukkit.getPluginManager().callEvent(new KMLoseCashEvent(loseCashProperties, e.getAmount(),
                e.getVictim(), e.getDiscount()));
        }
    }

    @EventHandler
    public void onLoseCash(KMLoseCashEvent e) {
        if (e.isCancelled()) {
            return;
        }

        // Send money losing message to player
        if (e.getVictim() != null && e.getVictim().isOnline()) {
            // Withdraw money
            EconomyManager.withdraw(e.getVictim().getPlayer(), e.getAmount());
            String message = LanguageManager.cGet(LanguageString.GENERAL_YOU_DIED, e.getAmount().doubleValue());
            if (e.getDiscount() != 100) {    
                message = LanguageManager.cGet(LanguageString.GENERAL_YOU_DIED_DISCOUNT, e.getAmount().doubleValue(), 100 - e.getDiscount());
            }
            
            MessageHandler.process(e.getVictim().getPlayer(), message);
        }
        
        lostMoney = lostMoney.add(e.getAmount());
    }
    
    public static void process(LoseCashProperties lcp, Player victim) {
        if (BukkitMain.getEconomy() == null) {
            return;
        }

        if (!lcp.chanceGen()) {
            return;
        }
        
        double discount = getMoneyDiscounter(victim);
        if ("AMOUNT".equals(lcp.getMethod())) {
            money = new BigDecimal(Utils.randomNumber(lcp.minMoney(),
                lcp.maxMoney())).setScale(DefaultConfig.getDecimalPlaces(), BigDecimal.ROUND_HALF_EVEN);

            // Check for permission if necessary and multiply with discounters if present
            if (money.doubleValue() > 0) {
                if ((lcp.getPermission() != null && !lcp.getPermission().isEmpty())
                        && (!victim.hasPermission(lcp.getPermission()))) {
                    return;
                }

                // Use discounters ( Permission based )
                money = money.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_EVEN).multiply(
                        new BigDecimal(discount));
            }
        } else if ("PERCENT".equals(lcp.getMethod())){
            BigDecimal victimsMoney = new BigDecimal(BukkitMain.getEconomy().getBalance(victim));
            money = victimsMoney.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_EVEN).multiply(
                    new BigDecimal(lcp.getPercent())).setScale(DefaultConfig.getDecimalPlaces(), BigDecimal.ROUND_HALF_EVEN);

            if (money.doubleValue() > 0) {
                if ((lcp.getPermission() != null && !lcp.getPermission().isEmpty())
                        && (!victim.hasPermission(lcp.getPermission()))) {
                    return;
                }

                // Use discounters ( Permission based )
                money = money.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_EVEN).multiply(
                    new BigDecimal(discount)).setScale(DefaultConfig.getDecimalPlaces(), BigDecimal.ROUND_HALF_EVEN);
            }
            
            if (lcp.getMaxAmount() != 0 && lcp.getMaxAmount() < money.intValue()) {
                money = new BigDecimal(lcp.getMaxAmount()).setScale(DefaultConfig.getDecimalPlaces(),
                    BigDecimal.ROUND_HALF_EVEN);
            }
        }
        
        Bukkit.getPluginManager().callEvent(new KMLoseCashProcessorEvent(lcp, victim, money, discount));
    }

    private static double getMoneyDiscounter(Player p) {
        if (p != null) {
            for (Map.Entry<String, Double> discounter : DefaultConfig.getMoneyDiscounters().entrySet()) {
                if (p.hasPermission(KMPermission.MONEY_DISCOUNTER.get() + "." + discounter.getKey())) {
                    return 100 - discounter.getValue();
                }
            }
        }

        return 100;
    }
}
