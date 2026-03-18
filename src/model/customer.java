package model;

public class customer {
    private int id;
    private String costumerName;
    private String dui; // Format: 00000000-0
    private String phone;

    public customer() {}

    public customer(int id, String costumerName, String dui, String phone) {
        this.id = id;
        this.costumerName = costumerName;
        this.dui = dui;
        this.phone = phone;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getcostumerName() { return costumerName; }
    public void setcostumerName(String costumerName) { this.costumerName = costumerName; }

    public String getDui() { return dui; }
    public void setDui(String dui) { this.dui = dui; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return id + " | " + costumerName + " | DUI: " + dui + " | Tel: " + phone;
    }

}
