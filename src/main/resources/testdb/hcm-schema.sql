drop table T_HCM if exists;

create table T_HCM (EMPLOYEE_ID bigint identity primary key,
                        EXP decimal(5,2),
                        CURR_ROLE varchar(4),
                        YRS_CURR_ROLE int,
                        GOAL_COM_CURR_YR boolean,
                        CLNT_APRCTN_CURR_YR boolean);