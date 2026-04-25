create table customer_profiles
(
    id                  bigserial primary key,
    first_name          text                     not null,
    last_name           text                     not null,
    age                 integer                  not null,
    email               text                     not null,
    date_of_birth       date                     not null,
    phone_number        text,
    address             text,
    city                text,
    smoker              boolean                  not null,
    has_chronic_diseases boolean                  not null,
    created_at          timestamp with time zone not null
);