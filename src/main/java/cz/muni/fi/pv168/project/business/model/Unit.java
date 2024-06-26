package cz.muni.fi.pv168.project.business.model;

public class Unit extends Entity {
    private String name;
    private Unit conversionUnit;
    private float conversionRatio;
    private String abbreviation;

    public String getAbbreviation() {
        return abbreviation;
    }

    public Unit(String guid, String name, Unit conversionUnit, float ratio, String abbreviation) {
        super(guid);
        this.name = name;
        this.conversionUnit = conversionUnit;
        this.conversionRatio = ratio;
        this.abbreviation = abbreviation;
    }

    public Unit(String name, Unit conversionUnit, float ratio, String abbreviation) {
        this.name = name;
        this.conversionUnit = conversionUnit;
        this.conversionRatio = ratio;
        this.abbreviation = abbreviation;
    }

    public Unit() {
        this.name = "";
        this.conversionUnit = null;
        this.conversionRatio = 0;
        this.abbreviation = "";
    }

    @Override
    public String getName() {
        return name;
    }

    public Unit getConversionUnit() {
        return conversionUnit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConversionUnit(Unit conversionUnit) {
        this.conversionUnit = conversionUnit;
    }

    public void setConversionRatio(float conversionRatio) {
        this.conversionRatio = conversionRatio;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public float getConversionRatio() {
        return conversionRatio;
    }

    @Override
    public String toString() {
        return getName();
    }

    public boolean isBaseUnit() {
        return conversionUnit == null;
    }
}
