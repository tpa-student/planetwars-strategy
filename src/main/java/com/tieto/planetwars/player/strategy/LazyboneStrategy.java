package com.tieto.planetwars.player.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.tieto.planetwars.player.Command;
import com.tieto.planetwars.player.Player;
import com.tieto.planetwars.player.Strategy;
import com.tieto.planetwars.world.Planet;
import com.tieto.planetwars.world.WarsMap;

public class LazyboneStrategy implements Strategy {

	@Override
	public List<Command> doTurn(WarsMap gameMap, Player player) {
		List<Command> arrayList = new ArrayList<Command>();

		Planet sourcePlanet = chooseFirstSourcePlanetOwnetByPlayer(gameMap,
				player);

		if (sourcePlanet != null) {
			Random random = new Random();
			int nextInt = random.nextInt(gameMap.getPlanets().size());
			Planet destPlanet = gameMap.getPlanets().get(nextInt);
			int ships = 0;
			if (sourcePlanet.getNumberOfShips() / 2 > destPlanet
					.getNumberOfShips()) {
				ships = sourcePlanet.getNumberOfShips() / 2;
				arrayList.add(new Command(sourcePlanet, destPlanet, ships));
			} else {
				ships = sourcePlanet.getNumberOfShips() - 5;
				if (sourcePlanet.getNumberOfShips() - 5 > destPlanet
						.getNumberOfShips())
					arrayList.add(new Command(sourcePlanet, destPlanet, ships));
			}
		}

		return arrayList;
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
