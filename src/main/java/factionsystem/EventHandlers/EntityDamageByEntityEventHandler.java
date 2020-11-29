package factionsystem.EventHandlers;

import factionsystem.MedievalFactions;
import factionsystem.Objects.Duel;
import factionsystem.Subsystems.UtilitySubsystem;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class EntityDamageByEntityEventHandler {

    MedievalFactions main = null;

    public EntityDamageByEntityEventHandler(MedievalFactions plugin) {
        main = plugin;
    }

    public void handle(EntityDamageByEntityEvent event) {
    	
   	
        // this method disallows PVP between members of the same faction and between factions who are not at war
        // PVP is allowed between factionless players, players who belong to a faction and the factionless, and players whose factions are at war.
        // System.out.println("EntityDamageByIntity" + event.toString());

        // if this was between two players melee
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();
        	// if these players are actively duelling then we don't want to handle friendly fire.
            Duel duel = UtilitySubsystem.getDuel(attacker, victim, main);
            if (duel == null)
            {
            	handleIfFriendlyFire(event, attacker, victim);	
            }
            else if (duel.getStatus().equals(Duel.DuelState.DUELLING))
            {
            	if (victim.getHealth() - event.getFinalDamage() <= 0)
            	{
        			duel.setLoser(victim);
            		duel.finishDuel(false);
            		main.duelingPlayers.remove(this);
            		event.setCancelled(true);
            	}
            }
            else
            {
            	handleIfFriendlyFire(event, attacker, victim);
            }
        }
        else if (event.getDamager() instanceof Projectile && event.getEntity() instanceof Player) {
            Projectile arrow = (Projectile) event.getDamager();
            ProjectileSource source = arrow.getShooter();

            if (source instanceof Player){
                Player attacker = (Player) source;
                Player victim = (Player) event.getEntity();

            	// if these players are actively duelling then we don't want to handle friendly fire.
                Duel duel = UtilitySubsystem.getDuel(attacker, victim, main);
                if (duel == null)
                {
                	handleIfFriendlyFire(event, attacker, victim);	
                }
                else if (duel.getStatus().equals(Duel.DuelState.DUELLING))
                {
                	if (victim.getHealth() - event.getFinalDamage() <= 0)
                	{
            			duel.setLoser(victim);
                		duel.finishDuel(false);
                		main.duelingPlayers.remove(this);
                		event.setCancelled(true);
                	}
                }
                else
                {
                	handleIfFriendlyFire(event, attacker, victim);
                }
            }
        }
    }

    private void handleIfFriendlyFire(EntityDamageByEntityEvent event, Player attacker, Player victim) {
        if (!main.utilities.arePlayersInAFaction(attacker, victim)){
            // Factionless can fight anyone.
            return;
        }
        else if (main.utilities.arePlayersInSameFaction(attacker, victim)) {
            event.setCancelled(true);
            attacker.sendMessage(ChatColor.RED + "You can't attack another player if you are part of the same faction.");
        }

        // if attacker's faction and victim's faction are not at war
        else if (main.utilities.arePlayersFactionsNotEnemies(attacker, victim)) {
            if (main.getConfig().getBoolean("warsRequiredForPVP")) {
                event.setCancelled(true);
                attacker.sendMessage(ChatColor.RED + "You can't attack another player if your factions aren't at war.");
            }
        }
    }

}
