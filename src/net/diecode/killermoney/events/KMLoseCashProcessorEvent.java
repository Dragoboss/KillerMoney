package net.diecode.killermoney.events;

import java.math.BigDecimal;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.diecode.killermoney.objects.LoseCashProperties;

public class KMLoseCashProcessorEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private LoseCashProperties loseCashProperties;
    private Player victim;
    private BigDecimal amount;
    private boolean cancelled;

    public KMLoseCashProcessorEvent(LoseCashProperties loseCashProperties, Player victim, BigDecimal amount) {
        System.out.print("KM.Debug #4 - KMLoseCashProcessorEvent");
        this.loseCashProperties = loseCashProperties;
        this.victim = victim;
        this.amount = amount;
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
