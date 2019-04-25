package Domain;

import java.sql.Date;

public class Campers {
    private int camperPlate;
    private String type;
    private String brand;
    private Date factoryDate;
    private int millage;
    public Campers(Integer CamperPlate, String Type, String Brand, Date FactoryDate, Integer Millage){
        this.camperPlate = CamperPlate;
        this.type = Type;
        this.brand = Brand;
        this.factoryDate = FactoryDate;
        this.millage = Millage;

    }
    public int getCamperPlate() {
        return camperPlate;
    }

    public void setCamperPlate(Integer camperPlate) {
        this.camperPlate = camperPlate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Date getFactoryDate() {
        return factoryDate;
    }

    public void setFactoryDate(Date factoryDate) {
        this.factoryDate = factoryDate;
    }

    public int getMillage() {
        return millage;
    }

    public void setMillage(Integer millage) {
        this.millage = millage;
    }
}
