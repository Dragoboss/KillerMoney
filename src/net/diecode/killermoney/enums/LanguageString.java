package net.diecode.killermoney.enums;

public enum LanguageString {

    // General
    GENERAL_PREFIX("&7[ &aKillerMoney &7] > "),
    GENERAL_CURRENCY("$"),
    GENERAL_YOU_KILLED_AN_ENTITY_AND_EARN_MONEY("&7You killed a(n) entity (&a{0}&7, &a{1}&7). You earned &a${2}&7. Your damage was: &a{3}"), //{0} = entity type, {1} = entity_name, {2} = money, {3} = damage
    GENERAL_YOU_KILLED_AN_ENTITY_AND_LOSE_MONEY("&7You killed a(n) entity (&a{0}&7, &a{1}&7). You lost &a${2}&7. Your damage was: &a{3}"), //{0} = entity type, {1} = entity_name, {2} = money, {3} = damage
    GENERAL_YOU_PICKED_UP_MONEY("&7You picked up &a${0}&7."), // {0} = money
    GENERAL_CONFIGS_ARE_RELOADED("&7Configs are reloaded"),
    GENERAL_LIMIT_REACHED("&aLimit reached. &7Limit will be reset after {0}"), // {0} = reset time
    GENERAL_YOU_KILLED_A_PLAYER("&7You killed a player (&a{0}&7). You earned &a${1}&7. Your damage was: &a{2}"), // {0} = victim name, {1} = money, {2} = damage
    GENERAL_YOU_KILLED_BY_PLAYER("&7You killed by players (&a{0}&7). You lost &a${1}&7."), // {0} = killers' name, {1} = money
    GENERAL_YOU_DIED("&7You were killed... You lost &a${0}&7."), //{0} = money
    GENERAL_YOU_DIED_DISCOUNT("&7You were killed... You lost &a${0}&7. Your rank has &a{1}% &7discount."), //{0} = money, {1} = discount value
    
    // Multiplier
    MULTIPLIER_SET_NEW_MULTIPLIER_VALUE("&7You set multiplier to &a{0}x&7. Time left: &a{1}"), // {0} = multiplier value, {1} = time
    MULTIPLIER_GET_CURRENT_MULTIPLIER("&7Current multiplier value is &a{0}x&7. Time left: &a{1}"), // {0} = multiplier value, {1} = time
    MULTIPLIER_CANCEL_CUSTOM_MULTIPLIER("&7You cancelled custom multiplier value. Set it to default value (&a1x&7)"),
    MULTIPLIER_THERE_IS_NOT_CUSTOM_MULTIPLIER_SET("&7There is not custom multiplier set"),
    MULTIPLIER_THE_MULTIPLIER_MUST_BE_GREATER_THAN_ZERO("&7The multiplier must be greater than 0"),
    MULTIPLIER_THE_MINUTE_MUST_BE_GREATER_THAN_ZERO("&7The minute must be greater than 0"),
    MULTIPLIER_INVALID_VALUE("&7Invalid value: Multiplier and minutes must be a positive number and greater than 0"),

    COMMANDS_THIS_COMMAND_ONLY_USABLE_BY_PLAYER("&7This command is only usable by player"),
    COMMANDS_THIS_COMMAND_ONLY_USABLE_BY_CONSOLE("&7This command is only usable by console"),
    COMMANDS_YOU_HAVE_NOT_ENOUGH_PERMISSION("&7You have not enough permission (&a{0}&7)"), // {0} = permission
    COMMANDS_LIMIT_RESET("&7You reset all limit values to 0."),
    COMMANDS_COMMAND_MULTIPLIER_USAGE("&7Usage: &a/km multiplier &7<&aset&7, &aget&7, &acancel&7>"),
    COMMANDS_COMMAND_MULTIPLIER_SET_USAGE("&7Usage: &a/km multiplier set &7<&amultiplier value&7> <&aminute&7>"),
    COMMANDS_COMMAND_LIMIT_USAGE("&7Missing parameter. Usage: &a/km limit reset"),

    // Entities
    ENTITIES_BAT("Bat"),
    ENTITIES_BLAZE("Blaze"),
    ENTITIES_CAVE_SPIDER("Cave spider"),
    ENTITIES_CHICKEN("Chicken"),
    ENTITIES_COW("Cow"),
    ENTITIES_CREEPER("Creeper"),
    ENTITIES_DONKEY("Donkey"),
    ENTITIES_ELDER_GUARDIAN("Elder guardian"),
    ENTITIES_ENDER_DRAGON("Ender dragon"),
    ENTITIES_ENDERMAN("Enderman"),
    ENTITIES_ENDERMITE("Endermite"),
    ENTITIES_EVOKER("Evoker"),
    ENTITIES_GHAST("Ghast"),
    ENTITIES_GIANT("Giant"),
    ENTITIES_GUARDIAN("Guardian"),
    ENTITIES_HORSE("Horse"),
    ENTITIES_HUSK("Husk"),
    ENTITIES_IRON_GOLEM("Iron golem"),
    ENTITIES_LLAMA("Llama"),
    ENTITIES_MAGMA_CUBE("Magma cube"),
    ENTITIES_MULE("Mule"),
    ENTITIES_MUSHROOM_COW("Mushroom cow"),
    ENTITIES_OCELOT("Ocelot"),
    ENTITIES_PIG("Pig"),
    ENTITIES_PIG_ZOMBIE("Pig zombie"),
    ENTITIES_PLAYER("Player"),
    ENTITIES_POLAR_BEAR("Polar bear"),
    ENTITIES_RABBIT("Rabbit"),
    ENTITIES_SHEEP("Sheep"),
    ENTITIES_SHULKER("Shulker"),
    ENTITIES_SILVERFISH("Silverfish"),
    ENTITIES_SKELETON("Skeleton"),
    ENTITIES_SKELETON_HORSE("Skeleton horse"),
    ENTITIES_SLIME("Slime"),
    ENTITIES_SNOWMAN("Snowman"),
    ENTITIES_SPIDER("Spider"),
    ENTITIES_SQUID("Squid"),
    ENTITIES_STRAY("Stray"),
    ENTITIES_VEX("Vex"),
    ENTITIES_VILLAGER("Villager"),
    ENTITIES_VINDICATOR("Vindicator"),
    ENTITIES_WITCH("Witch"),
    ENTITIES_WITHER_SKELETON("Wither skeleton"),
    ENTITIES_WOLF("Wolf"),
    ENTITIES_ZOMBIE("Zombie"),
    ENTITIES_ZOMBIE_HORSE("Zombie horse"),
    ENTITIES_ZOMBIE_VILLAGER("Zombie villager");

    private String string;

    LanguageString(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public static boolean isValid(String str) {
        for (LanguageString ls : LanguageString.values()) {
            if (ls.name().equals(str)) {
                return true;
            }
        }

        return false;
    }

}
