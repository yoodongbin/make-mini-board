Drop table if exists board.comments;
Drop table if exists board.boards;
Drop table if exists board.member;

create table board.member
(
    member_seq  int auto_increment not null primary key,
    name  varchar(20)  not null,
    email varchar(100) not null,
    password  varchar(45) not null,
    birthdate date null,
    create_date datetime default now()
) comment '회원 DB';

create table board.boards
(
    board_seq      int auto_increment not null primary key,
    title          varchar(45)  not null,
    board_contents varchar(1000) not null,
    create_date    datetime  default now(),
    modify_date datetime,
    member_seq  int not null,
    view int default 0,
    parent_seq int default 0,
    group_num int default 0,
    board_level int default 0,
    KEY member_seq (member_seq),
    FOREIGN KEY (member_seq) REFERENCES member (MEMBER_SEQ)
) comment '게시판 DB';

CREATE TABLE board.comments (
                                comment_seq int NOT NULL AUTO_INCREMENT,
                                member_seq int NOT NULL,
                                board_seq int NOT NULL,
                                comment_contents varchar(500),
                                create_date datetime default now(),
                                PRIMARY KEY (comment_seq),
                                FOREIGN KEY (member_seq) REFERENCES member (MEMBER_SEQ),
                                FOREIGN KEY (board_seq) REFERENCES boards (BOARD_SEQ)
) comment '댓글 DB';

-- 회원삽입
insert into member (member_seq, name, email, password, birthdate)
values(member_seq, 'satto', 'satto@satto.co.kr','Test123!',19960412);

insert into member (member_seq, name, email, password, birthdate)
values(member_seq, 'bab', 'bab@bab.co.kr','Test123!',19970513);

insert into member (member_seq, name, email, password, birthdate)
values(member_seq, 'bab2', 'bab@bab','12',19970513);

insert into member (member_seq, name, email, password, birthdate)
values(member_seq, 'conchip', 'conchip@satto.co.kr','Test123!',19980614);
insert into member (member_seq, name, email, password, birthdate)
values(member_seq, 'pochachip', 'pochachip@pochachip.co.kr','Test123!',19990715);
insert into member (member_seq, name, email, password, birthdate)
values(member_seq, 'ggoboogichip', 'ggoboogichip@satto.co.kr','Test123!',20000816);
insert into member (member_seq, name, email, password, birthdate)
values(member_seq, 'zagalchi', 'zagalchi@satto.co.kr','Test123!',20010917);
insert into member (member_seq, name, email, password, birthdate)
values(member_seq, 'caramel', 'caramel@satto.co.kr','Test123!',20201018);

-- 게시판글 삽입
insert into boards
(board_seq, title, board_contents, member_seq)
values (board_seq, '간식 추천글', '하바네로 옥수수깡이 최고 !', '1');
insert into boards
(board_seq, title, board_contents, member_seq)
values (board_seq, '과자 추천글', '초당옥수수 콘칩이 최고 !', '2');
insert into boards
(board_seq, title, board_contents, member_seq)
values (board_seq, '스낵 추천글', '포카칩이 최고 !', '3');
insert into boards
(board_seq, title, board_contents, member_seq)
values (board_seq, '주전부리 추천글', '스윙칩이 최고 최고 !', '4');
insert into boards
(board_seq, title, board_contents, member_seq)
values (board_seq, '입심심 간식 추천글', '맛밤 최고 !', '5');
insert into boards
(board_seq, title, board_contents, member_seq)
values (board_seq, '좋아하는 과자 추천글', '곤약젤리 최고 !', '6');
insert into boards
(board_seq, title, board_contents, member_seq)
values (board_seq, '과자 왜이렇게 맛있어? 추천글', '단백질볼이 최고 !', '7');

-- 댓글 삽입
insert into comments
(comment_seq, member_seq, board_seq, comment_contents)
values (comment_seq, 1, 7, '공감 22 하바네로 옥수수깡 진짜 맛있어요 ! ');
insert into comments
(comment_seq, member_seq, board_seq, comment_contents)
values (comment_seq, 2, 6, '공감 22 닥터유 단백질 볼도 진짜 맛있어요 ! ');
insert into comments
(comment_seq, member_seq, board_seq, comment_contents)
values (comment_seq, 2, 5, '공감 22 곤약젤리는 샤인머스캣 진짜 맛있어요 ! ');
insert into comments
(comment_seq, member_seq, board_seq, comment_contents)
values (comment_seq, 3, 4, '공감 22 하지만 과자는 살찌죠 ! ');
insert into comments
(comment_seq, member_seq, board_seq, comment_contents)
values (comment_seq, 4, 3, '공감 22 요거트랑도 잘 어울려요 ! ');
insert into comments
(comment_seq, member_seq, board_seq, comment_contents)
values (comment_seq, 5, 2, '공감 22 존맛탱구리 ! ');
insert into comments
(comment_seq, member_seq, board_seq, comment_contents)
values (comment_seq, 7, 1, '공감 22 하 과자 못 끊어요 ! ');



WITH RECURSIVE board_paths (board_level, board_seq, title, group_num, parent_seq, path) AS
(
   SELECT board_level , board_seq, title, group_num, parent_seq, title
   FROM boards
   WHERE  board_level = '0'

   UNION ALL

   SELECT ep.board_level+1, e.board_seq, e.title, e.group_num, e.parent_seq,  CONCAT(ep.path, '->', e.title)
   FROM board_paths AS ep JOIN boards AS e
                               ON ep.board_seq = e.parent_seq
   -- and ep.group_num = e.group_num
)
SELECT * FROM board_paths ORDER BY path;
