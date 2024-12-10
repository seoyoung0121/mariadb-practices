-- 날짜 함수

-- curdate(), current_date
select  curdate(), current_date from dual;

-- curtime(), current_time
select curtime(), current_time from dual;

-- now(), sysdate()
select now(), sysdate() from dual;
-- 똑같음: 쿼리 실행되기 전의 시간을 구해놓고 작동함
select now(), sleep(2), now() from dual;
-- 다름: sysdate는 쿼리가 실행되면서 실행
select now(), sleep(2), sysdate() from dual;

-- date_format
-- default format
-- date: %Y-%m-%d
-- datetime: %Y-%m-%d %h:%i:%s
select date_format(now(), '%Y년 %m월 %d일 %h시 %i분 %s초') from dual;
select date_format(now(), '%d %b \'%y %h:%i:%s') from dual;

-- period_diff
-- 예제: 근무 개월
select first_name, hire_date,
       period_diff(date_format(curdate(), '%y%m'), date_format(hire_date, '%y%m')) as '근무 개월'
from employees;

-- date_add(=addDate), date_sub(subdate)
-- 예제: 각 사원의 근속 연수가 5년이 되는 날에 휴가를 보내준다면 각 사원의 5년 근속 휴가 날짜는?
select first_name,hire_date,
       date_add(hire_date, interval 5 year)
from employees;


