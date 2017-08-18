package com.keensta.Animamorphacandy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	
		public Animamorphacandy plugin;
		
		public Config(Animamorphacandy plugin) {
			this.plugin = plugin;
		}
		
		public void checkConfig(String name) {
			final File dataDirectory = plugin.getDataFolder();
			if (dataDirectory.exists() == false)
				dataDirectory.mkdirs();

			final File f = new File(plugin.getDataFolder(), name+".yml");
			if (f.canRead())
				return;

			plugin.log.info("[Animamorphacandy] Writing default " + name+".yml");
			final InputStream in = plugin.getResource("resources/" + name+".yml");

			if (in == null) {
				plugin.log.severe("[Animamorphacandy] Could not find " + name+".yml" + " resource");
				return;
			} else {
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(f);
					final byte[] buf = new byte[512];
					int len;
					while ((len = in.read(buf)) > 0) {
						fos.write(buf, 0, len);
					}
				} catch (final IOException iox) {
					plugin.log.severe("[Animamorphacandy] Could not export " + name+".yml");
					return;
				} finally {
					if (fos != null)
						try {
							fos.close();
						} catch (final IOException iox) {
						}
					if (in != null)
						try {
							in.close();
						} catch (final IOException iox) {
						}
				}
				return;
			}
		}
		
		public FileConfiguration loadConfig(String name) {
			return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), name+".yml"));
		}

		public void saveConfig(FileConfiguration cfg, String name) {
			try {
				cfg.save(new File(plugin.getDataFolder(), name+".yml"));
			} catch (IOException e) {
				Logger.getLogger("Minecraft").severe("[Animamorphacandy] Failed to save " + name +".yml!");
				e.printStackTrace();
			}
		}
		

}
