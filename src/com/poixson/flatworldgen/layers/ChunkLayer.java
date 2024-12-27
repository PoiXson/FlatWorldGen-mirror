package com.poixson.flatworldgen.layers;

import static com.poixson.utils.Utils.IsEmpty;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.ChunkData;


public interface ChunkLayer {



	public void generateChunk(final ChunkData chunk);



	public static ChunkLayer FromString(final int y, final String str) {
		// air
		if (IsEmpty(str))
			return new ChunkLayer_SingleType(y, Material.AIR);
		// random blocks by percent
		if (str.indexOf('%') != -1)
			return new ChunkLayer_Random(y, str);
		// single block type
		return new ChunkLayer_SingleType(y, str);
	}



}
