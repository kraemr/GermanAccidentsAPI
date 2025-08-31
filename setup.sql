CREATE DATABASE AccidentDB;
use AccidentDB;
Create Table accident_data(
    Id SERIAL,
    uident BIGINT UNSIGNED, #UIDENTSTLAE
    land SMALLINT, #ULAND
    region MEDIUMINT, #UREGBEZ
    district MEDIUMINT, #UKREIS
    munincipality MEDIUMINT, #UGEMEINDE
    year SMALLINT, 
    day SMALLINT,
    hour SMALLINT,
    month SMALLINT,
    category TINYINT, # UKATEGORIE
    kind TINYINT, # UART
    type TINYINT, # UTYP1
    light_condition TINYINT, # ULICHTVERH
    bycicle_involved BOOLEAN,
    car_involved BOOLEAN,
    passenger_involved BOOLEAN,
    motorcycle_involved BOOLEAN,
    delivery_van_involved BOOLEAN,
    truck_bus_or_tram_involved BOOLEAN,
    road_surface_condition TINYINT,
    coordinate_UTM_x FLOAT, #LINREFX 
    coordinate_UTM_y FLOAT, #LINREFY
    longitude FLOAT, # x
    latitude FLOAT, # y
    CONSTRAINT PK_Accident_Data PRIMARY KEY (Id)
);

Create Table land_def(
    land SMALLINT,
    land_str TINYTEXT,
    CONSTRAINT PK_Land_def PRIMARY KEY (land)
);

Create Table munincipality_def(
    munincipality MEDIUMINT, # Foreign Key
    munincipality_str TINYTEXT,
    CONSTRAINT PK_munincipality_def PRIMARY KEY (munincipality)
);

Create Table kind_def(
    kind TINYINT, # Foreign Key 
    kind_str TINYTEXT,
    CONSTRAINT PK_kind_def PRIMARY KEY (kind)
);

Create Table category_def(
    category TINYINT, # Foreign Key 
    category_str TINYTEXT,
    CONSTRAINT PK_category_def PRIMARY KEY (category)
);

Create Table type_def(
    type TINYINT, # Foreign Key 
    type_str TINYTEXT,
    CONSTRAINT PK_type_def PRIMARY KEY (type)
);

Create Table district_def(
    district MEDIUMINT, # Foreign Key
    district_str TINYTEXT,
    CONSTRAINT PK_district_def PRIMARY KEY (district)
);

CREATE TABLE light_condition_def (
    light_condition TINYINT,
    light_condition_str TINYTEXT,
    CONSTRAINT PK_light_condition_def PRIMARY KEY (light_condition)
);

CREATE TABLE bycicle_involved_def (
    bycicle_involved BOOLEAN,
    bycicle_involved_str TINYTEXT,
    CONSTRAINT PK_bycicle_involved_def PRIMARY KEY (bycicle_involved)
);

CREATE TABLE car_involved_def (
    car_involved BOOLEAN,
    car_involved_str TINYTEXT,
    CONSTRAINT PK_car_involved_def PRIMARY KEY (car_involved)
);

CREATE TABLE passenger_involved_def (
    passenger_involved BOOLEAN,
    passenger_involved_str TINYTEXT,
    CONSTRAINT PK_passenger_involved_def PRIMARY KEY (passenger_involved)
);

CREATE TABLE motorcycle_involved_def (
    motorcycle_involved BOOLEAN,
    motorcycle_involved_str TINYTEXT,
    CONSTRAINT PK_motorcycle_involved_def PRIMARY KEY (motorcycle_involved)
);

CREATE TABLE delivery_van_involved_def (
    delivery_van_involved BOOLEAN,
    delivery_van_involved_str TINYTEXT,
    CONSTRAINT PK_delivery_van_involved_def PRIMARY KEY (delivery_van_involved)
);

CREATE TABLE truck_bus_or_tram_involved_def (
    truck_bus_or_tram_involved BOOLEAN,
    truck_bus_or_tram_involved_str TINYTEXT,
    CONSTRAINT PK_truck_bus_or_tram_involved_def PRIMARY KEY (truck_bus_or_tram_involved)
);

CREATE TABLE road_surface_condition_def (
    road_surface_condition TINYINT,
    road_surface_condition_str TINYTEXT,
    CONSTRAINT PK_road_surface_condition_def PRIMARY KEY (road_surface_condition)
);

CREATE TABLE day_def (
    day TINYINT,
    day_str TINYTEXT,
    CONSTRAINT PK_day_def PRIMARY KEY (day)
);
CREATE INDEX IX_accident_year ON accident_data(year);
CREATE INDEX IX_accident_month ON accident_data(month);
CREATE INDEX IX_accident_region ON accident_data(region);
CREATE INDEX IX_accident_land ON accident_data(land);
CREATE INDEX IX_accident_kind ON accident_data(kind);
CREATE INDEX IX_accident_category ON accident_data(category);


CREATE INDEX IX_land_def ON land_def(land);
CREATE INDEX IX_munincipality_def ON munincipality_def(munincipality);
CREATE INDEX IX_kind_def ON kind_def(kind);
CREATE INDEX IX_category_def ON category_def(category);
CREATE INDEX IX_type_def ON type_def(type);
CREATE INDEX IX_district_def ON district_def(district);
CREATE INDEX IX_light_condition_def ON light_condition_def(light_condition);
CREATE INDEX IX_bycicle_involved_def ON bycicle_involved_def(bycicle_involved);
CREATE INDEX IX_car_involved_def ON car_involved_def(car_involved);
CREATE INDEX IX_passenger_involved_def ON passenger_involved_def(passenger_involved);
CREATE INDEX IX_motorcycle_involved_def ON motorcycle_involved_def(motorcycle_involved);
CREATE INDEX IX_delivery_van_involved_def ON delivery_van_involved_def(delivery_van_involved);
CREATE INDEX IX_truck_bus_or_tram_involved_def ON truck_bus_or_tram_involved_def(truck_bus_or_tram_involved);
CREATE INDEX IX_road_surface_condition_def ON road_surface_condition_def(road_surface_condition);
CREATE INDEX IX_day_def ON day_def(day);

