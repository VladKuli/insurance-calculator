create table price_breakdowns
(
    id                  bigserial primary key,
    base_price          numeric(10, 2) not null,
    risk_multiplier     numeric(10, 2) not null,
    coverage_multiplier numeric(10, 2) not null,
    additional_charges  numeric(10, 2) not null,
    discount            numeric(10, 2) not null,
    final_price         numeric(10, 2) not null,
    created_at          timestamp with time zone default now()

);