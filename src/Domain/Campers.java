package Domain;

import java.sql.Date;

public class Campers {
    private String camperPlate;
    private String type;
    private String brand;
    private Date factoryDate;
    private Long millage;
    public Campers(String CamperPlate, String Type, String Brand, Date FactoryDate,Long Millage){
        this.camperPlate = CamperPlate;
        this.type = Type;
        this.brand = Brand;
        this.factoryDate = FactoryDate;
        this.millage = Millage;

    }

    public String getCamperPlate() {
        return camperPlate;
    }

    public void setCamperPlate(String camperPlate) {
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

    public Long getMillage() {
        return millage;
    }

    public void setMillage(Long millage) {
        this.millage = millage;
    }
}
