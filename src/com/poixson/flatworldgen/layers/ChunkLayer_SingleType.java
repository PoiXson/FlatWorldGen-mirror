package com.poixson.flatworldgen.layers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator.ChunkData;


public class ChunkLayer_SingleType implements ChunkLayer {

	protected final int y;
	protected final BlockData block;



	public ChunkLayer_SingleType(final int y, final String typeStr) {
		this(y, Bukkit.createBlockData(typeStr));
	}
	public ChunkLayer_SingleType(final int y, final Material material) {
		this(y, Bukkit.createBlockData(material));
	}
	public ChunkLayer_SingleType(final int y, final BlockData block) {
		this.y     = y;
		this.block = block;
	}



	@Override
	public void generateChunk(final ChunkData chunk) {
		chunk.setRegion(
			0,  this.y,   0,
			16, this.y+1, 16,
			this.block
		);
	}



}
