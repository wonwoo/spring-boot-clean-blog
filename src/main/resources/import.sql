
insert into category(ID, NAME, REG_DATE) values(1, 'spring', CURRENT_TIMESTAMP());
insert into category(ID, NAME, REG_DATE) values(2, 'java', CURRENT_TIMESTAMP());

insert into post(ID, TITLE, CODE, CONTENT, STATUS, REG_DATE, CATEGORY_ID) values(1, '테스트', '지금 포스팅은 테스트 포스팅 입니다.', '지금 포스팅은 테스트 포스팅 입니다.', 'Y',CURRENT_TIMESTAMP(), 1);
