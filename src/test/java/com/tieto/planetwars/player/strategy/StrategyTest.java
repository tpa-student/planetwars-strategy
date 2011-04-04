package com.tieto.planetwars.player.strategy;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.tieto.planetwars.player.Command;
import com.tieto.planetwars.player.Player;
import com.tieto.planetwars.world.Planet;
import com.tieto.planetwars.world.WarsMap;


public class StrategyTest {
	
	@Test
	public void should_attack_weakest_opposite_player_planet_if_no_neutral_planets() throws Exception {
		{
			// given
			SimpleStrategy strategy = new SimpleStrategy();
			WarsMap gameMap = Mockito.mock(WarsMap.class);
			Player player   = Mockito.mock(Player.class);
			Player oppositePlayer = Mockito.mock(Player.class);
			List<Planet> planets = new ArrayList<Planet>();
			Planet planetToAttackStronger = Mockito.mock(Planet.class);
			Planet planetToAttackWeaker = Mockito.mock(Planet.class);
			Planet myStrongestPlanet = Mockito.mock(Planet.class);
		
			planets.add(planetToAttackStronger);
			planets.add(planetToAttackWeaker);
			planets.add(myStrongestPlanet);
			
			Mockito.when(gameMap.getPlanets()).thenReturn(planets);
			Mockito.when(player.getId()).thenReturn(1);	
			Mockito.when(oppositePlayer.getId()).thenReturn(2);	
			Mockito.when(myStrongestPlanet.getOwner()).thenReturn(player);
			Mockito.when(planetToAttackStronger.getOwner()).thenReturn(oppositePlayer);
			Mockito.when(planetToAttackStronger.getId()).thenReturn(2);
			Mockito.when(planetToAttackWeaker.getOwner()).thenReturn(oppositePlayer);
			Mockito.when(planetToAttackWeaker.getId()).thenReturn(3);			
			Mockito.when(myStrongestPlanet.getNumberOfShips()).thenReturn(20);
			Mockito.when(planetToAttackStronger.getNumberOfShips()).thenReturn(15);
			Mockito.when(planetToAttackWeaker.getNumberOfShips()).thenReturn(10);
			
			// when
			List<Command> doTurn = strategy.doTurn(gameMap, player);
			
			// then
			Command command = doTurn.get(0);
			assertThat(command.getSourcePlanet().getOwner().getId()).isEqualTo(player.getId());
			assertThat(command.getDestinationPlanet().getId()).isEqualTo(planetToAttackWeaker.getId());
			assertThat(command.getNumShips()).isEqualTo(myStrongestPlanet.getNumberOfShips());
		}
	}
	
	@Test
	public void should_attack_first_opposite_player_planet_if_no_neutral_planets() throws Exception {
		{
			// given
			SimpleStrategy strategy = new SimpleStrategy();
			WarsMap gameMap = Mockito.mock(WarsMap.class);
			Player player   = Mockito.mock(Player.class);
			Player oppositePlayer = Mockito.mock(Player.class);
			List<Planet> planets = new ArrayList<Planet>();
			Planet planetToAttack = Mockito.mock(Planet.class);
			Planet myStrongestPlanet = Mockito.mock(Planet.class);
		
			planets.add(planetToAttack);
			planets.add(myStrongestPlanet);
			
			Mockito.when(gameMap.getPlanets()).thenReturn(planets);
			Mockito.when(player.getId()).thenReturn(1);	
			Mockito.when(oppositePlayer.getId()).thenReturn(2);	
			Mockito.when(myStrongestPlanet.getOwner()).thenReturn(player);
			Mockito.when(planetToAttack.getOwner()).thenReturn(oppositePlayer);
			Mockito.when(planetToAttack.getId()).thenReturn(2);
			Mockito.when(myStrongestPlanet.getNumberOfShips()).thenReturn(20);
			Mockito.when(planetToAttack.getNumberOfShips()).thenReturn(10);
			
			// when
			List<Command> doTurn = strategy.doTurn(gameMap, player);
			
			// then
			Command command = doTurn.get(0);
			assertThat(command.getSourcePlanet().getOwner().getId()).isEqualTo(player.getId());
			assertThat(command.getDestinationPlanet().getId()).isEqualTo(planetToAttack.getId());
			assertThat(command.getNumShips()).isEqualTo(myStrongestPlanet.getNumberOfShips());
		}
	}
	
	@Test
	public void should_attack_first_neutral_planet() throws Exception {
		{
			// given
			SimpleStrategy strategy = new SimpleStrategy();
			WarsMap gameMap = Mockito.mock(WarsMap.class);
			Player player   = Mockito.mock(Player.class);
			List<Planet> planets = new ArrayList<Planet>();
			Planet planetToAttack = Mockito.mock(Planet.class);
			Planet myStrongestPlanet = Mockito.mock(Planet.class);
		
			planets.add(planetToAttack);
			planets.add(myStrongestPlanet);
			
			Mockito.when(gameMap.getPlanets()).thenReturn(planets);
			Mockito.when(player.getId()).thenReturn(1);			
			Mockito.when(myStrongestPlanet.getOwner()).thenReturn(player);
			Mockito.when(planetToAttack.getOwner()).thenReturn(null);
			Mockito.when(planetToAttack.getId()).thenReturn(2);
			Mockito.when(myStrongestPlanet.getNumberOfShips()).thenReturn(20);
			Mockito.when(planetToAttack.getNumberOfShips()).thenReturn(10);
			
			// when
			List<Command> doTurn = strategy.doTurn(gameMap, player);
			
			// then
			Command command = doTurn.get(0);
			assertThat(command.getSourcePlanet().getOwner().getId()).isEqualTo(player.getId());
			assertThat(command.getDestinationPlanet().getId()).isEqualTo(planetToAttack.getId());
			assertThat(command.getNumShips()).isEqualTo(myStrongestPlanet.getNumberOfShips());
		}
	}
	
	@Test
	public void should_attack_first_neutral_conquerable_planet_with_strongest_fleet() throws Exception {
		{
			// given
			SimpleStrategy strategy = new SimpleStrategy();
			WarsMap gameMap = Mockito.mock(WarsMap.class);
			Player player   = Mockito.mock(Player.class);
			List<Planet> planets = new ArrayList<Planet>();
			Planet planetToAttack = Mockito.mock(Planet.class);
			Planet myStrongestPlanet        = Mockito.mock(Planet.class);
			
			Planet otherPlanet     = Mockito.mock(Planet.class);
			Planet myWeakestPlanet = Mockito.mock(Planet.class);
	
			planets.add(planetToAttack);
			planets.add(otherPlanet);
			planets.add(myStrongestPlanet);
			planets.add(myWeakestPlanet);
			
			Mockito.when(gameMap.getPlanets()).thenReturn(planets);
			Mockito.when(player.getId()).thenReturn(1);			
			Mockito.when(myStrongestPlanet.getOwner()).thenReturn(player);
			Mockito.when(myWeakestPlanet.getOwner()).thenReturn(player);
			Mockito.when(otherPlanet.getOwner()).thenReturn(null);
			Mockito.when(planetToAttack.getOwner()).thenReturn(null);
			Mockito.when(planetToAttack.getId()).thenReturn(2);
			Mockito.when(myStrongestPlanet.getNumberOfShips()).thenReturn(20);
			Mockito.when(myWeakestPlanet.getNumberOfShips()).thenReturn(5);
			Mockito.when(planetToAttack.getNumberOfShips()).thenReturn(10);
			Mockito.when(otherPlanet.getNumberOfShips()).thenReturn(30);
			
			// when
			List<Command> doTurn = strategy.doTurn(gameMap, player);
			
			// then
			Command command = doTurn.get(0);
			assertThat(command.getSourcePlanet().getOwner().getId()).isEqualTo(player.getId());
			assertThat(command.getDestinationPlanet().getId()).isEqualTo(planetToAttack.getId());
			assertThat(command.getNumShips()).isEqualTo(myStrongestPlanet.getNumberOfShips());			
		}
	}
	
	@Test
	public void should_give_no_command_if_no_neutral_plannet() throws Exception {
		// given
		SimpleStrategy strategy = new SimpleStrategy();
		WarsMap gameMap = Mockito.mock(WarsMap.class);
		Player player   = Mockito.mock(Player.class);
		Player opPlayer = Mockito.mock(Player.class);
		List<Planet> planets = new ArrayList<Planet>();
		Planet planetToAttack = Mockito.mock(Planet.class);
		Planet myStrongestPlanet        = Mockito.mock(Planet.class);
		Planet otherPlanet     = Mockito.mock(Planet.class);
		
		planets.add(planetToAttack);
		planets.add(otherPlanet);
		planets.add(myStrongestPlanet);
				
		Mockito.when(gameMap.getPlanets()).thenReturn(planets);
		
		Mockito.when(myStrongestPlanet.getOwner()).thenReturn(player);
		Mockito.when(otherPlanet.getOwner()).thenReturn(player);
		Mockito.when(planetToAttack.getOwner()).thenReturn(player);
		Mockito.when(myStrongestPlanet.getNumberOfShips()).thenReturn(20);
		Mockito.when(planetToAttack.getNumberOfShips()).thenReturn(10);
		Mockito.when(otherPlanet.getNumberOfShips()).thenReturn(30);		
		
		// when
		List<Command> doTurn = strategy.doTurn(gameMap, player);
		
		// then
		assertThat(doTurn.isEmpty()).isTrue();			
	}
	
	@Test
	public void should_give_no_command_if_no_neutral_plannet_to_conquer() throws Exception {
		// given
		SimpleStrategy strategy = new SimpleStrategy();
		WarsMap gameMap = Mockito.mock(WarsMap.class);
		Player player   = Mockito.mock(Player.class);
		List<Planet> planets = new ArrayList<Planet>();
		Planet otherPlanet     = Mockito.mock(Planet.class);
		Planet planetToAttack = Mockito.mock(Planet.class);
		Planet myStrongestPlanet        = Mockito.mock(Planet.class);
		
		planets.add(planetToAttack);
		planets.add(otherPlanet);
		planets.add(myStrongestPlanet);
		
		Mockito.when(gameMap.getPlanets()).thenReturn(planets);
		Mockito.when(player.getId()).thenReturn(1);			
		Mockito.when(myStrongestPlanet.getOwner()).thenReturn(player);
		Mockito.when(otherPlanet.getOwner()).thenReturn(null);
		Mockito.when(planetToAttack.getOwner()).thenReturn(null);
		Mockito.when(planetToAttack.getId()).thenReturn(2);
		Mockito.when(myStrongestPlanet.getNumberOfShips()).thenReturn(20);
		Mockito.when(planetToAttack.getNumberOfShips()).thenReturn(30);
		Mockito.when(otherPlanet.getNumberOfShips()).thenReturn(30);
		
		// when
		List<Command> doTurn = strategy.doTurn(gameMap, player);
		
		// then
		assertThat(doTurn.isEmpty()).isTrue();			
	}
}
