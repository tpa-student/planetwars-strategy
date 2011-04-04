package com.tieto.planetwars.player.strategy;

import java.util.ArrayList;
import java.util.List;

import com.tieto.planetwars.player.Command;
import com.tieto.planetwars.player.Player;
import com.tieto.planetwars.player.Strategy;
import com.tieto.planetwars.world.Planet;
import com.tieto.planetwars.world.WarsMap;

public class MyStrategy implements Strategy {

	@Override
	public List<Command> doTurn(WarsMap gameMap, Player player) {
		List<Planet> planets = gameMap.getPlanets();

		List<Planet> myPlanets = new ArrayList<Planet>();
		List<Planet> neutralPlanets = new ArrayList<Planet>();
		List<Planet> enemyPlanets = new ArrayList<Planet>();
		
		splitPlanets(player, planets, myPlanets, neutralPlanets, enemyPlanets);

		Planet strongestPlanet = getStrongestPlanet(myPlanets);

		List<Command> commands = new ArrayList<Command>();
		Command command = attackFirstPlanet(strongestPlanet, neutralPlanets);
		if (command != null) {
			commands.add(command);
		}else{
			Planet weakestEnemyPlanet = enemyPlanets.get( 0 );
			for(int i = 1; i < enemyPlanets.size(); ++i ){
				Planet current = enemyPlanets.get( i );
				if( weakestEnemyPlanet.getNumberOfShips() > current.getNumberOfShips()){
					weakestEnemyPlanet = current;
				}
			}
			if ( weakestEnemyPlanet.getNumberOfShips() < strongestPlanet.getNumberOfShips()){
				commands.add( new Command(strongestPlanet, weakestEnemyPlanet, weakestEnemyPlanet.getNumberOfShips() + 1));
			}
		}

		return commands;
	}

	private Planet getStrongestPlanet(List<Planet> myPlanets) {
		Planet strongestPlanet = myPlanets.get(0);
		for (int i = 1; i < myPlanets.size(); ++i) {
			Planet current = myPlanets.get(i);
			if (strongestPlanet.getNumberOfShips() < current.getNumberOfShips()) {
				strongestPlanet = current;
			}
		}
		return strongestPlanet;
	}

	private void splitPlanets(Player player, List<Planet> planets,
			List<Planet> myPlanets, List<Planet> neutralPlanets, List<Planet> enemyPlanets) {
		for (int i = 0; i < planets.size(); ++i) {
			Planet current = planets.get(i);
			if (current.getOwner() == null) {
				neutralPlanets.add(current);
			} else if (current.getOwner().getId() == player.getId()) {
				myPlanets.add(current);
			} else {
				enemyPlanets.add( current );
			}
		}
	}

	private Command attackFirstPlanet(Planet source,
			List<Planet> destinations) {
		for (int i = 0; i < destinations.size(); ++i) {
			Planet currentNeutral = destinations.get(i);

			if (currentNeutral.getNumberOfShips() < source.getNumberOfShips()) {
				Command command = new Command(source, currentNeutral,
						currentNeutral.getNumberOfShips() + 1);
				return command;
			}
		}

		return null;
	}
}
