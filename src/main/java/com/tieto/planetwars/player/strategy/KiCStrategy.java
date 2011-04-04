package com.tieto.planetwars.player.strategy;

import java.util.ArrayList;
import java.util.List;

import com.tieto.planetwars.player.Command;
import com.tieto.planetwars.player.Player;
import com.tieto.planetwars.player.Strategy;
import com.tieto.planetwars.world.Planet;
import com.tieto.planetwars.world.WarsMap;

public class KiCStrategy implements Strategy {

	public List<Command> doTurn(WarsMap gameMap, Player player) {
		List<Planet> planets = gameMap.getPlanets();

		List<Command> commands = new ArrayList<Command>();

		Planet sourcePlanet = chooseBestSourcePlanetOwnetByPlayer(gameMap,
				player);

		if (sourcePlanet != null) {
			if (thereAreNeutralPlanets(planets)) {
				Planet firstWeakerPlanet = null;

				firstWeakerPlanet = findFirstWeakerNeutralPlanet(planets,
						sourcePlanet);

				int ships = firstWeakerPlanet.getNumberOfShips() + 1;

				if (firstWeakerPlanet != null)
					commands.add(new Command(sourcePlanet, firstWeakerPlanet, 1));
			} else {
				Planet weakestOpponentPlanet = findWeakestOpponentPlanet(
						planets, sourcePlanet);

				int ships = weakestOpponentPlanet.getNumberOfShips() + 1;
				// if (weakestOpponentPlanet != null)
				// commands.add(new Command(sourcePlanet,
				// weakestOpponentPlanet, ships));
			}
		}
		return commands;
	}

	private Planet findWeakestOpponentPlanet(List<Planet> planets,
			Planet sourcePlanet) {
		Planet weakestOpponentPlanet = null;

		for (Planet planet : planets) {
			if (planet.getOwner().getId() == 2) {
				if (planet.getNumberOfShips() < sourcePlanet.getNumberOfShips()) {
					if (weakestOpponentPlanet == null) {
						weakestOpponentPlanet = planet;
					} else {
						if (weakestOpponentPlanet.getNumberOfShips() > planet
								.getNumberOfShips())
							weakestOpponentPlanet = planet;
					}
				}
			}
		}

		return weakestOpponentPlanet;
	}

	private boolean thereAreNeutralPlanets(List<Planet> planets) {
		for (Planet planet : planets) {
			if (planet.getOwner() == null) {
				return true;
			}
		}
		return false;
	}

	private Planet findFirstWeakerNeutralPlanet(List<Planet> planets,
			Planet sourcePlanet) {

		for (Planet planet : planets) {
			Integer numberOfShipsOnPlanet = planet.getNumberOfShips();
			if (planet.getOwner() == null
					&& sourcePlanet.getNumberOfShips() > numberOfShipsOnPlanet) {
				return planet;
			}
		}
		return null;
	}

	private Planet chooseBestSourcePlanetOwnetByPlayer(WarsMap gameMap,
			Player player) {
		Planet sourcePlanet = null;

		for (Planet planet : gameMap.getPlanets()) {
			if (planet.getOwner() != null
					&& planet.getOwner().getId() == player.getId()) {
				if (sourcePlanet == null) {
					sourcePlanet = planet;
				} else {
					if (sourcePlanet.getNumberOfShips() < planet
							.getNumberOfShips()) {
						sourcePlanet = planet;
					}
				}
			}
		}
		return sourcePlanet;
	}
}
