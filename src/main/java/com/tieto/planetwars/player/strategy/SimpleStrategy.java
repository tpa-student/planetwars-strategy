package com.tieto.planetwars.player.strategy;

import java.util.ArrayList;
import java.util.List;

import com.tieto.planetwars.player.Command;
import com.tieto.planetwars.player.Player;
import com.tieto.planetwars.player.Strategy;
import com.tieto.planetwars.world.Planet;
import com.tieto.planetwars.world.WarsMap;

public class SimpleStrategy implements Strategy{

	@Override
	public List<Command> doTurn(WarsMap gameMap, Player player) {
		Planet planetToAttack = null;
		Planet sourcePlanet = null;
		
		sourcePlanet = findStrongestPlayerPlanet(gameMap, player, sourcePlanet);
		
		planetToAttack = findNeutralPlanetToAttack(gameMap, planetToAttack,
				sourcePlanet);
		
		if(planetToAttack == null) {
			planetToAttack = findOpponentPlanetToAttack(gameMap, planetToAttack, sourcePlanet);
		}
		
		List<Command> commands = new ArrayList<Command>();
		
		if(sourcePlanet != null && planetToAttack != null && sourcePlanet.getNumberOfShips() > 0) {
			Command command = new Command(sourcePlanet, planetToAttack, sourcePlanet.getNumberOfShips());
			commands.add(command);
		}
		
		return commands;
	}

	private Planet findOpponentPlanetToAttack(WarsMap gameMap,
			Planet planetToAttack, Planet sourcePlanet) {
		int minShipsOnPlanet = 1000;
		for(Planet pl : gameMap.getPlanets()) {
			if(pl.getOwner() != null && pl.getNumberOfShips() < sourcePlanet.getNumberOfShips() &&
					pl.getOwner().getId() != sourcePlanet.getOwner().getId() && pl.getNumberOfShips() < minShipsOnPlanet)
			{
				planetToAttack = pl;
				minShipsOnPlanet = sourcePlanet.getNumberOfShips();	
			}
		}
		return planetToAttack;
	}

	private Planet findNeutralPlanetToAttack(WarsMap gameMap,
			Planet planetToAttack, Planet sourcePlanet) {
		for(Planet pl : gameMap.getPlanets()) {
			if(pl.getOwner() == null && pl.getNumberOfShips() < sourcePlanet.getNumberOfShips()) {
				planetToAttack = pl;
			}
		}
		return planetToAttack;
	}

	private Planet findStrongestPlayerPlanet(WarsMap gameMap, Player player,
			Planet sourcePlanet) {
		
		int maxShipsOnPlanet = 0;
		for(Planet pl : gameMap.getPlanets()) {
			if (pl.getOwner() != null && pl.getOwner().getId() == player.getId() && pl.getNumberOfShips() > maxShipsOnPlanet) {
				sourcePlanet = pl;
				maxShipsOnPlanet = sourcePlanet.getNumberOfShips();
			}
		}
		
		return sourcePlanet;
	}
	
}
