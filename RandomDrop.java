package net.minenite.warzairdrops;

public class RandomDrop
{
    public String dropName;
    public int dropProbability;

    public RandomDrop(String dropName, int probability) {
        this.dropName = dropName;
        this.dropProbability = probability;
    }
}
