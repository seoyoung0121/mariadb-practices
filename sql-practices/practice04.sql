-- 서브쿼리(SUBQUERY) SQL 문제입니다.

-- 단 조회결과는 급여의 내림차순으로 정렬되어 나타나야 합니다. 

-- 문제1.
-- 현재 전체 사원의 평균 급여보다 많은 급여를 받는 사원은 몇 명이나 있습니까?
select count(*)
from salaries
where salary > (select avg(salary) from salaries where to_date = '9999-01-01')
and to_date = '9999-01-01';

-- 문제2. 
-- 현재, 각 부서별로 최고의 급여를 받는 사원의 사번, 이름, 부서 급여을 조회하세요. 단 조회결과는 급여의 내림차순으로 정렬합니다.
select a.emp_no, a.first_name, d.dept_name, b.salary
from employees a, salaries b, dept_emp c, departments d
where a.emp_no = b.emp_no and b.emp_no = c.emp_no and c.dept_no = d.dept_no
  and (c.dept_no, b.salary) in (select b.dept_no, max(a.salary)
                                from salaries a, dept_emp b
                                where a.emp_no = b.emp_no
                                  and a.to_date = '9999-01-01'
                                  and b.to_date = '9999-01-01'
                                group by b.dept_no)
  and b.to_date = '9999-01-01' and c.to_date = '9999-01-01'
order by b.salary desc;

-- 문제3.
-- 현재, 사원 자신들의 부서의 평균 급여보다 급여가 많은 사원들의 사번, 이름 그리고 급여를 조회하세요
select a.emp_no, b.salary
from employees a, salaries b, (select a.dept_no, avg(b.salary) as avg_salary
                               from dept_emp a, salaries b
                               where a.emp_no = b.emp_no
                                 and a.to_date = '9999-01-01' and b.to_date = '9999-01-01'
                               group by a.dept_no) c, dept_emp d
where a.emp_no = b.emp_no and a.emp_no = d.emp_no and d.dept_no = c.dept_no
and b.to_date = '9999-01-01'
and b.salary > c.avg_salary
order by b.salary desc;

        
-- 문제4.
-- 현재, 사원들의 사번, 이름, 그리고 매니저 이름과 부서 이름을 출력해 보세요.
select a.emp_no, a.first_name, b.first_name, c.dept_name
from employees a, (select a.first_name, b.dept_no
                   from employees a, dept_manager b
                   where a.emp_no = b.emp_no
                   and b.to_date = '9999-01-01'
                   ) b, departments c, dept_emp d
where a.emp_no = d.emp_no and c.dept_no = d.dept_no and b.dept_no = c.dept_no
and d.to_date = '9999-01-01';

-- 문제5.
-- 현재, 평균급여가 가장 높은 부서의 사원들의 사번, 이름, 직책 그리고 급여를 조회하고 급여 순으로 출력하세요.
select a.emp_no, a.first_name, b.title, c.salary
from employees a, titles b, salaries c, dept_emp d
where a.emp_no = b.emp_no and b.emp_no = c.emp_no and c.emp_no = d.emp_no
and b.to_date = '9999-01-01' and c.to_date = '9999-01-01' and d.to_date = '9999-01-01'
and d.dept_no = (select a.dept_no
                 from dept_emp a, salaries b
                 where a.emp_no = b.emp_no
                   and a.to_date = '9999-01-01' and b.to_date = '9999-01-01'
                 group by a.dept_no
                 order by avg(b.salary) desc
                 limit 1)
order by c.salary desc;

-- 문제6.
-- 현재, 평균 급여가 가장 높은 부서의 이름 그리고 평균급여를 출력하세요.
select c.dept_name, avg(b.salary) as avg_salary
 from dept_emp a, salaries b, departments c
 where a.emp_no = b.emp_no and a.dept_no = c.dept_no
   and a.to_date = '9999-01-01' and b.to_date = '9999-01-01'
 group by a.dept_no
 order by avg_salary desc
 limit 1;

-- 문제7.
-- 현재, 평균 급여가 가장 높은 직책의 타이틀 그리고 평균급여를 출력하세요.
select a.title, avg(b.salary) as avg_salary
from titles a, salaries b
where a.emp_no = b.emp_no
  and a.to_date = '9999-01-01' and b.to_date = '9999-01-01'
group by a.title
order by avg_salary desc
limit 1;