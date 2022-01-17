package com.tomacheese.mackerelspigotwatcher.lib;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;

public class TPS {
	static boolean firstSetting = false;

	public static double getTPS1m() {
		if (!firstSetting) {
			OnEnable_TPSSetting();
		}
		try {
			double[] tpsdouble = ((double[]) tpsField.get(serverInstance));
			if (tpsdouble[0] > 20.0) {
				return Math.min(Math.round(tpsdouble[0] * 100.0) / 100.0, 20.0);
			}
			return Math.min(Math.round(tpsdouble[0] * 100.0) / 100.0, 20.0);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static double getTPS5m() {
		if (!firstSetting) {
			OnEnable_TPSSetting();
		}
		try {
			double[] tpsdouble = ((double[]) tpsField.get(serverInstance));
			if (tpsdouble[1] > 20.0) {
				return Math.min(Math.round(tpsdouble[1] * 100.0) / 100.0, 20.0);
			}
			return Math.min(Math.round(tpsdouble[1] * 100.0) / 100.0, 20.0);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static double getTPS15m() {
		if (!firstSetting) {
			OnEnable_TPSSetting();
		}
		try {
			double[] tpsdouble = ((double[]) tpsField.get(serverInstance));
			if (tpsdouble[2] > 20.0) {
				return Math.min(Math.round(tpsdouble[2] * 100.0) / 100.0, 20.0);
			}
			return Math.min(Math.round(tpsdouble[2] * 100.0) / 100.0, 20.0);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private final static String name = Bukkit.getServer().getClass().getPackage().getName();
	private final static String version = name.substring(name.lastIndexOf('.') + 1);

	private static Object serverInstance;
	private static Field tpsField;

	private static Class<?> getNMSClass() {
		try {
			return Class.forName("net.minecraft.server.%s.MinecraftServer".formatted(version));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static void OnEnable_TPSSetting() {
		try {
			serverInstance = getNMSClass().getMethod("getServer").invoke(null);
			tpsField = serverInstance.getClass().getField("recentTps");
			firstSetting = true;
		} catch (NoSuchFieldException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
}
