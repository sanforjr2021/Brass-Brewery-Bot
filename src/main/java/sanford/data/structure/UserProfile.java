package sanford.data.structure;


import org.json.simple.JSONObject;

public class UserProfile {
    private String id;
    private int currency;
    private Boolean autoRenew, autoUpgrade;

    public UserProfile(String id, int currency, Boolean autoRenew, Boolean autoUpgrade) {
        this.id = id;
        this.currency = currency;
        this.autoRenew = autoRenew;
        this.autoUpgrade = autoUpgrade;
    }

    public UserProfile(String id) {
        this.id = id;
        this.currency = 0;
        this.autoRenew = true;
        this.autoUpgrade = true;
    }


    public Boolean getAutoRenew() {
        return autoRenew;
    }

    public void setAutoRenew(Boolean autoRenew) {
        this.autoRenew = autoRenew;
    }

    public Boolean getAutoUpgrade() {
        return autoUpgrade;
    }

    public void setAutoUpgrade(Boolean autoUpgrade) {
        this.autoUpgrade = autoUpgrade;
    }

    public int getCurrency() {
        return currency;
    }

    public String getId() {
        return id;
    }

}
