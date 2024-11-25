package BlockingAgents;

public class Predator extends Animal{
    int strength;
    List<Territory> territory;
    World world;
    Predator(int strength ){
        this. strength = strength;

        Set<Location> surroundingTiles = world.getSurroundingTiles(world.getLocation(this));
        for (int i = 0; i < surroundingTiles.toArray().length; i++) {
           territory.add(new Territory(world.getLocation(surroundingTiles.toArray()), world ,this));
           world.setTile(world.getLocation(surroundingTiles.toArray()), territory.get(i));
        }
    }
    void hunt (){


    }
    void fight (){}


}
