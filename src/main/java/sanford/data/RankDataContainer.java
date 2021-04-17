package sanford.data;

import org.jetbrains.annotations.NotNull;

public class RankDataContainer implements Comparable<RankDataContainer>{
    private String id;
    private int cost;
    private String name;
    private int tier;

    public RankDataContainer(String id, int cost, String name, int tier) {
        this.id = id;
        this.cost = cost;
        this.name = name;
        this.tier = tier;
    }
    public RankDataContainer(String toString){
        String[] values = toString.split(",");
        id = values[0].trim();
        cost = Integer.parseInt(values[1].trim());
        name = values[2].trim();
        tier = Integer.parseInt(values[3].trim());
    }

    public String getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public int compareTo(@NotNull RankDataContainer o) {
        return Integer.compare(tier, o.tier);
    }

    @Override
    public String toString() {
        return "RankDataContainer{" +
                "id='" + id + '\'' +
                ", cost=" + cost +
                ", name='" + name + '\'' +
                ", tier=" + tier +
                '}';
    }
}
