package sanford.data;

public class MemberDataContainer {
    private String id;
    private int currency;

    public MemberDataContainer(String id, int currency) {
        this.id = id;
        this.currency = currency;
    }
    public MemberDataContainer(String row){
        String[] values = row.split(",");
        this.id = values[0].trim();
        this.currency = Integer.parseInt(values[1].trim());
    }
    public String getId() {
        return id;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }
    public void addValueToCurrency(int value){
        this.currency += value;
    }
    public String toString(){
        return id + " has a currency value of " + currency;
    }
}
