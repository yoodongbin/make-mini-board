Drop table if exists board.boards;

create table board.boards
(
    board_seq      int auto_increment primary key,
    title          varchar(45)   null,
    board_contents varchar(1000) null,
    create_date    datetime      null,
    member_seq     varchar(45)   null
) comment '게시판 DB';
