package com.tieto.planetwars.player.strategy;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.tieto.planetwars.player.Command;
import com.tieto.planetwars.player.Player;
import com.tieto.planetwars.player.Strategy;
import com.tieto.planetwars.world.Fleet;
import com.tieto.planetwars.world.Planet;
import com.tieto.planetwars.world.WarsMap;

public class TestStrategy {

	private Strategy myStrategy;
	private List<Planet> planets;
	private List<Fleet> fleets;
	private Player player;
	private Planet playersPlanet;
	private Planet neutralPlanet;

	@Before
	public void set_up() {	
		myStrategy = new MyStrategy();
		
		playersPlanet = Mockito.mock(Planet.class);
		neutralPlanet = Mockito.mock(Planet.class);
		planets = new ArrayList<Planet>();
		planets.add(playersPlanet);
		planets.add(neutralPlanet);
		
		fleets = new ArrayList<Fleet>();
		player = Mockito.mock(Player.class);
		
		Mockito.when(playersPlanet.getNumberOfShips()).thenReturn( 10 );
		Mockito.when(playersPlanet.getOwner()).thenReturn( player );
		Mockito.when(neutralPlanet.getNumberOfShips()).thenReturn( 7 );
		Mockito.when(neutralPlanet.getOwner()).thenReturn( null ); 
	}
	
	@Test
	public void attack_neutral() {
		//given 			
		WarsMap warsMap = new WarsMap(planets, fleets);
		
		// when
		List<Command> commands = myStrategy.doTurn(warsMap, player);
		
		// then 
		Assertions.assertThat(commands.size()).isEqualTo( 1 );
		Command returnedCommand = commands.get( 0 );
		
		Assertions.assertThat( returnedCommand.getNumShips() ).isEqualTo( neutralPlanet.getNumberOfShips() + 1 );
		Assertions.assertThat( returnedCommand.getDestinationPlanet() ).isEqualTo( neutralPlanet );
		Assertions.assertThat( returnedCommand.getSourcePlanet() ).isEqualTo( playersPlanet ); 
	}
	
	@Test
	public void attack_only_one_neutral_which_has_less_ships_than_any_of_my_planets() throws Exception {
		// given
		Planet anotherNeutral = Mockito.mock( Planet.class );
		Mockito.when( anotherNeutral.getOwner()).thenReturn( null );
		Mockito.when( anotherNeutral.getNumberOfShips() ).thenReturn( 5 );
		planets.add( anotherNeutral );
		
		WarsMap warsMap = new WarsMap( planets, fleets );
		
		// when
		List<Command> commands = myStrategy.doTurn( warsMap, player );
		
		// then
		Assertions.assertThat(commands.size()).isEqualTo( 1 );
		Command returnedCommand = commands.get( 0 );
		
		Assertions.assertThat( returnedCommand.getNumShips() ).isEqualTo( neutralPlanet.getNumberOfShips() + 1 );
		Assertions.assertThat( returnedCommand.getDestinationPlanet() ).isEqualTo( neutralPlanet );
		Assertions.assertThat( returnedCommand.getSourcePlanet() ).isEqualTo( playersPlanet );
	}
	
	@Test
	public void attack_first_neutral_which_has_less_ships_than_any_of_my_planets() throws Exception {
		// given
		Mockito.when( neutralPlanet.getNumberOfShips() ).thenReturn( 11 );
		Planet anotherNeutral = Mockito.mock( Planet.class );
		Mockito.when( anotherNeutral.getOwner()).thenReturn( null );
		Mockito.when( anotherNeutral.getNumberOfShips() ).thenReturn( 5 );
		planets.add( anotherNeutral );
		
		WarsMap warsMap = new WarsMap( planets, fleets );
		
		// when
		List<Command> commands = myStrategy.doTurn( warsMap, player );
		
		// then
		Assertions.assertThat(commands.size()).isEqualTo( 1 );
		Command returnedCommand = commands.get( 0 );
		
		Assertions.assertThat( returnedCommand.getNumShips() ).isEqualTo( anotherNeutral.getNumberOfShips() + 1 );
		Assertions.assertThat( returnedCommand.getDestinationPlanet() ).isEqualTo( anotherNeutral );
		Assertions.assertThat( returnedCommand.getSourcePlanet() ).isEqualTo( playersPlanet );
	}
	
	@Test
	public void attack_only_from_planet_with_sufficient_forces() throws Exception {
		// given
		Mockito.when( neutralPlanet.getNumberOfShips() ).thenReturn( 20 );
		Planet anotherMyPlanet = Mockito.mock( Planet.class );
		Mockito.when( anotherMyPlanet.getOwner() ).thenReturn(player);
		Mockito.when( anotherMyPlanet.getNumberOfShips() ).thenReturn( 25 );
		planets.add( anotherMyPlanet );
		WarsMap warsMap = new WarsMap( planets, fleets );
		
		// when
		List<Command> commands = myStrategy.doTurn( warsMap, player );
		
		// then
		Assertions.assertThat(commands.size()).isEqualTo( 1 );
		Command returnedCommand = commands.get( 0 );
		
		Assertions.assertThat( returnedCommand.getNumShips() ).isEqualTo( neutralPlanet.getNumberOfShips() + 1 );
		Assertions.assertThat( returnedCommand.getDestinationPlanet() ).isEqualTo( neutralPlanet );
		Assertions.assertThat( returnedCommand.getSourcePlanet() ).isEqualTo( anotherMyPlanet );
		
	}
	
	@Test
	public void no_commands_if_no_neutral_planets() throws Exception {
		// given
		planets.remove( neutralPlanet );
		Player opponent = Mockito.mock( Player.class );
		Planet other    = Mockito.mock( Planet.class );
		Mockito.when( other.getOwner() ).thenReturn( opponent );
		planets.add( other );
		WarsMap warsMap = new WarsMap(planets, fleets);
		
		// when 
		List<Command> commands = myStrategy.doTurn(warsMap, player);
		
		// then
		Assertions.assertThat(commands.isEmpty()).isTrue();
	}
	
	@Test
	public void no_commands_if_there_are_no_sufficient_force_in_a_single_planet_to_conquer_neutral_planet() throws Exception {
		// given
		Mockito.when( neutralPlanet.getNumberOfShips() ).thenReturn( 20 );
		WarsMap warsMap = new WarsMap(planets, fleets);
		
		// when
		List<Command> commands = myStrategy.doTurn(warsMap, player);
		
		// then
		Assertions.assertThat(commands.isEmpty()).isTrue();
	}
}
