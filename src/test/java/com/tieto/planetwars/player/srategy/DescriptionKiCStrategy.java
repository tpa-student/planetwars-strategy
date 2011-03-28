package com.tieto.planetwars.player.srategy;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.tieto.planetwars.player.Player;
import com.tieto.planetwars.world.Planet;
import com.tieto.planetwars.world.WarsMap;

public class DescriptionKiCStrategy {
	KiCStrategy strategy = new KiCStrategy();

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
		verify(warsMap).getPlanets();

	}

}
