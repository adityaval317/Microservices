create table accounts.customers (
customer_id bigserial primary key,
name varchar(100) not null,
email varchar(100) not null,
mobile_number varchar(20) not null,
created_at timestamp NOT NULL,
created_by varchar(20) NOT NULL,
updated_at timestamp DEFAULT NULL,
updated_by varchar(20) DEFAULT NULL
);

create table accounts.accounts (
account_id bigserial primary key,
customer_id int not null references accounts.customers(customer_id),
account_number int not null,
account_type varchar(20) not null,
branch_address varchar(100) not null,
created_at timestamp NOT NULL,
created_by varchar(20) NOT NULL,
updated_at timestamp DEFAULT NULL,
updated_by varchar(20) DEFAULT NULL
);

CREATE TABLE loans.loans (
  loan_id bigserial primary key ,
  mobile_number varchar(15) NOT NULL,
  loan_number varchar(100) NOT NULL,
  loan_type varchar(100) NOT NULL,
  total_loan int NOT NULL,
  amount_paid decimal NOT NULL,
  outstanding_amount decimal NOT NULL,
  created_at timestamp NOT NULL,
  created_by varchar(20) NOT NULL,
  updated_at timestamp DEFAULT NULL,
  updated_by varchar(20) DEFAULT NULL
);


CREATE TABLE IF NOT EXISTS cards.cards (
  card_id bigserial primary key,
  mobile_number varchar(15) NOT NULL,
  card_number varchar(100) NOT NULL,
  card_type varchar(100) NOT NULL,
  total_limit int NOT NULL,
  amount_used decimal NOT NULL,
  available_amount decimal NOT NULL,
  created_at timestamp NOT NULL,
  created_by varchar(20) NOT NULL,
  updated_at timestamp DEFAULT NULL,
  updated_by varchar(20) DEFAULT NULL
);
