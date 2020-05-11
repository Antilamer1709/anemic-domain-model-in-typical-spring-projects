package ua.vslobo.examples.refactoring;

import org.springframework.beans.factory.annotation.Autowired;
import ua.vslobo.examples.layered.architecture.mock.ExpenseDTO;
import ua.vslobo.examples.layered.architecture.mock.ExpenseEntity;
import ua.vslobo.examples.layered.architecture.mock.ExpenseRepo;
import ua.vslobo.examples.layered.architecture.mock.UserEntity;

public class ExtractMethod {

    @Autowired
    private ExpenseRepo expenseRepo;

    // Before
    public void createExpense(ExpenseDTO expenseDTO) {
        ExpenseEntity expenseEntity = new ExpenseEntity();
        expenseEntity.setUser(getLoggedUser());
        expenseEntity.setPrice(expenseDTO.getPrice());
        expenseEntity.setComment(expenseDTO.getComment());
        // imagine that there are many other setters

        expenseRepo.save(expenseEntity);
    }


    // After
    public void createExpenseRefactored(ExpenseDTO expenseDTO) {
        ExpenseEntity expenseEntity = createInitializedExpenseEntity(expenseDTO);
        expenseRepo.save(expenseEntity);
    }

    private ExpenseEntity createInitializedExpenseEntity(ExpenseDTO expenseDTO) {
        ExpenseEntity expenseEntity = new ExpenseEntity();
        expenseEntity.setUser(getLoggedUser());
        expenseEntity.setPrice(expenseDTO.getPrice());
        expenseEntity.setComment(expenseDTO.getComment());
        // other setters

        return expenseEntity;
    }




    // Silly mock method, we need it to compile code
    UserEntity getLoggedUser() {
        return new UserEntity();
    }

}
