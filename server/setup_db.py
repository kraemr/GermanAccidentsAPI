import requests
import zipfile
import io
import os
import datetime
import csv
import MySQLdb
import sys

# TODO improve this setup script, currently takes "ages"

def download_zip(zip_file_url,year):
    response = requests.get(zip_file_url)
    if response.status_code == 200:
        with io.BytesIO(response.content) as zip_data:
                with zipfile.ZipFile(zip_data, "r") as zip_ref:
                    zip_ref.extractall("csvs/"+str(year))
    else:
        return -1

def download_all():
    start_year = 2016 #the csvs start at 2016
    current_year = datetime.date.today().year
    year = start_year
    while(year <current_year):
        download_zip('https://www.opengeodata.nrw.de/produkte/transport_verkehr/unfallatlas/Unfallorte' + str(year)+'_EPSG25832_CSV.zip', year)
        year+=1

# table is the table name and colnames is an array with two values 0=>int(the id) and the other is 1=>definition string
def insert_def(table,colnames,fstr):
    db = MySQLdb.connect(host="127.0.0.1",    # your host, usually localhost
    user="myuser",         # your username
    passwd="Test",  # your password
    db="AccidentDB")        # name of the data base
    cursor = db.cursor()
    with open(fstr) as csvf:
        reader = csv.DictReader(csvf,delimiter=";")
        for row in reader:
            num = row["number"]
            name = row["name"]
            sqlstr = f"Insert into {table}({colnames[0]},{colnames[1]}) VALUES({num},'{name}');"
            cursor.execute(sqlstr)
        db.commit()

def parse_csv():
    db = MySQLdb.connect(host="127.0.0.1",    # your host, usually localhost
                     user="myuser",         # your username
                     passwd="Test",  # your password
                     db="AccidentDB")        # name of the data base
    cursor = db.cursor()
    start_year = 2016 #the csvs start at 2016
    current_year = int(datetime.date.today().year)
    year_i = start_year
    year_i = int(year_i)
    #cursor.execute("SET sql_log_bin = 0;") # avoid huge binary logs
    db.commit()
    while(year_i < current_year):
        print(str(year_i) + ":")
        filestr = ""
        if year_i < 2021:
            filestr = f'csvs/{year_i}/csv/Unfallorte{year_i}_LinRef.csv'
        elif year_i == 2021:
            filestr = f'csvs/{year_i}/Unfallorte2021_EPSG25832_CSV/Unfallorte{year_i}_LinRef.csv'
        elif year_i == 2022:
            filestr = f'csvs/{year_i}/Unfallorte{year_i}_LinRef.csv'
        else:
            filestr = f'csvs/{year_i}/csv/Unfallorte{year_i}_LinRef.csv'
        with open(filestr) as csvf:
            reader = csv.DictReader(csvf,delimiter=";")
            i=0
            for row in reader:
                uident=0
                try:
                    uident = int(row["UIDENTSTLAE"]) 
	            #if an exception is thrown because key doesnt exist, or the number is corrupt, like in a few csvs ... then the uident will just be set to 0
                except:
                    uident = 0
                land = row["ULAND"]
                region = row["UREGBEZ"]
                district = row["UKREIS"]
                munincipality = row["UGEMEINDE"]
                year = row["UJAHR"]
                day = row["UWOCHENTAG"]
                month = row["UMONAT"]
                hour = row["USTUNDE"]
                category = row["UKATEGORIE"]
                kind = row["UART"]
                type = row["UTYP1"]
                if(year_i == 2017):
                    light_cond = row["LICHT"]
                    delivery_involved = 0
                else:
                    light_cond = row["ULICHTVERH"]
                    delivery_involved = row["IstGkfz"]
                if(year_i == 2016): # they changed the name of this FOUR TIMES ... just why
                    road_surface_condition=row["IstStrasse"]
                if(year_i == 2017 or year_i == 2018 or year_i == 2019 or year_i == 2020):
                    road_surface_condition=row["STRZUSTAND"]                
                if(year_i >= 2021):
                    road_surface_condition = row["IstStrassenzustand"]
                bycicle_involved = row["IstRad"]
                car_involved = row["IstPKW"]
                passenger_involved = row["IstFuss"]
                motorcycle_involved = row["IstKrad"]
                try:
                    truck_bus_or_tram_involved = row["IstSonstige"]
                except:
                    truck_bus_or_tram_involved = row["IstSonstig"]
            #WHY IS THIS NOT IN THE PROPER FORMAT BY DEFAULT AND WHY ARE THERE FUCKING WHITESPACES/TABS INSIDE OF A NUMBER IN THE CSV !?=!"?!?!?!?!??!"
            #Tax Money at work i guess :)
                coordinate_UTM_x = row["LINREFX"].replace(",",".") 
                coordinate_UTM_y = row["LINREFY"].replace(",",".")
                long = row["XGCSWGS84"].replace(",",".")
                lat = row["YGCSWGS84"].replace(",",".")
                sqlstr="INSERT INTO accident_data(uident,land,region,district,munincipality,"
                sqlstr+="year,day,hour,month,category,kind,type,light_condition,bycicle_involved,car_involved,"
                sqlstr+="passenger_involved,motorcycle_involved,delivery_van_involved,truck_bus_or_tram_involved,"
                sqlstr+="road_surface_condition,coordinate_UTM_x,coordinate_UTM_y,longitude,latitude)"
                sqlstr+=f"VALUES({uident},{land},{region},{district},{munincipality},{year},{day},{hour},{month},"
                sqlstr+=f"{category},{kind},{type},{light_cond},{bycicle_involved},{car_involved},"
                sqlstr+=f"{passenger_involved},{motorcycle_involved},{delivery_involved},{truck_bus_or_tram_involved},"
                sqlstr+=f"{road_surface_condition},{coordinate_UTM_x},{coordinate_UTM_y},{long},{lat});"
                cursor.execute(sqlstr)
        db.commit()
        year_i+=1
    db.close()


def fix_csvs():
    list_of_files = os.listdir(os.getcwd() + "/csvs")
    for filename in list_of_files:
        year = filename # year is a directory in csvs years less than 2021 contain additional csv folder where the csv is named .txt ...?????????? why
        year = int(year)

        if year == 2021:
            year_str = str(year)
            csv_folder = os.getcwd() + "/csvs/" + year_str + "/Unfallorte2021_EPSG25832_CSV/"              
            path = csv_folder + f"Unfallorte_{year_str}_LinRef.txt"
            newpath = csv_folder + f"Unfallorte{year_str}_LinRef.csv"
            os.rename(path,newpath)
       

        if year < 2020:
            list_csv_csv_files = os.listdir(os.getcwd() + "/csvs/" + str(year) + "/csv/")
            # Why do they change the naming randomly ?????? like cmon
            if year == 2016:
                try:
                    path = os.getcwd() + "/csvs/" + str(year) + "/csv/" + f"Unfallorte_{year}_LinRef.txt"
                    newpath = os.getcwd() + "/csvs/" + str(year) + "/csv/" + f"Unfallorte{year}_LinRef.csv"
                    os.rename(path,newpath)
                except:
                    print("File not found or already fixed to be .csv")
            else:
                try:
                    path = os.getcwd() + "/csvs/" + str(year) + "/csv/" + f"Unfallorte{year}_LinRef.txt"
                    newpath = os.getcwd() + "/csvs/" + str(year) + "/csv/" + f"Unfallorte{year}_LinRef.csv"
                    os.rename(path,newpath)
                except:
                    print("File not found or already fixed to be .csv")
                

def main():
    print(sys.argv)
    if sys.argv[1] == "-d" or sys.argv[1] == "--download":
        download_all()
    if sys.argv[1] == "-p" or sys.argv[1] == "--parse":
        parse_csv()
    if sys.argv[1] == "-f" or sys.argv[1] == "--fix":
        fix_csvs()
    if sys.argv[1] == "-i":
        colnames=["",""]
        tablename=sys.argv[2]
        colnames[0] = sys.argv[3] #colname for id
        colnames[1] = sys.argv[4] #colname for definition
        fstr=sys.argv[5] #path to csv
        insert_def(tablename,colnames,fstr)

if __name__=="__main__":
    main()
