package com.poixson.flatworldgen;

import java.util.LinkedList;
import java.util.Random;

import org.bukkit.HeightMap;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import com.poixson.flatworldgen.layers.ChunkLayer;
import com.poixson.utils.Utils;


public class FlatWorldChunkGenerator extends ChunkGenerator {

	public static String DEFAULT_LAYERS = "64=minecraft:air|1=minecraft:bedrock|2=minecraft:stone|1=minecraft:grass_block";

	protected final ChunkLayer[] layers;



	public FlatWorldChunkGenerator(final String layersStr) {
		this.layers = DecodeLayersString(layersStr);
	}



	public static ChunkLayer[] DecodeLayersString(final String layersStr) {
		if (Utils.IsEmpty(layersStr))
			return new ChunkLayer[0];
		final LinkedList<ChunkLayer> layers = new LinkedList<ChunkLayer>();
		final String[] parts = layersStr.split("[|]");
		int y = -64;
		for (final String part : parts) {
			if (Utils.IsEmpty(part)) continue;
			final String[] prts = part.split("[=]");
			if (prts.length < 2)
				throw new NullPointerException("Invalid layer: " + part);
			final int height = Integer.parseInt(prts[0]);
			// fill layer blocks
			for (int i=0; i<height; i++) {
				// find layer type
				final ChunkLayer layer = ChunkLayer.FromString(y+i, prts[1]);
				if (layer == null)
					throw new NullPointerException("Unknown material: " + prts[1]);
				layers.add(layer);
			}
			y += height;
		}
		return layers.toArray(new ChunkLayer[0]);
	}



	@Override
	public void generateSurface(final WorldInfo worldInfo, final Random random,
			final int chunkX, final int chunkZ, final ChunkData chunk) {
		for (final ChunkLayer layer : this.layers)
			layer.generateChunk(chunk);
	}



	@Override
	public int getBaseHeight(
			final WorldInfo worldInfo, final Random random,
			final int x, final int z, final HeightMap heightMap) {
		return this.layers.length - 64;
	}



}
