package net.diecode.killermoney.objects;

import java.util.ArrayList;

public class PlayerWorldProperties extends WorldProperties {

    private CashTransferProperties cashTransferProperties;

    public PlayerWorldProperties(ArrayList<String> worlds, MoneyProperties moneyProperties,
                           CCommandProperties cCommandProperties, CItemProperties cItemProperties,
                                 CashTransferProperties cashTransferProperties) {
        super(worlds, moneyProperties, cCommandProperties, cItemProperties);

        this.cashTransferProperties = cashTransferProperties;
    }

    public CashTransferProperties getCashTransferProperties() {
        return cashTransferProperties;
    }
}