package dansplugins.factionsystem.managers;

import dansplugins.factionsystem.MedievalFactions;
import dansplugins.fiefs.Fiefs;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static ConfigManager instance;
    private boolean altered = false;

    private ConfigManager() {

    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public void handleVersionMismatch() {
        // set version
        if (!getConfig().isString("version")) {
            getConfig().addDefault("version", MedievalFactions.getInstance().getVersion());
        }
        else {
            getConfig().set("version", MedievalFactions.getInstance().getVersion());
        }

        // add defaults if they don't exist
        if (!getConfig().isInt("initialMaxPowerLevel")) {
            getConfig().addDefault("initialMaxPowerLevel", 20);
        }
        if (!getConfig().isInt("initialPowerLevel")) {
            getConfig().addDefault("initialPowerLevel", 5);
        }
        if (!getConfig().isBoolean("mobsSpawnInFactionTerritory")) {
            getConfig().addDefault("mobsSpawnInFactionTerritory", false);
        }
        if (!getConfig().isInt("powerIncreaseAmount")) {
            getConfig().addDefault("powerIncreaseAmount", 2);
        }
        if (!getConfig().isBoolean("laddersPlaceableInEnemyFactionTerritory")) {
            getConfig().addDefault("laddersPlaceableInEnemyFactionTerritory", true);
        }
        if (!getConfig().isInt("minutesBeforeInitialPowerIncrease")) {
            getConfig().addDefault("minutesBeforeInitialPowerIncrease", 30);
        }
        if (!getConfig().isInt("minutesBetweenPowerIncreases")) {
            getConfig().addDefault("minutesBetweenPowerIncreases", 60);
        }
        if (!getConfig().isBoolean("warsRequiredForPVP")) {
            getConfig().addDefault("warsRequiredForPVP", true);
        }
        if (!getConfig().isDouble("factionOwnerMultiplier")) {
            getConfig().addDefault("factionOwnerMultiplier", 2.0);
        }
        if (!getConfig().isDouble("officerPerMemberCount")){
            getConfig().addDefault("officerPerMemberCount", 5);
        }
        if (!getConfig().isDouble("factionOfficerMultiplier")){
            getConfig().addDefault("factionOfficerMultiplier", 1.5);
        }
        if (!getConfig().isBoolean("powerDecreases")) {
        	getConfig().addDefault("powerDecreases", true);
        }
        if (!getConfig().isInt("minutesBetweenPowerDecreases")) {
        	getConfig().addDefault("minutesBetweenPowerDecreases", 1440);
        }
        if (!getConfig().isInt("minutesBeforePowerDecrease")) {
        	getConfig().addDefault("minutesBeforePowerDecrease", 20160);
        }
        if (!getConfig().isInt("powerDecreaseAmount")) {
            getConfig().addDefault("powerDecreaseAmount", 1);
        }
        if (!getConfig().isInt("factionMaxNameLength")) {
            getConfig().addDefault("factionMaxNameLength", 20);
        }
        if (!getConfig().isInt("factionMaxNumberGates")) {
            getConfig().addDefault("factionMaxNumberGates", 5);
        }
        if (!getConfig().isInt("factionMaxGateArea")) {
            getConfig().addDefault("factionMaxGateArea", 64);
        }
        if (!getConfig().isBoolean("surroundedChunksProtected")) {
            getConfig().addDefault("surroundedChunksProtected", true);
        }
        if (!getConfig().isBoolean("zeroPowerFactionsGetDisbanded")) {
            getConfig().addDefault("zeroPowerFactionsGetDisbanded", false);
        }
        if (!getConfig().isDouble("vassalContributionPercentageMultiplier")) {
            getConfig().addDefault("vassalContributionPercentageMultiplier", 0.75);
        }
        if (!getConfig().isBoolean("nonMembersCanInteractWithDoors")) {
            getConfig().addDefault("nonMembersCanInteractWithDoors", false);
        }
        if (!getConfig().isBoolean("playersChatWithPrefixes")) {
            getConfig().addDefault("playersChatWithPrefixes", true);
        }
        if (!getConfig().isInt("maxClaimRadius")) {
            getConfig().addDefault("maxClaimRadius", 3);
        }
        if (!getConfig().isString("languageid")) {
            getConfig().addDefault("languageid", "en-us");
        }
        if (!getConfig().isBoolean("chatSharedInVassalageTrees")) {
            getConfig().addDefault("chatSharedInVassalageTrees", true);
        }
        if (!getConfig().isBoolean("allowAllyInteraction")) {
            getConfig().addDefault("allowAllyInteraction", false);
        }
        if (!getConfig().isBoolean("allowVassalageTreeInteraction")) {
            getConfig().addDefault("allowVassalageTreeInteraction", false);
        }
        if (!getConfig().isString("factionChatColor")) {
            getConfig().addDefault("factionChatColor", "gold");
        }
        if (!getConfig().isBoolean("territoryAlertPopUp")) {
            getConfig().addDefault("territoryAlertPopUp", true);
        }
        if (!getConfig().isBoolean("territoryIndicatorActionbar")) {
            getConfig().addDefault("territoryIndicatorActionbar", true);
        }
        if (!getConfig().isString("territoryAlertColor")) {
            getConfig().addDefault("territoryAlertColor", "white");
        }
        if (!getConfig().isBoolean("randomFactionAssignment")) {
            getConfig().addDefault("randomFactionAssignment", false);
        }
        if (!getConfig().isBoolean("allowNeutrality")) {
            getConfig().addDefault("allowNeutrality", false);
        }
        if (!getConfig().isBoolean("showPrefixesInFactionChat")) {
            getConfig().addDefault("showPrefixesInFactionChat", false);
        }
        if (!getConfig().isBoolean("debugMode")) {
            getConfig().addDefault("debugMode", false);
        }
        if (!getConfig().isBoolean("factionProtectionsEnabled")) {
            getConfig().addDefault("factionProtectionsEnabled", true);
        }
        if (!getConfig().isBoolean("limitLand")) {
            getConfig().addDefault("limitLand", true);
        }
        if (!getConfig().isBoolean("factionsCanSetPrefixColors")) {
            getConfig().addDefault("factionsCanSetPrefixColors", true);
        }

        deleteOldConfigOptionsIfPresent();

        getConfig().options().copyDefaults(true);
        MedievalFactions.getInstance().saveConfig();
    }

    private void deleteOldConfigOptionsIfPresent() {

        if (getConfig().isInt("officerLimit")) {
            getConfig().set("officerLimit", null);
        }

        if (getConfig().isInt("hourlyPowerIncreaseAmount")) {
            getConfig().set("hourlyPowerIncreaseAmount", null);
        }

        if (getConfig().isInt("maxPowerLevel")) {
            getConfig().set("maxPowerLevel", null);
        }

    }

    public void setConfigOption(String option, String value, CommandSender sender) {

        if (getConfig().isSet(option)) {

            if (option.equalsIgnoreCase("version")) {
                sender.sendMessage(ChatColor.RED + LocaleManager.getInstance().getText("CannotSetVersion"));
                return;
            }
            else if (option.equalsIgnoreCase("initialMaxPowerLevel") || option.equalsIgnoreCase("initialPowerLevel")
                    || option.equalsIgnoreCase("powerIncreaseAmount")
                    || option.equalsIgnoreCase("minutesBeforeInitialPowerIncrease")
                    || option.equalsIgnoreCase("minutesBetweenPowerIncreases")
                    || option.equalsIgnoreCase("officerLimit")
                    || option.equalsIgnoreCase("officerPerMemberCount")
                    || option.equalsIgnoreCase("minutesBetweenPowerDecreases")
                    || option.equalsIgnoreCase("minutesBeforePowerDecrease")
                    || option.equalsIgnoreCase("powerDecreaseAmount")
                    || option.equalsIgnoreCase("factionMaxNameLength")
	                || option.equalsIgnoreCase("factionMaxNumberGates")
	                || option.equalsIgnoreCase("factionMaxGateArea")
                    || option.equalsIgnoreCase("maxClaimRadius"))
            {
                getConfig().set(option, Integer.parseInt(value));
                sender.sendMessage(ChatColor.GREEN + LocaleManager.getInstance().getText("IntegerSet"));
            }
            else if (option.equalsIgnoreCase("mobsSpawnInFactionTerritory")
                    || option.equalsIgnoreCase("laddersPlaceableInEnemyFactionTerritory")
                    || option.equalsIgnoreCase("warsRequiredForPVP")
                    || option.equalsIgnoreCase("powerDecreases")
                    || option.equalsIgnoreCase("surroundedChunksProtected")
                    || option.equalsIgnoreCase("zeroPowerFactionsGetDisbanded")
                    || option.equalsIgnoreCase("nonMembersCanInteractWithDoors")
                    || option.equalsIgnoreCase("playersChatWithPrefixes")
                    || option.equalsIgnoreCase("chatSharedInVassalageTrees")
                    || option.equalsIgnoreCase("allowAllyInteraction")
                    || option.equalsIgnoreCase("allowVassalageTreeInteraction")
                    || option.equalsIgnoreCase("territoryAlertPopUp")
                    || option.equalsIgnoreCase("territoryIndicatorActionbar")
                    || option.equalsIgnoreCase("randomFactionAssignment")
                    || option.equalsIgnoreCase("allowNeutrality")
                    || option.equalsIgnoreCase("showPrefixesInFactionChat")
                    || option.equalsIgnoreCase("debugMode")
                    || option.equalsIgnoreCase("factionProtectionsEnabled")
                    || option.equalsIgnoreCase("limitLand")
                    || option.equalsIgnoreCase("factionsCanSetPrefixColors")) {
                getConfig().set(option, Boolean.parseBoolean(value));
                sender.sendMessage(ChatColor.GREEN + LocaleManager.getInstance().getText("BooleanSet"));
            }
            else if (option.equalsIgnoreCase("factionOwnerMultiplier")
                    || option.equalsIgnoreCase("factionOfficerMultiplier")
                    || option.equalsIgnoreCase("vassalContributionPercentageMultiplier")){
                getConfig().set(option, Double.parseDouble(value));
                sender.sendMessage(ChatColor.GREEN + LocaleManager.getInstance().getText("DoubleSet"));
            }
            else {
                getConfig().set(option, value);
                sender.sendMessage(ChatColor.GREEN + LocaleManager.getInstance().getText("StringSet"));

                if (option.equalsIgnoreCase("languageid")) {
                    LocaleManager.getInstance().reloadStrings();
                }
            }

            // save
            MedievalFactions.getInstance().saveConfig();
            altered = true;
        }
        else {
            sender.sendMessage(ChatColor.RED + String.format(LocaleManager.getInstance().getText("WasntFound"), option));
        }

    }

    public void saveConfigDefaults() {
        getConfig().addDefault("version", MedievalFactions.getInstance().getVersion());
        getConfig().addDefault("initialMaxPowerLevel", 20);
        getConfig().addDefault("initialPowerLevel", 5);
        getConfig().addDefault("powerIncreaseAmount", 2);
        getConfig().addDefault("mobsSpawnInFactionTerritory", false);
        getConfig().addDefault("laddersPlaceableInEnemyFactionTerritory", true);
        getConfig().addDefault("minutesBeforeInitialPowerIncrease", 30);
        getConfig().addDefault("minutesBetweenPowerIncreases", 60);
        getConfig().addDefault("warsRequiredForPVP", true);
        getConfig().addDefault("factionOwnerMultiplier", 2.0);
        getConfig().addDefault("officerPerMemberCount", 5);
        getConfig().addDefault("factionOfficerMultiplier", 1.5);
        getConfig().addDefault("powerDecreases", true);
        getConfig().addDefault("minutesBetweenPowerDecreases", 1440);
        getConfig().addDefault("minutesBeforePowerDecrease", 20160);
        getConfig().addDefault("powerDecreaseAmount", 1);
        getConfig().addDefault("factionMaxNameLength", 20);
        getConfig().addDefault("factionMaxNumberGates", 5);
        getConfig().addDefault("factionMaxGateArea", 64);
        getConfig().addDefault("surroundedChunksProtected", true);
        getConfig().addDefault("zeroPowerFactionsGetDisbanded", false);
        getConfig().addDefault("vassalContributionPercentageMultiplier", 0.75);
        getConfig().addDefault("nonMembersCanInteractWithDoors", false);
        getConfig().addDefault("playersChatWithPrefixes", true);
        getConfig().addDefault("maxClaimRadius", 3);
        getConfig().addDefault("languageid", "en-us");
        getConfig().addDefault("chatSharedInVassalageTrees", true);
        getConfig().addDefault("allowAllyInteraction", false);
        getConfig().addDefault("allowVassalageTreeInteraction", false);
        getConfig().addDefault("factionChatColor", "gold");
        getConfig().addDefault("territoryAlertPopUp", true);
        getConfig().addDefault("territoryAlertColor", "white");
        getConfig().addDefault("territoryIndicatorActionbar", true);
        getConfig().addDefault("randomFactionAssignment", false);
        getConfig().addDefault("allowNeutrality", false);
        getConfig().addDefault("showPrefixesInFactionChat", false);
        getConfig().addDefault("debugMode", false);
        getConfig().addDefault("factionProtectionsEnabled", true);
        getConfig().addDefault("limitLand", true);
        getConfig().addDefault("factionsCanSetPrefixColors", true);
        getConfig().options().copyDefaults(true);
        MedievalFactions.getInstance().saveConfig();
    }

    public void sendPageOneOfConfigList(CommandSender sender) {
        sender.sendMessage(ChatColor.AQUA + LocaleManager.getInstance().getText("ConfigListPageOne"));
        sender.sendMessage(ChatColor.AQUA + "version: " + getConfig().getString("version")
                + ", languageid: " + getConfig().getString("languageid")
                + ", debugMode: " + getConfig().getBoolean("debugMode")
                + ", initialMaxPowerLevel: " + getConfig().getInt("initialMaxPowerLevel")
                + ", initialPowerLevel: " +  getConfig().getInt("initialPowerLevel")
                + ", powerIncreaseAmount: " + getConfig().getInt("powerIncreaseAmount")
                + ", mobsSpawnInFactionTerritory: " + getConfig().getBoolean("mobsSpawnInFactionTerritory")
                + ", laddersPlaceableInEnemyFactionTerritory: " + getConfig().getBoolean("laddersPlaceableInEnemyFactionTerritory")
                + ", minutesBeforeInitialPowerIncrease: " + getConfig().getInt("minutesBeforeInitialPowerIncrease")
                + ", minutesBetweenPowerIncreases: " + getConfig().getInt("minutesBetweenPowerIncreases")
                + ", warsRequiredForPVP: " + getConfig().getBoolean("warsRequiredForPVP")
                + ", factionOwnerMultiplier: " + getConfig().getDouble("factionOwnerMultiplier")
                + ", officerPerMemberCount: " + getConfig().getInt("officerPerMemberCount")
                + ", factionOfficerMultiplier: " + getConfig().getDouble("factionOfficerMultiplier")
                + ", powerDecreases: " + getConfig().getBoolean("powerDecreases")
                + ", minutesBetweenPowerDecreases: " + getConfig().getInt("minutesBetweenPowerDecreases")
                + ", minutesBeforePowerDecrease: " + getConfig().getInt("minutesBeforePowerDecrease")
                + ", powerDecreaseAmount: " + getConfig().getInt("powerDecreaseAmount")
                + ", factionMaxNameLength: " + getConfig().getInt("factionMaxNameLength")
		        + ", factionMaxNumberGates: " + getConfig().getInt("factionMaxNumberGates"));
    }

    public void sendPageTwoOfConfigList(CommandSender sender) {
        sender.sendMessage(ChatColor.AQUA + LocaleManager.getInstance().getText("ConfigListPageTwo"));
        sender.sendMessage(ChatColor.AQUA+ "factionMaxGateArea: " + getConfig().getInt("factionMaxGateArea")
                + ", surroundedChunksProtected: " + getConfig().getBoolean("surroundedChunksProtected")
                + ", zeroPowerFactionsGetDisbanded: " + getConfig().getBoolean("zeroPowerFactionsGetDisbanded")
                + ", vassalContributionPercentageMultiplier: " + getConfig().getDouble("vassalContributionPercentageMultiplier")
                + ", nonMembersCanInteractWithDoors: " + getConfig().getBoolean("nonMembersCanInteractWithDoors")
                + ", playersChatWithPrefixes: " + getConfig().getBoolean("playersChatWithPrefixes")
                + ", maxClaimRadius: " + getConfig().getInt("maxClaimRadius")
                + ", chatSharedInVassalageTrees: " + getConfig().getBoolean("chatSharedInVassalageTrees")
                + ", allowAllyInteraction: " + getConfig().getBoolean("allowAllyInteraction")
                + ", allowVassalageTreeInteraction: " + getConfig().getBoolean("allowVassalageTreeInteraction")
                + ", factionChatColor: " + getConfig().getString("factionChatColor")
                + ", territoryAlertPopUp: " + getConfig().getBoolean("territoryAlertPopUp")
                + ", territoryAlertColor: " + getConfig().getString("territoryAlertColor")
                + ", territoryIndicatorActionbar: " + getConfig().getBoolean("territoryIndicatorActionbar")
                + ", randomFactionAssignment: " + getConfig().getBoolean("randomFactionAssignment")
                + ", allowNeutrality: " + getConfig().getBoolean("allowNeutrality")
                + ", showPrefixesInFactionChat: " + getConfig().getBoolean("showPrefixesInFactionChat")
                + ", factionProtectionsEnabled: " + getConfig().getBoolean("factionProtectionsEnabled")
                + ", limitLand: " + getConfig().getBoolean("limitLand")
                + ", factionsCanSetPrefixColors: " + getConfig().getBoolean("factionsCanSetPrefixColors"));
    }

    public boolean hasBeenAltered() {
        return altered;
    }

    public FileConfiguration getConfig() {
        return MedievalFactions.getInstance().getConfig();
    }

    public int getInt(String option) {
        return getConfig().getInt(option);
    }

    public boolean getBoolean(String option) {
        return getConfig().getBoolean(option);
    }

    public double getDouble(String option) {
        return getConfig().getDouble(option);
    }

    public String getString(String option) {
        return getConfig().getString(option);
    }

}
