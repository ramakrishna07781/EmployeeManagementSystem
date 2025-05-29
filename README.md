There are two users - Manager, Employee.
Manager can create a task, assign it to an employee who is free, get task details whenever needed, delete them.
Manager can get a list of unassigned employees and can choose one of them.
Refer to Task.java for schema.
Employees on the other hand, can update the status of the task like IN_PROGRESS, COMPLETED, etc...
Refer to Employee.java for the schema.
Regestration module is yet to be pushed.
Also, i used PathVariable in ManagerController.java after which i remembered that we can use RequestParam, So used it in EmployeeController.java. 
