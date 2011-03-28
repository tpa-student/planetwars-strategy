package com.tieto.planetwars.player.strategy;

import java.util.ArrayList;
import java.util.List;

import com.tieto.planetwars.player.Command;
import com.tieto.planetwars.player.Player;
import com.tieto.planetwars.player.Strategy;
import com.tieto.planetwars.world.Planet;
import com.tieto.planetwars.world.WarsMap;

public class SuperHiperStrategy implements Strategy {

	@Override
	public List<Command> doTurn(WarsMap gameMap, Player player) {
		
		Planet sourcePlanet = gameMap.getPlanets().get(0);
		Planet destinationPlanet= gameMap.getPlanets().get(1);
		int numShips = gameMap.getFleets().get(0).getNumberOfShips();
		Command command = new Command(sourcePlanet, destinationPlanet, numShips);
		List <Command> result = new ArrayList<Command>();
		result.add(command);
		return result;
	}

}
