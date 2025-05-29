There are two users - Manager, Employee.
Manager can create a task, assign it to an employee who is free, get task details whenever needed, delete them.
Manager can get a list of unassigned employees and can choose one of them.
Once the manager assigns a task to an employee then the task status will automatically be updated to ASSIGNED and the employee's assigned status will be changed to true (it's a boolean value), so that that employee cannot be assigned with a new task until the current task is completed.
Refer to Task.java for schema.
Employees on the other hand, can update the status of the task like IN_PROGRESS, COMPLETED, etc...
Once the user changes the status of the task, that will also be reflected in the Task table.
Refer to Employee.java for the schema.
Regestration module is yet to be pushed.
Also, i used PathVariable in ManagerController.java after which i remembered that we can use RequestParam, So used it in EmployeeController.java. 
