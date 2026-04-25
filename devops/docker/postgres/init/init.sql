CREATE ROLE insurance_admin WITH LOGIN PASSWORD 'admin' CREATEDB;

CREATE DATABASE insurance_calculator OWNER insurance_admin;
CREATE DATABASE insurance_calculator_test OWNER insurance_admin;

\connect insurance_calculator

CREATE EXTENSION IF NOT EXISTS hstore WITH SCHEMA pg_catalog;
CREATE EXTENSION IF NOT EXISTS pg_trgm;

\connect insurance_calculator_test

CREATE EXTENSION IF NOT EXISTS hstore WITH SCHEMA pg_catalog;
CREATE EXTENSION IF NOT EXISTS pg_trgm;