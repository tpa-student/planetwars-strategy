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
		
		for (int i = 0; i < planets.size(); ++i){
			Planet current = planets.get( i );
			if (current.getOwner() == null){
				neutralPlanets.add( current );
			}
			else if ( current.getOwner().equals( player )){
				myPlanets.add( current );
			}
		}
		
		List<Command> commands = new ArrayList<Command>();
		for ( int i = 0; i < neutralPlanets.size(); ++i ){
			Planet currentNeutral = neutralPlanets.get( i );
			for ( int j = 0; j < myPlanets.size(); ++j ){
				Planet currentMy = myPlanets.get( j );
				if ( currentNeutral.getNumberOfShips() < currentMy.getNumberOfShips() ){
					Command command = new Command( currentMy, currentNeutral,currentNeutral.getNumberOfShips() + 1 );
					commands.add( command );
					// exit loops!
					j = myPlanets.size();
					i = neutralPlanets.size();
				}
			}
		}
		
		return commands;
	}
}
