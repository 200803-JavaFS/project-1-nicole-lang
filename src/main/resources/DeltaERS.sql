create table ers_users(
	ers_users_id serial primary key,
	ers_username varchar(50) unique,
	ers_password varchar(50) not null,
	user_first_name varchar(50) not null,
	user_last_name varchar(50) not null,
	user_email varchar(100) not null unique,
	user_role_id integer not null references ers_user_roles(user_role_id)
);
create table ers_reimbursement_status(
	reimb_status_id serial primary key,
	reimb_status varchar(10) not null
);

insert into ers_reimbursement_status (reimb_status)
values ('Pending'), ('Approved'), ('Denied');

create table ers_reimbursement_type(
	reimb_type_id serial primary key,
	reimb_type varchar(10) not null
);

insert into ers_reimbursement_type (reimb_type)
values ('Lodging'), ('Travel'), ('Food'), ('Other');

create table ers_user_roles(
	user_role_id serial primary key,
	user_role varchar(10) not null
);

insert into ers_user_roles (user_role)
values ('Employee'), ('Manager');

create table ers_reimbursements(
	reimb_id serial primary key,
	reimb_amount numeric not null,
	reimb_submitted timestamp not null,
	reimb_resolved timestamp,
	reimb_description varchar(250),
	reimb_author integer references ers_users(ers_users_id) not null,
	reimb_resolver integer references ers_users(ers_users_id),
	reimb_status_id integer references ers_reimbursement_status(reimb_status_id) not null,
	reimb_type_id integer references ers_reimbursement_type(reimb_type_id) not null
)