create table authors
(
    id        serial       not null,
    image_url varchar(255) not null,
    name      varchar(30)  not null,
    surname   varchar(30)  not null,
    primary key (id)
);
create table books
(
    id        serial       not null,
    amount    int4,
    image_url varchar(255) not null,
    name      varchar(30)  not null,
    year      int4 check (year <=2023 AND year >=0) ,
    author_id int4, primary key (id))
create table clients
(
    id        serial      not null,
    image_url varchar(255),
    name      varchar(30) not null,
    password  varchar(30) not null,
    phone     varchar(255),
    role      varchar(255),
    primary key (id)
);
create table shopping_basket
(
    id        serial not null,
    book_id   int4,
    client_id int4,
    primary key (id)
);
alter table books
    add constraint FK_books_to_authors foreign key (author_id) references authors;
alter table shopping_basket
    add constraint FK_shopping_basket_to_books foreign key (book_id) references books;
alter table shopping_basket
    add constraint FK_shopping_basket_to_clients foreign key (client_id) references clients;