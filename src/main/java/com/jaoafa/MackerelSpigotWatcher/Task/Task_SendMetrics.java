package com.jaoafa.MackerelSpigotWatcher.Task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import com.jaoafa.MackerelSpigotWatcher.Main;
import com.jaoafa.MackerelSpigotWatcher.Lib.MackerelAPI;
import com.jaoafa.MackerelSpigotWatcher.Lib.MackerelAPI.Metric;
import com.jaoafa.MackerelSpigotWatcher.Lib.ParseLogFile;
import com.jaoafa.MackerelSpigotWatcher.Lib.TPS;

public class Task_SendMetrics extends BukkitRunnable {
	@Override
	public void run() {
		long time = System.currentTimeMillis() / 1000;
		int playerCount = Bukkit.getOnlinePlayers().size();
		double tps1m = TPS.getTPS1m();
		double tps5m = TPS.getTPS5m();
		double tps15m = TPS.getTPS15m();
		ParseLogFile plf = new ParseLogFile();
		long logSize = getLogSize();
		Runtime runtime = Runtime.getRuntime();
		float free = runtime.freeMemory();
		float total = runtime.totalMemory();
		float max = runtime.maxMemory();
		float used = total - free;

		MackerelAPI mackerelAPI = Main.getMackerelAPI();

		List<Metric> metrics = new ArrayList<>();
		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.player.count", playerCount, time));

		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.tps.1min", tps1m, time));
		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.tps.5min", tps5m, time));
		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.tps.15min", tps15m, time));

		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.log.severe", plf.getSevereCount(), time));
		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.log.warning", plf.getWarningCount(), time));
		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.log.info", plf.getInfoCount(), time));
		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.log.config", plf.getConfigCount(), time));
		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.log.fine", plf.getFineCount(), time));
		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.log.finer", plf.getFinerCount(), time));
		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.log.finest", plf.getFinestCount(), time));
		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.log.error", plf.getErrorCount(), time));
		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.log.debug", plf.getDebugCount(), time));

		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.logsize", logSize, time));

		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.memory.free", free, time));
		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.memory.used", used, time));
		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.memory.total", total, time));
		metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.memory.max", max, time));

		for (World world : Bukkit.getServer().getWorlds()) {
			int tileEntities = 0;

			try {
				for (Chunk chunk : world.getLoadedChunks()) {
					try {
						tileEntities += chunk.getTileEntities().length;
					} catch (IllegalStateException e) {
						continue;
					}
				}
				metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.player." + world.getName(),
						world.getPlayers().size(), time));

				metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.world_loadedchunk." + world.getName(),
						world.getLoadedChunks().length, time));
				metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.world_entitys." + world.getName(),
						world.getEntities().size(), time));
				metrics.add(new MackerelAPI.Metric(mackerelAPI, "custom.msw.world_tileentitys." + world.getName(),
						tileEntities, time));
			} catch (ClassCastException ex) {
				continue;
			} catch (NullPointerException ex) {
				continue;
			}
		}

		if (!mackerelAPI.postMetrics(metrics)) {
			Main.getJavaPlugin().getLogger().warning("postMetrics failed.");
		}
	}

	long getLogSize() {
		File latestLogFile = new File("./logs/latest.log");
		if (!latestLogFile.exists()) {
			return -1L;
		}
		return latestLogFile.length();
	}
}
