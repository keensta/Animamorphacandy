package com.keensta.Animamorphacandy;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import de.slikey.effectlib.EffectManager;

public class Animamorphacandy extends JavaPlugin {
	
	public Logger log = Logger.getLogger("Minecraft");
	private Config config;
	private EffectManager effectManager;
	
	@Override
	public void onEnable() {
		config = new Config(this);
		effectManager = new EffectManager(this);
		
		config.checkConfig("disguiseConfig");
		
		getServer().getPluginManager().registerEvents(new AnimaEatEvent(this), this);
		
		log.info("[Animamorphacandy] Is enabled!!");
	}
	
	@Override
	public void onDisable() {
		effectManager.dispose();
	}
	
	public EffectManager getEffectMan() {
		return effectManager;
	}
	
	public Config getConfigFile(){
		return config;
	}

}
