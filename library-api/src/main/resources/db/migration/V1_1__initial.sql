-- Borrower
create table borrower
(
    birthday           date,
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6),
    city               varchar(255),
    created_by         varchar(255),
    email              varchar(255) not null
        constraint UQ_borrower_email unique,
    first_name         varchar(255) not null,
    gender             varchar(255)
        constraint CHK_borrower_gender check (
            (gender)::text = ANY (ARRAY['MALE','FEMALE']::text[])
            ),
    borrower_id                 varchar(255) not null
        constraint PK_borrower primary key,
    last_modified_by   varchar(255),
    last_name          varchar(255) not null,
    phone              varchar(255)
        constraint UQ_borrower_phone unique,
    state              varchar(255),
    street             varchar(255),
    zip                varchar(255)
);

alter table borrower owner to username;


-- Faculties
create table faculties
(
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6),
    created_by         varchar(255),
    faculty_id         varchar(255) not null
        constraint PK_faculties primary key,
    faculty_name       varchar(255) not null
        constraint UQ_faculties_faculty_name unique,
    last_modified_by   varchar(255)
);

alter table faculties owner to username;


-- Departments
create table departments
(
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6),
    created_by         varchar(255),
    department_id      varchar(255) not null
        constraint PK_departments primary key,
    department_name    varchar(255) not null
        constraint UQ_departments_department_name unique,
    faculty_faculty_id varchar(255)
        constraint FK_departments_faculty references faculties(faculty_id),
    last_modified_by   varchar(255)
);

alter table departments owner to username;


-- Faculties_Departments (Join Table)
create table faculties_departments
(
    departments_department_id varchar(255) not null
        constraint UQ_faculties_departments_department_id unique
        constraint FK_faculties_departments_department references departments(department_id),
    faculty_faculty_id        varchar(255) not null
        constraint FK_faculties_departments_faculty references faculties(faculty_id),
    constraint PK_faculties_departments primary key (departments_department_id, faculty_faculty_id)
);

alter table faculties_departments owner to username;


-- Students
create table students
(
    baccalaureate_year integer not null,
    baccalaureate_name varchar(255),
    department_id      varchar(255)
        constraint FK_students_department references departments(department_id),
    student_id                 varchar(255) not null,
    student_code       varchar(255) not null
        constraint UQ_students_student_code unique,
    constraint PK_students primary key (student_id),
    constraint FK_students_borrower foreign key (student_id) references borrower(borrower_id)
);

alter table students owner to username;


-- Teachers
create table teachers
(
    field_of_study varchar(255),
    teacher_id             varchar(255) not null,
    teacher_code   varchar(255) not null
        constraint UQ_teachers_teacher_code unique,
    constraint PK_teachers primary key (teacher_id),
    constraint FK_teachers_borrower foreign key (teacher_id) references borrower(borrower_id)
);

alter table teachers owner to username;


-- Departments_Teachers (Join Table)
create table departments_teachers
(
    department_id varchar(255) not null
        constraint FK_departments_teachers_department references departments(department_id),
    teacher_id    varchar(255) not null
        constraint FK_departments_teachers_teacher references teachers(teacher_id),
    constraint PK_departments_teachers primary key (department_id, teacher_id)
);

alter table departments_teachers owner to username;


-- Works
create table works
(
    created_date       timestamp(6) not null,
    last_modified_date timestamp(6),
    work_type          varchar(31) not null,
    author             varchar(255),
    created_by         varchar(255),
    description        varchar(255),
    last_modified_by   varchar(255),
    title              varchar(255),
    work_id            varchar(255) not null
        constraint PK_works primary key
);

alter table works owner to username;


-- Books
create table books
(
    year          integer not null,
    cover_picture varchar(255),
    isbn          varchar(255) not null
        constraint UQ_books_isbn unique,
    book_id       varchar(255) not null,
    constraint PK_books primary key (book_id),
    constraint FK_books_works foreign key (book_id) references works(work_id)
);

alter table books owner to username;


-- Borrow Transactions
create table borrow_transactions
(
    borrow_date           timestamp(6),
    return_date           timestamp(6),
    borrow_transaction_id varchar(255) not null,
    borrower_id           varchar(255) not null,
    work_id               varchar(255) not null,
    constraint PK_borrow_transactions primary key (borrow_transaction_id),
    constraint FK_borrow_transactions_borrower foreign key (borrower_id) references borrower(borrower_id),
    constraint FK_borrow_transactions_works foreign key (work_id) references works(work_id)
);

alter table borrow_transactions owner to username;


-- Theses
create table theses
(
    date_of_graduation date not null,
    these_id            varchar(255) not null,
    constraint PK_theses primary key (these_id),
    constraint FK_theses_works foreign key (these_id) references works(work_id)
);

alter table theses owner to username;
