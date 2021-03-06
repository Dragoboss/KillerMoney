package net.diecode.killermoney.commands.subcommands;

import net.diecode.killermoney.Utils;
import net.diecode.killermoney.enums.KMPermission;
import net.diecode.killermoney.enums.LanguageString;
import net.diecode.killermoney.enums.SenderType;
import net.diecode.killermoney.events.KMMultiplierChangedEvent;
import net.diecode.killermoney.managers.CommandManager;
import net.diecode.killermoney.managers.LanguageManager;
import net.diecode.killermoney.functions.MultiplierHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class MultiplierCommand extends CommandManager {

    public MultiplierCommand() {
        permission = KMPermission.ADMIN;
        minArgs = 1;
        usage = LanguageManager.cGet(LanguageString.COMMANDS_COMMAND_MULTIPLIER_USAGE);
        senderType = SenderType.ANYONE;
    }

    @Override
    public void run(CommandSender cs, String[] args) {
        String subCommand = args[0];

        if (subCommand.equalsIgnoreCase("set")) {
            if (args.length == 3) {
                try {
                    double multiplier = Double.valueOf(args[1]);
                    int minute = Integer.valueOf(args[2]);

                    if (multiplier < 1) {
                        LanguageManager.send(cs, LanguageString.MULTIPLIER_THE_MULTIPLIER_MUST_BE_GREATER_THAN_ZERO);

                        return;
                    }

                    if (minute <= 0) {
                        LanguageManager.send(cs, LanguageString.MULTIPLIER_THE_MINUTE_MUST_BE_GREATER_THAN_ZERO);

                        return;
                    }

                    Bukkit.getPluginManager().callEvent(new KMMultiplierChangedEvent(multiplier, minute, cs));
                } catch (NumberFormatException e) {
                    LanguageManager.send(cs, LanguageString.MULTIPLIER_INVALID_VALUE);
                }
            } else {
                LanguageManager.send(cs, LanguageString.COMMANDS_COMMAND_MULTIPLIER_SET_USAGE);
            }

            return;
        }

        if (subCommand.equalsIgnoreCase("get")) {
            if (MultiplierHandler.getTimer() != null) {
                LanguageManager.send(cs, LanguageString.MULTIPLIER_GET_CURRENT_MULTIPLIER,
                        MultiplierHandler.getMultiplier(),
                        Utils.getRemainingTimeHumanFormat(MultiplierHandler.getMinuteLeft()));
            } else {
                LanguageManager.send(cs, LanguageString.MULTIPLIER_THERE_IS_NOT_CUSTOM_MULTIPLIER_SET);
            }

            return;
        }

        if (subCommand.equalsIgnoreCase("cancel")) {
            if (MultiplierHandler.getTimer() != null) {
                Bukkit.getPluginManager().callEvent(new KMMultiplierChangedEvent(1, 0, cs));
            }  else {
                cs.sendMessage(LanguageManager.cGet(LanguageString.MULTIPLIER_THERE_IS_NOT_CUSTOM_MULTIPLIER_SET));
            }

            return;
        }
    }
}
