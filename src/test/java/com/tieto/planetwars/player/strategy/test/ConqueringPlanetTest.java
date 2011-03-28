package com.tieto.planetwars.player.strategy.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.tieto.planetwars.player.Command;
import com.tieto.planetwars.player.Player;
import com.tieto.planetwars.player.Strategy;
import com.tieto.planetwars.player.strategy.SuperHiperStrategy;
import com.tieto.planetwars.world.Coordinates;
import com.tieto.planetwars.world.Fleet;
import com.tieto.planetwars.world.Planet;
import com.tieto.planetwars.world.WarsMap;

public class ConqueringPlanetTest {

	@Test
	public void should_conquer_if_attacked_with_more_ships() {

		Planet preyPlanet = new Planet(1, 5, null, 10, new Coordinates(1, 1));
		Player conqueror = mock(Player.class);
		Planet sourcePlanet = new Planet(2, 5, conqueror, 20, new Coordinates(
				2, 2));
		Fleet fleet = new Fleet(conqueror, 15, sourcePlanet, preyPlanet);

		Strategy strategy = new SuperHiperStrategy();
		List<Planet> planets = mock(ArrayList.class);
		List<Fleet> fleets = mock(ArrayList.class);
		when(fleets.get(0)).thenReturn(fleet);
		when(planets.get(0)).thenReturn(sourcePlanet);
		when(planets.get(1)).thenReturn(preyPlanet);

		WarsMap gameMap = new WarsMap(planets, fleets);
		List<Command> commands = strategy.doTurn(gameMap, conqueror);

		assertThat(commands.isEmpty(), is(false));
		Command attackCommand = commands.get(0);
		assertThat(attackCommand.getDestinationPlanet(), is(preyPlanet));
		assertThat(attackCommand.getSourcePlanet(), is(sourcePlanet));
		assertThat(attackCommand.getNumShips(), is(fleet.getNumberOfShips()));
	}
}
