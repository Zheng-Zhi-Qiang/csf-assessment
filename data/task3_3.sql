-- TODO Task 3
drop database if exists ecommerce;

create database ecommerce;

use ecommerce;

create table orders (
    order_id char(26) not null,
    order_date date not null,
    name varchar(128) not null,
    address varchar(256) not null,
    priority boolean not null,
    comments text,

    primary key(order_id)
);

-- Omitted primary key since there can be duplicate product_ids in the same order as per instructions
create table line_items (
    product_id varchar(128) not null,
    name varchar(256) not null,
    quantity int not null,
    price float not null,
    order_id char(26) not null,

    constraint fk_order_id foreign key(order_id) references orders(order_id)  
);

grant all privileges on ecommerce.* to fred@'%';

flush privileges;