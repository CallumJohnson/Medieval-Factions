package dansplugins.factionsystem.commands;

import dansplugins.factionsystem.ConfigManager;
import dansplugins.factionsystem.LocaleManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfigCommand {

    // args count is at least 1 at this point (/mf config)
    // possible sub-commands are show and set
    public void handleConfigAccess(CommandSender sender, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("mf.config") || player.hasPermission("mf.admin")) {
                if (args.length > 1) {

                    if (args[1].equalsIgnoreCase("show") || args[1].equalsIgnoreCase(LocaleManager.getInstance().getText("CmdConfigShow"))) {
                        // no further arguments needed, list config
                        ConfigManager.getInstance().sendPlayerConfigList(player);
                        return;
                    }

                    if (args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase(LocaleManager.getInstance().getText("CmdConfigSet"))) {

                        // two more arguments needed
                        if (args.length > 3) {

                            String option = args[2];
                            String value = args[3];

                            ConfigManager.setConfigOption(option, value, player);
                            return;
                        }
                        else {
                            player.sendMessage(ChatColor.RED + LocaleManager.getInstance().getText("UsageConfigSet"));
                            return;
                        }

                    }

                    player.sendMessage(ChatColor.RED + LocaleManager.getInstance().getText("ValidSubCommandsShowSet"));

                }
                else {
                    player.sendMessage(ChatColor.RED + LocaleManager.getInstance().getText("ValidSubCommandsShowSet"));
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + String.format(LocaleManager.getInstance().getText("PermissionNeeded"), "mf.config"));
            }
        }

    }

}