CREATE OR REPLACE PROCEDURE find_sal(c_id customers.id%TYPE) IS c_sal customers.salary%TYPE; 
BEGIN 
  SELECT salary INTO c_sal 
  FROM customers 
  WHERE id = c_id; 
  dbms_output.put_line('Salary: '|| c_sal); 
END find_sal;