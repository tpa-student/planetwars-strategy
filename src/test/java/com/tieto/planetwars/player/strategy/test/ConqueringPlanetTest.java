package com.tieto.planetwars.player.strategy.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.tieto.planetwars.player.Command;
import com.tieto.planetwars.player.Player;
import com.tieto.planetwars.player.Strategy;
import com.tieto.planetwars.player.strategy.SuperHiperStrategy;
import com.tieto.planetwars.world.Coordinates;
import com.tieto.planetwars.world.Fleet;
import com.tieto.planetwars.world.Planet;
import com.tieto.planetwars.world.WarsMap;

public class ConqueringPlanetTest {

  private int sourcePlanetFleetSize;
  private int neutralPlanetFleetSize;
  private int defaultGrowth;
  private Planet neutralPlanets[];
  private Player conqueror;
  private Planet playerPlanet;
  private Strategy strategy;
  private List<Planet> planets;
  private List<Fleet> fleets;
  private WarsMap gameMap;

  @Before
  public void setup() {
    defaultGrowth = 5;
    conqueror = mock(Player.class);
    strategy = new SuperHiperStrategy();
  }

  @Test
  public void should_conquer_weaker_neutral_planet_if_only_one_present() {
    // given
    sourcePlanetFleetSize = 20;
    neutralPlanetFleetSize = 10;

    neutralPlanets = new Planet[] {
        new Planet(1, defaultGrowth, null, 1 * neutralPlanetFleetSize, new Coordinates(15, 15)),
        new Planet(3, defaultGrowth, null, 5 * neutralPlanetFleetSize, new Coordinates(3, 3)),
    };

    playerPlanet = new Planet(2, defaultGrowth, conqueror, sourcePlanetFleetSize, new Coordinates(0, 0));
    planets = Lists.newArrayList(playerPlanet);

    Planet neutralPlanet = neutralPlanets[0];
    planets.add(neutralPlanet);

    gameMap = new WarsMap(planets, fleets);

    // when
    List<Command> commands = strategy.doTurn(gameMap, conqueror);

    // then
    Command attackCommand = commands.get(0);
    assertThat(attackCommand.getDestinationPlanet(), is(neutralPlanet));
    assertThat(attackCommand.getSourcePlanet(), is(playerPlanet));
  }

  @Test
  public void should_attack_nearest_weaker_neutral_planet() {
    // given
    neutralPlanetFleetSize = 10;
    sourcePlanetFleetSize = neutralPlanetFleetSize + 1;

    neutralPlanets = new Planet[] {
        new Planet(1, defaultGrowth, null, neutralPlanetFleetSize, new Coordinates(3, 3)),
        new Planet(4, defaultGrowth, null, 10 * neutralPlanetFleetSize, new Coordinates(2, 2)),
        };

    playerPlanet = new Planet(42, defaultGrowth, conqueror, sourcePlanetFleetSize, new Coordinates(0, 0));
    planets = Lists.newArrayList(playerPlanet);
    for (Planet planet : neutralPlanets) {
      planets.add(planet);
    }

    gameMap = new WarsMap(planets, fleets);

    // when
    List<Command> commands = strategy.doTurn(gameMap, conqueror);

    // then
    Command attackCommand = commands.get(0);
    assertThat(attackCommand.getDestinationPlanet(), is(neutralPlanets[0]));
    assertThat(attackCommand.getSourcePlanet(), is(playerPlanet));
  }

  @Test
  public void should_attack_nearest_weaker_neutral_planet_using_strongest_player_planet() {
    // given
    neutralPlanetFleetSize = 10;
    sourcePlanetFleetSize = neutralPlanetFleetSize + 1;

    neutralPlanets = new Planet[] {
        new Planet(1, defaultGrowth, null, neutralPlanetFleetSize, new Coordinates(3, 3)),
        new Planet(2, defaultGrowth, null, 10 * neutralPlanetFleetSize, new Coordinates(2, 2)),
        };

    Planet strongetsPlayerPlanet = new Planet(101, defaultGrowth, conqueror, 2 * sourcePlanetFleetSize, new Coordinates(1, 1));
    planets = Lists.newArrayList(
        new Planet(100, defaultGrowth, conqueror, sourcePlanetFleetSize, new Coordinates(0, 0)),
        strongetsPlayerPlanet
        );
    for (Planet planet : neutralPlanets) {
      planets.add(planet);
    }

    gameMap = new WarsMap(planets, fleets);

    // when
    List<Command> commands = strategy.doTurn(gameMap, conqueror);

    // then
    Command attackCommand = commands.get(0);
    assertThat(attackCommand.getDestinationPlanet(), is(neutralPlanets[0]));
    assertThat(attackCommand.getSourcePlanet(), is(strongetsPlayerPlanet));
  }
  
  @Test
  public void should_attack_nearest_weaker_enemy_planet_using_strongest_player_planet() {
    // given
    neutralPlanetFleetSize = 10;
    sourcePlanetFleetSize = neutralPlanetFleetSize + 1;

    Player enemy = mock(Player.class);
    neutralPlanets = new Planet[] {
        new Planet(1, defaultGrowth, enemy, neutralPlanetFleetSize, new Coordinates(3, 3)),
        new Planet(2, defaultGrowth, enemy, 10 * neutralPlanetFleetSize, new Coordinates(2, 2)),
        };

    Planet strongetsPlayerPlanet = new Planet(101, defaultGrowth, conqueror, 2 * sourcePlanetFleetSize, new Coordinates(1, 1));
    planets = Lists.newArrayList(
        new Planet(100, defaultGrowth, conqueror, sourcePlanetFleetSize, new Coordinates(0, 0)),
        strongetsPlayerPlanet
        );
    for (Planet planet : neutralPlanets) {
      planets.add(planet);
    }

    gameMap = new WarsMap(planets, fleets);

    // when
    List<Command> commands = strategy.doTurn(gameMap, conqueror);

    // then
    Command attackCommand = commands.get(0);
    assertThat(attackCommand.getDestinationPlanet(), is(neutralPlanets[0]));
    assertThat(attackCommand.getSourcePlanet(), is(strongetsPlayerPlanet));
  }
}
