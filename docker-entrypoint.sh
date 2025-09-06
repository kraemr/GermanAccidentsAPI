#!/bin/bash





pwd
ls -la

# # Initialize MariaDB if needed
# if [ ! -d /var/lib/mysql/mysql ]; then
#     echo "Initializing MariaDB..."
#     chown -R mysql:mysql /var/lib/mysql
#     mysql_install_db --user=mysql --basedir=/usr --datadir=/var/lib/mysql
# fi

echo "Starting MariaDB..."
mysqld --user=mysql --bind-address=0.0.0.0 --port=3306 --console &
sleep 4



if [ ! -f /var/lib/mysql/.init_done ]; then
    echo "Bootstrapping MariaDB user and database..."

    mariadb < setup.sql
    mysql --host=localhost <<-EOSQL
    CREATE DATABASE IF NOT EXISTS \`${MYSQL_DATABASE}\`;
    CREATE USER IF NOT EXISTS '${MYSQL_USER}'@'%' IDENTIFIED BY '${MYSQL_PASSWORD}';
    GRANT ALL PRIVILEGES ON \`${MYSQL_DATABASE}\`.* TO '${MYSQL_USER}'@'%';
    CREATE USER IF NOT EXISTS '${MYSQL_USER}'@'localhost' IDENTIFIED BY '${MYSQL_PASSWORD}';
    GRANT ALL PRIVILEGES ON \`${MYSQL_DATABASE}\`.* TO '${MYSQL_USER}'@'localhost';
    FLUSH PRIVILEGES;
EOSQL

    touch /var/lib/mysql/.init_done
fi



if [ ! -f /opt/.setup_done ]; then
    # Run the Python setup steps
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

    python3 setup_db.py --download
    python3 setup_db.py --fix
    python3 setup_db.py --parse
fi
touch /opt/.setup_done
# Start the Spring Boot app
echo "Starting Spring Boot application..."
exec "$@"