package net.diecode.killermoney.objects;

import net.diecode.killermoney.Utils;
import java.util.List;

public class LoseCashProperties {

    private String method;
    private double percent;
    private int maxAmount;
    private double minMoney;
    private double maxMoney;
    private double chance;
    private String permission;
    private List<String> dmgCauses;
    private boolean enabledLose;

    public LoseCashProperties(String method, double percent, int maxAmount, double minMoney, double maxMoney, double chance, String permission,
                                  List<String> dmgCauses, boolean enabledLose) {
        this.method = method;
        this.percent = percent;
        this.maxAmount = maxAmount;
        this.minMoney = minMoney;
        this.maxMoney = maxMoney;
        this.chance = chance;
        this.permission = permission;
        this.dmgCauses = dmgCauses;
        this.enabledLose = enabledLose;
    }

    public String getMethod() {
        return method;
    }
    
    public double getPercent() {
        return percent;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public double minMoney() {
        return minMoney;
    }
    
    public double maxMoney() {
        return maxMoney;
    }
    
    public double getChance() {
        return chance;
    }

    public String getPermission() {
        return permission;
    }
    
    public List<String> getCauses() {
        return dmgCauses;
    }

    public boolean isEnabled() {
        return enabledLose;
    }

    public boolean chanceGen() {
        return Utils.chanceGenerator(this.chance);
    }
}
