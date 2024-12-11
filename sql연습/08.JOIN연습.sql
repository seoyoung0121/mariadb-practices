-- join

-- 예제: 이름이 'parto hitomi'인 직원의 현재 직책을 구하세요.
select emp_no
from employees
where concat(first_name, ' ', last_name) = 'parto hitomi';

-- 11052
select title
from titles
where emp_no=11052;

select a.emp_no, a.first_name, a.last_name, b.title
from employees a, titles b
where a.emp_no = b.emp_no -- join condition
and concat(a.first_name, ' ', a.last_name) = 'parto hitomi'; -- row selection condition


-- inner join

-- 예제 1) 현재, 근무하고 있는 직원의 이름과 직책을 모두 출력하세요.
select a.first_name, b.title
from employees a, titles b
where a.emp_no=b.emp_no -- join 조건(n-1)
and b.to_date = '9999-01-01'; -- row 선택조건

-- 예제 2) 현재, 근무하고 있는 직원의 이름과 직책을 모두 출력하되, 여성 엔지니어(Engineer)만 출력하도록 한다.
select a.first_name, a.gender, b.title
from employees a, titles b
where a.emp_no = b. emp_no -- join 조건(n-1)
and b.to_date = '9999-01-01' -- row 선택조건1
and a.gender = 'f' -- row 선택조건2
and b.title = 'Engineer'; -- row 선택조건3

-- ANSI / ISO SQL1999 JOIN 표준 문법

-- 1) natural join
-- 조인 대상이 되는 두 테이블에 이름이 같은 공통 컬럼이 있는 경우
-- 조인 조건을 명시하지 않고 암묵적으로 조인이 된다.
select a.first_name, b.title
from employees a natural join titles b
where b.to_date = '9999-01-01';

-- 2) join ~ using
-- natural join의 문제점
select count(*)
from salaries a natural join titles b
where a.to_date = '9999-01-01'
  and b.to_date = '9999-01-01';

select count(*)
from salaries a join titles b using(emp_no)
where a.to_date = '9999-01-01'
and b.to_date = '9999-01-01';

-- 3) join ~ on
select count(*)
from salaries a join titles b on a.emp_no = b.emp_no
where a.to_date = '9999-01-01'
  and b.to_date = '9999-01-01';


-- 실습문제 1. 현재 직책별 평균 연봉을 큰 순서대로 출력하세요.
select b.title, avg(a.salary) as avg_salary
from salaries a join titles b on a.emp_no = b.emp_no
where a.to_date = '9999-01-01'
  and b.to_date = '9999-01-01'
group by  b.title
order by avg_salary desc;

-- 실습문제 2.
-- 현재, 직책별 평균 연봉과 직원 수를 구하되 직원수가 100명 이상인 직책만 출력
-- projection: 직책 평균연봉 직원수
select b.title, avg(a.salary) as avg_salary, count(*) as people
from salaries a join titles b on a.emp_no = b.emp_no
where a.to_date = '9999-01-01'
and b.to_date = '9999-01-01'
group by  b.title
having people >= 100
order by  people asc;

-- 실습문제 3.
-- 현재, 부서별로 직책이 Engineer인 직원들에 대한 평균 연봉을 구하세요.
-- projection: 부서이름 평균연봉
select c.dept_name, avg(a.salary) as avg_salary
from salaries a, dept_emp b, departments c, titles d
where a.emp_no = b.emp_no and b.dept_no = c.dept_no and b.emp_no = d.emp_no
and a.to_date = '9999-01-01' and b.to_date = '9999-01-01' and d.to_date = '9999-01-01'
and d.title = 'Engineer'
group by  c.dept_name
order by avg_salary desc;













