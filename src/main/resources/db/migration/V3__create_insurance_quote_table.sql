create table insurance_quote
(
    id                  bigserial primary key,
    insurance_type      varchar(50)              not null,
    risk_level          varchar(20)              not null,
    status              varchar(20)              not null,
    customer_profile_id bigint                   not null,
    price_breakdown_id  bigint                   not null,
    created_at          timestamp with time zone not null default now(),
    updated_at          timestamp with time zone
);