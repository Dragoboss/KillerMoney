package net.diecode.killermoney.objects;

import java.util.ArrayList;

public class PlayerWorldSecondaryProperties extends WorldProperties {

    private LoseCashProperties loseCashProperties;

    public PlayerWorldSecondaryProperties(ArrayList<String> worlds, MoneyProperties moneyProperties,
                           CCommandProperties cCommandProperties, CItemProperties cItemProperties,
                                 LoseCashProperties loseCashProperties) {
        super(worlds, moneyProperties, cCommandProperties, cItemProperties);

        this.loseCashProperties = loseCashProperties;
    }

    public LoseCashProperties getLoseCashProperties() {
        return loseCashProperties;
    }
}