package com.poixson.flatworldgen;

import org.bukkit.generator.ChunkGenerator;

import com.poixson.tools.xJavaPlugin;


public class FlatWorldGenPlugin extends xJavaPlugin {
//TODO: spigot id
	@Override public int getSpigotPluginID() { return 0; }
	@Override public int getBStatsID() {       return 21474;  }



	public FlatWorldGenPlugin() {
		super(FlatWorldGenPlugin.class);
	}



	@Override
	public void onEnable() {
	}
	@Override
	public void onDisable() {
	}



	@Override
	public ChunkGenerator getDefaultWorldGenerator(final String worldName, final String layersStr) {
		this.log().info("Using layers: " + layersStr);
		return new FlatWorldChunkGenerator(layersStr);
	}



}
