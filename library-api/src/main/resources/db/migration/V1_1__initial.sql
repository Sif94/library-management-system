create table borrower
(
    birthday           date,
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6),
    city               varchar(255),
    created_by         varchar(255),
    email              varchar(255) not null
        unique,
    first_name         varchar(255) not null,
    gender             varchar(255)
        constraint borrower_gender_check
            check ((gender)::text = ANY ((ARRAY ['MALE'::character varying, 'FEMALE'::character varying])::text[])),
    id                 varchar(255) not null
        primary key,
    last_modified_by   varchar(255),
    last_name          varchar(255) not null,
    phone              varchar(255)
        unique,
    state              varchar(255),
    street             varchar(255),
    zip                varchar(255)
);

alter table borrower
    owner to username;

create table departments
(
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6),
    created_by         varchar(255),
    department_id      varchar(255) not null
        primary key,
    department_name    varchar(255) not null
        unique,
    last_modified_by   varchar(255)
);

alter table departments
    owner to username;

create table faculties
(
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6),
    created_by         varchar(255),
    faculty_id         varchar(255) not null
        primary key,
    faculty_name       varchar(255) not null
        unique,
    last_modified_by   varchar(255)
);

alter table faculties
    owner to username;

create table students
(
    baccalaureate_year integer      not null,
    baccalaureate_name varchar(255),
    department_id      varchar(255)
        constraint students_constraint_department_id_fk
            references departments,
    id                 varchar(255) not null
        primary key
        constraint students_constraint_borrower_id_fk
            references borrower,
    student_code       varchar(255) not null
        unique
);

alter table students
    owner to username;

create table teachers
(
    field_of_study varchar(255),
    id             varchar(255) not null
        primary key
        constraint teachers_constraint_borrower_id_fk
            references borrower,
    teacher_code   varchar(255) not null
        unique
);

alter table teachers
    owner to username;

create table departments_teachers
(
    department_id varchar(255) not null
        constraint departments_teachers_constraint_department_id_fk
            references departments,
    teacher_id    varchar(255) not null
        constraint departments_teachers_constraint_teacher_id_fk
            references teachers,
    primary key (department_id, teacher_id)
);

alter table departments_teachers
    owner to username;

create table work
(
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6),
    author             varchar(255),
    created_by         varchar(255),
    description        varchar(255),
    last_modified_by   varchar(255),
    title              varchar(255),
    work_id            varchar(255) not null
        primary key
);

alter table work
    owner to username;

create table books
(
    isbn    integer      not null
        unique,
    year    integer      not null,
    work_id varchar(255) not null
        primary key
        constraint books_constraint_work_id_fk
            references work
);

alter table books
    owner to username;

create table borrow_transactions
(
    borrow_date           timestamp(6),
    return_date           timestamp(6),
    borrow_transaction_id varchar(255) not null
        primary key,
    borrower_id           varchar(255) not null
        constraint borrow_transactions_constraint_borrower_id_fk
            references borrower,
    work_id               varchar(255) not null
        constraint borrow_transactions_constraint_work_id_fk
            references work
);

alter table borrow_transactions
    owner to username;

create table theses
(
    date_of_graduation date         not null,
    work_id            varchar(255) not null
        primary key
        constraint theses_constraint_work_id_fk
            references work
);

alter table theses
    owner to username;

