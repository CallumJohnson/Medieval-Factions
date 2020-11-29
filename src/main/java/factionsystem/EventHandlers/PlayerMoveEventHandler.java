package factionsystem.EventHandlers;

import factionsystem.MedievalFactions;
import factionsystem.Objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

import static factionsystem.Subsystems.UtilitySubsystem.*;
import static org.bukkit.Bukkit.getServer;

public class PlayerMoveEventHandler {

    MedievalFactions main = null;

    public PlayerMoveEventHandler(MedievalFactions plugin) {
        main = plugin;
    }

    public void handle(PlayerMoveEvent event) {
        // Full disclosure, I feel like this method might be extremely laggy, especially if a player is travelling.
        // May have to optimise this, or just not have this mechanic.
        // - Dan
        // if player enters a new chunk
        if (event.getFrom().getChunk() != Objects.requireNonNull(event.getTo()).getChunk()) {

            // auto claim check
            for (Faction faction : main.factions) {
                if (faction.isOwner(event.getPlayer().getUniqueId())) {

                    if (faction.getAutoClaimStatus()) {

                        // if not at demesne limit
                        Faction playersFaction = getPlayersFaction(event.getPlayer().getUniqueId(), main.factions);
                        if (getChunksClaimedByFaction(playersFaction.getName(), main.claimedChunks) < playersFaction.getCumulativePowerLevel()) {
                            getServer().getScheduler().runTaskLater(main, new Runnable() {
                                @Override
                                public void run() {
                                    // add new chunk to claimed chunks
                                    main.utilities.addChunkAtPlayerLocation(event.getPlayer());
                                }
                            }, 1); // delayed by 1 tick (1/20th of a second) because otherwise players will claim the chunk they just left
                        }
                        else {
                            event.getPlayer().sendMessage(ChatColor.RED + "You have reached your demesne limit! Invite more players to increase this.");
                        }
                    }
                }
            }

            // if new chunk is claimed and old chunk was not
            if (isClaimed(event.getTo().getChunk(), main.claimedChunks) && !isClaimed(event.getFrom().getChunk(), main.claimedChunks)) {
                String title = getClaimedChunk(event.getTo().getChunk().getX(), event.getTo().getChunk().getZ(), event.getTo().getChunk().getWorld().getName(), main.claimedChunks).getHolder();
                event.getPlayer().sendTitle(title, null, 10, 70, 20);
                return;
            }

            // if new chunk is unclaimed and old chunk was not
            if (!isClaimed(event.getTo().getChunk(), main.claimedChunks) && isClaimed(event.getFrom().getChunk(), main.claimedChunks)) {
                event.getPlayer().sendTitle("Wilderness", null, 10, 70, 20);
                return;
            }

            // if new chunk is claimed and old chunk was also claimed
            if (isClaimed(event.getTo().getChunk(), main.claimedChunks) && isClaimed(event.getFrom().getChunk(), main.claimedChunks)) {
                // if chunk holders are not equal
                if (!(getClaimedChunk(event.getFrom().getChunk().getX(), event.getFrom().getChunk().getZ(), event.getFrom().getWorld().getName(), main.claimedChunks).getHolder().equalsIgnoreCase(getClaimedChunk(event.getTo().getChunk().getX(), event.getTo().getChunk().getZ(), event.getTo().getChunk().getWorld().getName(), main.claimedChunks).getHolder()))) {
                    String title = getClaimedChunk(event.getTo().getChunk().getX(), event.getTo().getChunk().getZ(), event.getTo().getChunk().getWorld().getName(), main.claimedChunks).getHolder();
                    event.getPlayer().sendTitle(title, null, 10, 70, 20);
                }
            }

        }
    }

}
