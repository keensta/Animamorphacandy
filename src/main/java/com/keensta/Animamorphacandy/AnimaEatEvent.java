package com.keensta.Animamorphacandy;

import java.util.HashMap;
import java.util.Set;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.WarpEffect;

public class AnimaEatEvent implements Listener {
	
	public Animamorphacandy plugin;
	private HashMap<String, String> disguises = new HashMap<String, String>();
	
	public AnimaEatEvent(Animamorphacandy plugin) {
		this.plugin = plugin;
		
		loadDisguises();
	}
	
	private void loadDisguises() {
		FileConfiguration disguisesFile = plugin.getConfigFile().loadConfig("disguiseConfig");
		Set<String> disguiseList = disguisesFile.getConfigurationSection("Disguises").getKeys(false);
		
		for(String food : disguiseList) {
			disguises.put(food, disguisesFile.getString("Disguises." + food + ".Type"));
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerEat(PlayerInteractEvent ev) {
		
		Action a = ev.getAction();
		Player p =  ev.getPlayer();
		
		if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
			Material mat = p.getItemInHand().getType();
			
			if (mat == Material.GOLDEN_APPLE) {
				undisguisePlayer(p);
			} else {
				if (disguises.containsKey(mat.name())) {
					disguisePlayer(p, mat.name());
				}
			}
		}
		
	}

	private void undisguisePlayer(Player p) {
		if (DisguiseAPI.isDisguised(p)) {
			DisguiseAPI.undisguiseToAll(p);
			playChangeEffect(p);
		}
	}

	private void disguisePlayer(Player p, String name) {
		MobDisguise mobD = new MobDisguise(DisguiseType.valueOf(disguises.get(name).toUpperCase()));
		LivingWatcher livingWatcher = (LivingWatcher) mobD.getWatcher();
		
		livingWatcher.addPotionEffect(PotionEffectType.GLOWING);
		
		DisguiseAPI.disguiseToAll(p, mobD);
		playChangeEffect(p);
	}

	
	private void playChangeEffect(Player p) {
		EffectManager effectMan = plugin.getEffectMan();
		
		WarpEffect warpEffect = new WarpEffect(effectMan);
		warpEffect.setEntity(p);
		warpEffect.iterations = 1 * 20;
		warpEffect.start();
	}
}
