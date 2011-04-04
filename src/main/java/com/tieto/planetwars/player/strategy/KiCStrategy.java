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

		Planet sourcePlanet = chooseFirstSourcePlanetOwnetByPlayer(gameMap,
				player);

		if (sourcePlanet != null) {
			for (Planet planet : planets) {
				int ships = 0;
				Integer numberOfShipsOnPlanet = planet.getNumberOfShips();
				if (sourcePlanet.getNumberOfShips() > numberOfShipsOnPlanet) {
					ships = numberOfShipsOnPlanet + 1;
					commands.add(new Command(sourcePlanet, planet, ships));
					break;
				}
			}
		}

		return commands;
	}

	private Planet chooseFirstSourcePlanetOwnetByPlayer(WarsMap gameMap,
			Player player) {
		Planet sourcePlanet = null;
		for (Planet planet : gameMap.getPlanets()) {
			if (planet.getOwner() != null
					&& planet.getOwner().getId() == player.getId()) {
				sourcePlanet = planet;
				break;
			}

		}
		return sourcePlanet;
	}

}
