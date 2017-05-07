package net.diecode.killermoney.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.math.BigDecimal;
import net.diecode.killermoney.objects.LoseCashProperties;

public class KMLoseCashEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private LoseCashProperties loseCashProperties;
    private Player victim;
    private BigDecimal amount;
    private boolean cancelled;

    public KMLoseCashEvent(LoseCashProperties loseCashProperties, BigDecimal amount, Player victim) {
        System.out.print("KM.Debug #5 - KMLoseCashEvent");
        this.loseCashProperties = loseCashProperties;
        this.amount = amount;
        this.victim = victim;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public LoseCashProperties getLoseCashProperties() {
        return loseCashProperties;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Player getVictim() {
        return victim;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
