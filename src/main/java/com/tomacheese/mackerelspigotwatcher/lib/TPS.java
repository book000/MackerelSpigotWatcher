package com.tomacheese.mackerelspigotwatcher.lib;

import org.bukkit.Bukkit;

public class TPS {
	public static double getTPS1m() {
        return Math.min(Math.round(Bukkit.getTPS()[0] * 100.0) / 100.0, 20.0);
    }

	public static double getTPS5m() {
        return Math.min(Math.round(Bukkit.getTPS()[1] * 100.0) / 100.0, 20.0);
	}

	public static double getTPS15m() {
        return Math.min(Math.round(Bukkit.getTPS()[2] * 100.0) / 100.0, 20.0);
	}
}
