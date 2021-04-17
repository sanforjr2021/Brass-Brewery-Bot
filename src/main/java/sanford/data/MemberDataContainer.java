package sanford.data;

public class MemberDataContainer {
    private final String id;
    private int currency;

    public MemberDataContainer(String id, int currency) {
        this.id = id;
        this.currency = currency;
    }

    public MemberDataContainer(String row) {
        String[] values = row.split(",");
        this.id = values[0].trim();
        try{
            this.currency = Integer.parseInt(values[1].trim());
        }
        catch(ArrayIndexOutOfBoundsException e){
            this.currency = 0;
        }

    }

    public String getId() {
        return id;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
        System.out.println(id + "'s currency set to " + this.currency);
    }

    public String toString() {
        return id + " has a currency value of " + currency;
    }
}
