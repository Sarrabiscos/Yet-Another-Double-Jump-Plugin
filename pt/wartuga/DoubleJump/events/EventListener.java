package pt.wartuga.DoubleJump.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import pt.wartuga.DoubleJump.Main;

public class EventListener implements Listener {

	private Main plugin;

	public EventListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onToggleFlight(PlayerToggleFlightEvent event) {
		final Player p = event.getPlayer();
		if (p.getGameMode().equals(GameMode.CREATIVE))
			return;
		if (!p.hasPermission("doublejump.allow"))
			return;
		if (!plugin.worlds.contains(p.getLocation().getWorld().getName()))
			return;

		if (plugin.xp)
			p.setExp(0);
		event.setCancelled(true);
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			public void run() {
				p.setAllowFlight(false);
				p.setFlying(false);
				Vector vector = p.getEyeLocation().getDirection();
				vector.multiply(plugin.multiplier);
				vector.setY(plugin.height);
				p.setVelocity(vector);
				p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 10F, 1F);
			}
		}, 1L);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (p.getGameMode().equals(GameMode.CREATIVE))
			return;
		if (!p.hasPermission("doublejump.allow"))
			return;
		if (!plugin.worlds.contains(p.getLocation().getWorld().getName()))
			return;

		Location loc = p.getLocation();

		if (!loc.getBlock().getRelative(BlockFace.DOWN).getType()
				.equals(Material.AIR)
				|| !loc.getBlock().getRelative(BlockFace.NORTH).getType()
						.equals(Material.AIR)
				|| !loc.getBlock().getRelative(BlockFace.SOUTH).getType()
						.equals(Material.AIR)
				|| !loc.getBlock().getRelative(BlockFace.EAST).getType()
						.equals(Material.AIR)
				|| !loc.getBlock().getRelative(BlockFace.WEST).getType()
						.equals(Material.AIR)) {
			p.setAllowFlight(true);
			if (plugin.xp)
				p.setExp(1F);
		}
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player
				&& event.getCause().equals(DamageCause.FALL)) {
			Player p = (Player) event.getEntity();
			if(!plugin.worlds.contains(p.getLocation().getWorld().getName()))
				return;
			if (!plugin.fd && p.hasPermission("doublejump.allow")) {
				event.setCancelled(true);
				event.setDamage(0D);
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		Player p = event.getPlayer();
		if (p.getGameMode().equals(GameMode.CREATIVE))
			return;
		if (!p.hasPermission("doublejump.allow"))
			return;
		if (!plugin.worlds.contains(p.getLocation().getWorld().getName()))
			return;
		
		if(plugin.xp)
			p.setExp(1);
	}
	
	@EventHandler
	public void onChangeWorld(PlayerChangedWorldEvent event){
		Player p = event.getPlayer();
		if (p.getGameMode().equals(GameMode.CREATIVE))
			return;
		if (!p.hasPermission("doublejump.allow"))
			return;
		if (!plugin.worlds.contains(p.getLocation().getWorld().getName()))
			return;
		
		if(plugin.xp)
			p.setExp(1);
	}
}
