package com.poixson.flatworldgen;

import org.bukkit.generator.ChunkGenerator;

import com.poixson.tools.xJavaPlugin;


public class FlatWorldGenPlugin extends xJavaPlugin {
	@Override public int getBStatsID() { return 21474; }



	public FlatWorldGenPlugin() {
		super(FlatWorldGenPlugin.class);
	}



	@Override
	public void onEnable() {
		super.onEnable();
	}
	@Override
	public void onDisable() {
		super.onDisable();
	}



	@Override
	public ChunkGenerator getDefaultWorldGenerator(final String worldName, final String layersStr) {
		this.log().info("Using layers: " + layersStr);
		return new FlatWorldChunkGenerator(layersStr);
	}



}
