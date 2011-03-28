package com.tieto.planetwars.player.strategy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.tieto.planetwars.player.Player;
import com.tieto.planetwars.world.Planet;
import com.tieto.planetwars.world.WarsMap;


public class StrategyTest {
	
	@Test
	public void should_attack_neutral_planet_with_max_seven_ships() throws Exception {
		//Given
		SimpleStrategy strategy = new SimpleStrategy();
		WarsMap gameMap = Mockito.mock(WarsMap.class);
		Player player   = Mockito.mock(Player.class);
		List<Planet> planets = new ArrayList<Planet>();
		Planet plantetToAttack = Mockito.mock(Planet.class);
		Planet otherPlanet     = Mockito.mock(Planet.class);
		Planet myPlanet        = Mockito.mock(Planet.class);
		planets.add(plantetToAttack);
		planets.add(otherPlanet);
		planets.add(myPlanet);
		
		Mockito.when(gameMap.getPlanets()).thenReturn(planets);
		Mockito.when(myPlanet.getOwner()).thenReturn(player);
		Mockito.when(otherPlanet.getOwner()).thenReturn(null);
		Mockito.when(plantetToAttack.getOwner()).thenReturn(null);
		
		// when
		strategy.doTurn(gameMap, player);
	}
}
