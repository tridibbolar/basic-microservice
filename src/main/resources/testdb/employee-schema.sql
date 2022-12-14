drop table T_EMPLOYEE if exists;

create table T_EMPLOYEE (EMPLOYEE_ID bigint identity primary key,
                        NAME varchar(50) not null,
                        AGE int not null,
                        GENDER varchar(1),
                        ADDRESS varchar(200),
                        ISACTIVE boolean not null);