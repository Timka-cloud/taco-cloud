create table if not exists users
(
    id        identity primary key,
    username   varchar(150) not null,
    "PASSWORD" varchar(150) not null,
    fullname   varchar(150) not null,
    street  varchar(150)  not null,
    city    varchar(150) not null,
    state       varchar(150) not null,
    "ZIP"   varchar(150)  not null,
    phone_number  varchar(150)  not null
);

create table if not exists Taco_Order
(
    id              identity primary key,
    delivery_Name   varchar(50) not null,
    delivery_Street varchar(50) not null,
    delivery_City   varchar(50) not null,
    delivery_State  varchar(2)  not null,
    delivery_Zip    varchar(10) not null,
    cc_number       varchar(16) not null,
    cc_expiration   varchar(5)  not null,
    cc_cvv          varchar(3)  not null,
    placed_at       timestamp   not null,
    user_id         bigint not null
);
create table if not exists Taco (
    id identity primary key,
    name varchar(50) not null,
    taco_order bigint not null,
    taco_order_key bigint not null,
    created_at timestamp not null
);
create table if not exists Ingredient_Ref (
    ingredient varchar(4) not null,
    taco bigint not null,
    taco_key bigint not null
);
create table if not exists Ingredient (
    id varchar(4) not null primary key ,
    name varchar(25) not null,
    type varchar(10) not null
);
alter table Taco
    add foreign key (taco_order) references Taco_Order(id);
alter table Ingredient_Ref
    add foreign key (ingredient) references Ingredient(id);
alter table Taco_Order
    add foreign key (user_id) references users(id);
--
-- Если в корне пути поиска классов приложения имеется файл с именем init-schema.sql,
--     то его содержимое будет интерпретироваться как код на языке SQL и выполняться
--     в базе данных при запуске приложения. Поэтому сохраните содержимое листинга 3.8 в файле с именем
--     init-schema.sql в папке src/main/resources.

