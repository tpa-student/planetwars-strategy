package com.tieto.planetwars.player.strategy;

import java.util.ArrayList;
import java.util.List;

import com.tieto.planetwars.player.Command;
import com.tieto.planetwars.player.Player;
import com.tieto.planetwars.player.Strategy;
import com.tieto.planetwars.world.Planet;
import com.tieto.planetwars.world.WarsMap;

public class SuperHiperStrategy implements Strategy {

  @Override
  public List<Command> doTurn(WarsMap gameMap, Player player) {
    // musi byc co najmniej 11 :P
    int attackingFleetSize = 12; //= gameMap.getFleets().get(0).getNumberOfShips();
    Planet sourcePlanet = getStrongestPlayerPlanet(gameMap.getPlanets(), player);
    Planet playerPlanet = getPlanetOwnedByPlayer(gameMap.getPlanets(), player);
    Planet destinationPlanet = getClosestWeakerPlanet(gameMap.getPlanets(), player, playerPlanet, attackingFleetSize);

    Command command = new Command(sourcePlanet, destinationPlanet, attackingFleetSize);
    List<Command> result = new ArrayList<Command>();
    result.add(command);
    return result;
  }

  private Planet getStrongestPlayerPlanet(List<Planet> planets, Player player) {
    Planet strongestPlanet = getPlanetOwnedByPlayer(planets, player);

    for (Planet testPlanet : planets) {
      if (testPlanet.getOwner() != null && testPlanet.getOwner().equals(player)) {
        if (testPlanet.getNumberOfShips() > strongestPlanet.getNumberOfShips()) {
          strongestPlanet = testPlanet;
        }
      }
    }
    return strongestPlanet;
  }

  private Planet getPlanetOwnedByPlayer(List<Planet> planets, Player player) {
    for (Planet testPlanet : planets) {
      if (testPlanet.getOwner() != null
          && testPlanet.getOwner().equals(player)) {
        return testPlanet;
      }
    }
    return null;
  }

  private Planet getClosestWeakerPlanet(List<Planet> planets, Player player, Planet playerPlanet, int attackingFleetSize) {
    Planet closestPlanet = playerPlanet;
    double shortestDistance = Double.MAX_VALUE;

    for (Planet testPlanet : planets) {
      if ((testPlanet.getOwner() == null || !testPlanet.getOwner().equals(player)) && !closestPlanet.equals(testPlanet)) {

        double distance = testPlanet.getCoordinates().distance(closestPlanet.getCoordinates());
        if (distance < shortestDistance && attackingFleetSize > testPlanet.getNumberOfShips()) {
          shortestDistance = distance;
          closestPlanet = testPlanet;
        }
      }
    }
    if (closestPlanet.equals(playerPlanet)) {
      return null;
    }
    return closestPlanet;
  }

  private Planet getPlanetNotOwnedByPlayer(List<Planet> planets, Player player) {
    for (Planet testPlanet : planets) {
      if (testPlanet.getOwner() == null || !testPlanet.getOwner().equals(player)) {
        return testPlanet;
      }
    }
    return null;
  }
}
