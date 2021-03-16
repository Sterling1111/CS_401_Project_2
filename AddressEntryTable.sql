-- auto-generated definition
create table ADDRESSENTRYTABLE
(
    ID        NUMBER generated as identity,
    FIRSTNAME VARCHAR2(25) not null,
    LASTNAME  VARCHAR2(25) not null,
    STREET    VARCHAR2(25),
    CITY      VARCHAR2(25) not null,
    STATE     VARCHAR2(25) not null,
    ZIP       NUMBER       not null,
    EMAIL     VARCHAR2(50) not null,
    PHONE     VARCHAR2(25) not null
)
    /

create unique index ADDRESSENTRYTABLE_ID_UINDEX
    on ADDRESSENTRYTABLE (ID)
/

alter table ADDRESSENTRYTABLE
    add constraint ADDRESSENTRYTABLE_PK
        primary key (ID)
    /