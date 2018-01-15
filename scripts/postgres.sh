sudo apt-get -y install postgresql postgresql-contrib
sudo apt-get -y install pgadmin3
sudo -u postgres psql -c "CREATE USER alpralpr WITH PASSWORD 'alpralpr'"
sudo -u postgres psql -c "create database alpralpr owner alpralpr"
echo "\c alpralpr;
CREATE TABLE ViolationRecord (picture bytea, licensePlateNumber character varying(128), capturedDate timestamp with time zone, recordedDate timestamp with time zone);" |sudo -u postgres psql
