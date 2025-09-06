package com.GTAD.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "accident_data")
public class AccidentData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long uident;

    private Short land;
    private Integer region;
    private Integer district;
    private Integer munincipality;

    private Short year;
    private Short day;
    private Short hour;
    private Short month;

    private Byte category;
    private Byte kind;
    private Byte type;
    private Byte lightCondition;

    private Boolean bycicleInvolved;
    private Boolean carInvolved;
    private Boolean passengerInvolved;
    private Boolean motorcycleInvolved;
    private Boolean deliveryVanInvolved;
    private Boolean truckBusOrTramInvolved;

    private Byte roadSurfaceCondition;

    private Float coordinateUtmX;
    private Float coordinateUtmY;
    private Float longitude;
    private Float latitude;

    // getters + setters
    public Long getUident() {
    return uident;
}

public void setUident(Long uident) {
    this.uident = uident;
}

public Short getLand() {
    return land;
}

public void setLand(Short land) {
    this.land = land;
}

public Integer getRegion() {
    return region;
}

public void setRegion(Integer region) {
    this.region = region;
}

public Integer getDistrict() {
    return district;
}

public void setDistrict(Integer district) {
    this.district = district;
}

public Integer getMunincipality() {
    return munincipality;
}

public void setMunincipality(Integer munincipality) {
    this.munincipality = munincipality;
}

public Short getYear() {
    return year;
}

public void setYear(Short year) {
    this.year = year;
}

public Short getDay() {
    return day;
}

public void setDay(Short day) {
    this.day = day;
}

public Short getHour() {
    return hour;
}

public void setHour(Short hour) {
    this.hour = hour;
}

public Short getMonth() {
    return month;
}

public void setMonth(Short month) {
    this.month = month;
}

public Byte getCategory() {
    return category;
}

public void setCategory(Byte category) {
    this.category = category;
}

public Byte getKind() {
    return kind;
}

public void setKind(Byte kind) {
    this.kind = kind;
}

public Byte getType() {
    return type;
}

public void setType(Byte type) {
    this.type = type;
}

public Byte getLightCondition() {
    return lightCondition;
}

public void setLightCondition(Byte lightCondition) {
    this.lightCondition = lightCondition;
}

public Boolean getBycicleInvolved() {
    return bycicleInvolved;
}

public void setBycicleInvolved(Boolean bycicleInvolved) {
    this.bycicleInvolved = bycicleInvolved;
}

public Boolean getCarInvolved() {
    return carInvolved;
}

public void setCarInvolved(Boolean carInvolved) {
    this.carInvolved = carInvolved;
}

public Boolean getPassengerInvolved() {
    return passengerInvolved;
}

public void setPassengerInvolved(Boolean passengerInvolved) {
    this.passengerInvolved = passengerInvolved;
}

public Boolean getMotorcycleInvolved() {
    return motorcycleInvolved;
}

public void setMotorcycleInvolved(Boolean motorcycleInvolved) {
    this.motorcycleInvolved = motorcycleInvolved;
}

public Boolean getDeliveryVanInvolved() {
    return deliveryVanInvolved;
}

public void setDeliveryVanInvolved(Boolean deliveryVanInvolved) {
    this.deliveryVanInvolved = deliveryVanInvolved;
}

public Boolean getTruckBusOrTramInvolved() {
    return truckBusOrTramInvolved;
}

public void setTruckBusOrTramInvolved(Boolean truckBusOrTramInvolved) {
    this.truckBusOrTramInvolved = truckBusOrTramInvolved;
}

public Byte getRoadSurfaceCondition() {
    return roadSurfaceCondition;
}

public void setRoadSurfaceCondition(Byte roadSurfaceCondition) {
    this.roadSurfaceCondition = roadSurfaceCondition;
}

public Float getCoordinateUtmX() {
    return coordinateUtmX;
}

public void setCoordinateUtmX(Float coordinateUtmX) {
    this.coordinateUtmX = coordinateUtmX;
}

public Float getCoordinateUtmY() {
    return coordinateUtmY;
}

public void setCoordinateUtmY(Float coordinateUtmY) {
    this.coordinateUtmY = coordinateUtmY;
}

public Float getLongitude() {
    return longitude;
}

public void setLongitude(Float longitude) {
    this.longitude = longitude;
}

public Float getLatitude() {
    return latitude;
}

public void setLatitude(Float latitude) {
    this.latitude = latitude;
}

}