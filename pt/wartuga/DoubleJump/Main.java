package pt.wartuga.DoubleJump;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import pt.wartuga.DoubleJump.events.EventListener;

public class Main extends JavaPlugin{
	
	public List<String> worlds = new ArrayList<String>();
	public double multiplier;
	public double height;
	public boolean xp;
	public boolean fd;
	
	public void onEnable() {
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
		
		worlds = getConfig().getStringList("Worlds");
		multiplier = getConfig().getDouble("Multiplier", 1.5);
		height = getConfig().getDouble("Height", 1.0);
		xp = getConfig().getBoolean("UseXPBar", true);
		fd = getConfig().getBoolean("TakeFallDamage", false);
	}
	
}

