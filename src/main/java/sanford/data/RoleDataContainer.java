package sanford.data;

public class RoleDataContainer {
    private String id;
    private int cost;
    private String isMonthly;
    private String name;
    private int tier;

    public RoleDataContainer(String id, int cost, String isMonthly, String name, int tier) {
        this.id = id;
        this.cost = cost;
        this.isMonthly = isMonthly;
        this.name = name;
        this.tier = tier;
    }
    public RoleDataContainer(String row){
        String[] values = row.split(",");
        this.id = values[0].trim();
        this.cost = Integer.parseInt(values[1].trim());
        this.isMonthly = values[2].trim();
        this.name = values[3].trim();
        this.tier = Integer.parseInt(values[4].trim());
    }

    public String shopString(){
        String toString;
        toString = "**" + name + "**\t\t";
        if(cost != 0){
            toString += + cost + " points";
            if(isMonthly.equals("1")){
                toString += " per month";
            }
        }
        if(tier == -1){
            toString += "(Server Role)";
        }
        else if(tier == 0){
            toString += "(Color Role)";
        }
        else{
            toString += "(Rank " + tier +")";
        }
        return toString;
    }

    //getters
    public String getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public Boolean getIsMonthly() {
        return isMonthly.equals("1");
    }

    public int getTier() {
        return tier;
    }
}