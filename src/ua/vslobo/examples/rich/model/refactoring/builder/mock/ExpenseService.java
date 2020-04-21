package ua.vslobo.examples.rich.model.refactoring.builder.mock;

import ua.vslobo.examples.layered.architecture.mock.ExpenseDTO;

public interface ExpenseService {
    void createExpense(ExpenseDTO expenseDTO);
    void createExpenseBefore(ExpenseDTO expenseDTO);
}
