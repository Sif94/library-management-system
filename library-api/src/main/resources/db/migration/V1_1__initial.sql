create table borrowers
(
    birthday           date,
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6),
    borrower_id        varchar(255) not null
        primary key,
    city               varchar(255),
    created_by         varchar(255) not null,
    email              varchar(255) not null
        unique,
    first_name         varchar(255) not null,
    gender             varchar(255)
        constraint borrowers_gender_check
            check ((gender)::text = ANY ((ARRAY ['MALE'::character varying, 'FEMALE'::character varying])::text[])),
    last_modified_by   varchar(255),
    last_name          varchar(255) not null,
    phone              varchar(255)
        unique,
    state              varchar(255),
    street             varchar(255),
    zip                varchar(255)
);

alter table borrowers
    owner to username;

create table faculties
(
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6),
    created_by         varchar(255) not null,
    description        text,
    faculty_id         varchar(255) not null
        primary key,
    faculty_name       varchar(255) not null
        unique,
    last_modified_by   varchar(255)
);

alter table faculties
    owner to username;

create table departments
(
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6),
    created_by         varchar(255) not null,
    department_id      varchar(255) not null
        primary key,
    department_name    varchar(255) not null
        unique,
    description        varchar(255),
    faculty_id         varchar(255)
        constraint fk_departments_faculty_id
            references faculties,
    last_modified_by   varchar(255)
);

alter table departments
    owner to username;

create table students
(
    baccalaureate_year integer      not null,
    baccalaureate_name varchar(255),
    department_id      varchar(255)
        constraint fk_students_department_id
            references departments,
    student_code       varchar(255) not null
        unique,
    student_id         varchar(255) not null
        primary key
        constraint fk_students_student_id
            references borrowers
);

alter table students
    owner to username;

create table teachers
(
    field_of_study varchar(255),
    teacher_code   varchar(255) not null
        unique,
    teacher_id     varchar(255) not null
        primary key
        constraint fk_teachers_teacher_id
            references borrowers
);

alter table teachers
    owner to username;

create table departments_teachers
(
    department_id varchar(255) not null
        constraint fk_departments_teachers_department_id
            references departments,
    teacher_id    varchar(255) not null
        constraint fk_departments_teachers_teacher_id
            references teachers,
    primary key (department_id, teacher_id)
);

alter table departments_teachers
    owner to username;

create table works
(
    is_available       boolean,
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6),
    work_type          varchar(31)  not null,
    author             varchar(255),
    created_by         varchar(255) not null,
    description        varchar(255),
    last_modified_by   varchar(255),
    title              varchar(255),
    work_id            varchar(255) not null
        primary key
);

alter table works
    owner to username;

create table books
(
    year          integer      not null,
    book_id       varchar(255) not null
        primary key
        constraint fk_books_book_id
            references works,
    cover_picture varchar(255),
    isbn          varchar(255) not null
        unique
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
        constraint fk_borrow_transactions_borrower_id
            references borrowers,
    work_id               varchar(255) not null
        constraint fk_borrow_transactions_work_id
            references works
);

alter table borrow_transactions
    owner to username;

create table theses
(
    date_of_graduation date         not null,
    these_id           varchar(255) not null
        primary key
        constraint fk_theses_these_id
            references works
);

alter table theses
    owner to username;

