#!/bin/bash
# This setup script was tested on debian12, for ubuntu you might need different names for the apt packages
sudo apt-get update -y
sudo apt-get install python3-mysqldb php php-mysql docker docker.io mariadb-client -y
## Docker only
sudo docker pull mariadb
sudo docker container stop crash_db
sudo docker container rm crash_db
sudo docker container prune --force
sudo docker volume prune --force
sudo mkdir -p /opt/crashdb/mysql-data #Later make the DB persistent
res=$(sudo docker run -itd --env MARIADB_ROOT_PASSWORD="Test" --name crash_db --expose 3306 mariadb)
IPADDR="$(sudo docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $res)"
export CRASH_DB_IPADDR="$IPADDR"
echo "$CRASH_DB_IPADDR"
sleep 10 # Wait for mariadb to init
res=$(sudo mariadb --host=$CRASH_DB_IPADDR -p"Test" < setup.sql) #Operation log grows large, delete or disable after, compress_rows to save space
echo $res
## Docker only remove this if you want to run on your own DB


mkdir ./csvs
python3 setup_db.py -i land_def land land_str setup_csvs/bundesländer.csv
python3 setup_db.py -i type_def type type_str setup_csvs/type.csv
python3 setup_db.py -i category_def category category_str setup_csvs/category.csv
python3 setup_db.py -i kind_def kind kind_str setup_csvs/kind.csv
python3 setup_db.py -i light_condition_def light_condition light_condition_str setup_csvs/licht.csv
python3 setup_db.py -i bycicle_involved_def bycicle_involved bycicle_involved_str setup_csvs/rad.csv
python3 setup_db.py -i car_involved_def car_involved car_involved_str setup_csvs/istpkw.csv
python3 setup_db.py -i passenger_involved_def passenger_involved passenger_involved_str setup_csvs/istfuss.csv
python3 setup_db.py -i motorcycle_involved_def motorcycle_involved motorcycle_involved_str setup_csvs/istKrad.csv
python3 setup_db.py -i delivery_van_involved_def delivery_van_involved delivery_van_involved_str setup_csvs/istgfkz.csv
python3 setup_db.py -i truck_bus_or_tram_involved_def truck_bus_or_tram_involved truck_bus_or_tram_involved_str setup_csvs/istsonstig.csv
python3 setup_db.py -i road_surface_condition_def road_surface_condition road_surface_condition_str setup_csvs/strzustand.csv
python3 setup_db.py -i day_def day day_str setup_csvs/wochentag.csv

sudo python3 setup_db.py --download # downloads and unzips the contents
sudo python3 setup_db.py --fix      # fixes the filepaths (renames .txt to .csv ...)
sudo python3 setup_db.py --parse    # This parses each csv and inserts it into the db TODO: add ip param and password param for argvc