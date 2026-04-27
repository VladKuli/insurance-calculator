create table customer_profiles
(
    id                   bigserial primary key,
    first_name           text not null,
    last_name            text not null,
    email                text not null,
    date_of_birth        date not null,
    phone_number         text not null,
    address              text,
    city                 text,
    smoker               boolean,
    has_chronic_diseases boolean,
    created_at           timestamp with time zone default now()
);