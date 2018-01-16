sudo apt-get -y install postgresql postgresql-contrib
sudo apt-get -y install pgadmin3
sudo -u postgres psql -c "CREATE USER alpralpr WITH PASSWORD 'alpralpr'"
sudo -u postgres psql -c "CREATE DATABASE alpralpr OWNER alpralpr"
echo "\c alpralpr;
CREATE TABLE violation_record (picture bytea, license_plate_number character varying(128), captured_date timestamp with time zone, recorded_date timestamp with time zone, PRIMARY KEY(license_plate_number, captured_date));
ALTER TABLE violation_record OWNER TO alpralpr;
CREATE TABLE permitted_vehicle (license_plate_number character varying(128), type character varying(128), PRIMARY KEY(license_plate_number));
ALTER TABLE permitted_vehicle OWNER TO alpralpr;" |sudo -u postgres psql
