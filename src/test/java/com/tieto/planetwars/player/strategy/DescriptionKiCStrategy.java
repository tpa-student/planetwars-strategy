package com.tieto.planetwars.player.strategy;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import com.tieto.planetwars.player.Command;
import com.tieto.planetwars.player.Player;
import com.tieto.planetwars.player.Strategy;
import com.tieto.planetwars.world.Coordinates;
import com.tieto.planetwars.world.Planet;
import com.tieto.planetwars.world.WarsMap;

public class DescriptionKiCStrategy {
	Strategy strategy = new KiCStrategy();
	static int counter = 0;

	public Planet makePlanet(int owner, int numberOfShips) {
		Player player = Mockito.mock(Player.class);
		when(player.getId()).thenReturn(owner);

		return new Planet(counter++, 0, player, numberOfShips, new Coordinates(
				counter * 10, counter * 10));
	}

	@Test
	public void should_have_method_doTurn() throws Exception {
		WarsMap warsMap = Mockito.mock(WarsMap.class);
		Player player = Mockito.mock(Player.class);
		strategy.doTurn(warsMap, player);
	}

	@Test
	public void doTurn_should_check_planets_in_world() throws Exception {
		// given
		WarsMap warsMap = Mockito.mock(WarsMap.class);
		List<Planet> planets = new ArrayList<Planet>();
		when(warsMap.getPlanets()).thenReturn(planets);

		// when
		strategy.doTurn(warsMap, null);

		// then
		verify(warsMap, times(2)).getPlanets();
	}

	@Test
	public void player_should_attack_first_weaker_neutral_planet()
			throws Exception {
		// given
		WarsMap warsMap = Mockito.mock(WarsMap.class);

		List<Planet> planets = new ArrayList<Planet>();
		planets.add(makePlanet(1, 100));
		planets.add(makePlanet(2, 100));
		planets.add(makePlanet(2, 100));
		planets.add(makePlanet(0, 200));
		planets.add(makePlanet(2, 200));
		Planet worst = makePlanet(0, 50);
		planets.add(worst);

		when(warsMap.getPlanets()).thenReturn(planets);

		Player player = Mockito.mock(Player.class);
		when(player.getId()).thenReturn(1);

		// when
		List<Command> commands = strategy.doTurn(warsMap, player);

		// then
		Assertions.assertThat(commands).isNotEmpty();

		Command firstCommand = commands.get(0);

		// then
		Assertions.assertThat(firstCommand.getDestinationPlanet()).isEqualTo(
				worst);
	}

}
