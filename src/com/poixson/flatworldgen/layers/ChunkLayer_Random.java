package com.poixson.flatworldgen.layers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import com.poixson.tools.xRand;
import com.poixson.utils.Utils;


public class ChunkLayer_Random implements ChunkLayer {

	protected final int y;

	protected final HashMap<BlockData, Double> types = new HashMap<BlockData, Double>();

	protected final double total_percent;

	protected final xRand random = (new xRand()).seed_time();



	public ChunkLayer_Random(final int y, final String typeStr) {
		this.y = y;
		final String[] parts = typeStr.split("[=]");
		for (final String part : parts) {
			final String[] prts = part.split("[%]");
			if (prts.length == 1) {
				final Material mat = Material.getMaterial( prts[0].toUpperCase() );
				if (mat == null) throw new NullPointerException("Unknown material: " + prts[0]);
				this.types.put(mat.createBlockData(), Double.valueOf(0.0));
				continue;
			}
			if ("*".equals(prts[0])) {
				final Material mat = Material.getMaterial( prts[1].toUpperCase() );
				if (mat == null) throw new NullPointerException("Unknown material: " + prts[1]);
				this.types.put(mat.createBlockData(), Double.valueOf(0.0));
				continue;
			}
			{
				final double percent = Double.parseDouble(prts[0]);
				final Material mat = (
					Utils.IsEmpty(prts[1])
					? Material.AIR
					: Material.getMaterial( prts[1].toUpperCase() )
				);
				if (mat == null) throw new NullPointerException("Unknown material: " + prts[1]);
				this.types.put(mat.createBlockData(), Double.valueOf(percent));
			}
		}
		// calculate for *
		int wildcardCount = 0;
		double percentTotal = 0.0;
		{
			final Iterator<Double> it = this.types.values().iterator();
			while (it.hasNext()) {
				final Double val = it.next();
				if (val == 0.0) wildcardCount++; else
				if (val >  0.0) percentTotal += val;
			}
		}
		if (wildcardCount == 0) {
			this.total_percent = percentTotal;
		// wildcard > 0
		} else {
			this.total_percent = (percentTotal < 100.0 ? 100.0 : percentTotal);
			final double percentRemain = (percentTotal >= 100.0 ? 0.0 : 100.0 - percentTotal);
			final double percentRemainSplit = percentRemain / ((double)wildcardCount);
			final Iterator<Entry<BlockData, Double>> it = this.types.entrySet().iterator();
			while (it.hasNext()) {
				final Entry<BlockData, Double> entry = it.next();
				final BlockData block = entry.getKey();
				final Double    val   = entry.getValue();
				if (val == 0.0) this.types.put(block, Double.valueOf(percentRemainSplit));
			}
		}
	}



	@Override
	public void generateChunk(final ChunkData chunk) {
		for (int x=0; x<16; x++) {
			for (int z=0; z<16; z++) {
				// pick a random block type
				BlockData block = null;
				final Iterator<Entry<BlockData, Double>> it = this.types.entrySet().iterator();
				double d = 0.0;
				BlockData lastBlock = null;
				// random number
				final double rnd = this.random.nextDbl(0, this.total_percent);
				while (it.hasNext()) {
					final Entry<BlockData, Double> entry = it.next();
					final Double val = entry.getValue();
					d += val;
					if (d >= rnd) { block = entry.getKey(); break; }
					lastBlock = entry.getKey();
				}
				if (block == null) block = lastBlock;
				if (block == null) throw new NullPointerException("Random block is null");
				chunk.setBlock(x, this.y, z, block);
			}
		}
	}



}
