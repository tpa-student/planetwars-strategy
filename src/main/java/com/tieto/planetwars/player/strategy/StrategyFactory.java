package com.tieto.planetwars.player.strategy;

import com.tieto.planetwars.player.Strategy;

public class StrategyFactory {
	public static Strategy createStrategy() {
		String implementationName = MyStrategy.class.getName();

		try {
			Class<?> loadClass = ClassLoader.getSystemClassLoader().loadClass(
					implementationName);
			return (Strategy) loadClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Can not create strategy");
		}
	}
}
