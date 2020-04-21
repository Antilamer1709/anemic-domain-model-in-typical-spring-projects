package ua.vslobo.examples.layered.architecture.mock;

public interface ExpenseService {
    void createExpense(ExpenseDTO expenseDTO);
    void createExpenseBefore(ExpenseDTO expenseDTO);
}
