package ua.vslobo.examples.rich.model.refactoring.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vslobo.examples.layered.architecture.mock.AuthenticationService;
import ua.vslobo.examples.layered.architecture.mock.ExpenseDTO;
import ua.vslobo.examples.layered.architecture.mock.UserEntity;
import ua.vslobo.examples.rich.model.refactoring.builder.mock.ExpenseRepo;
import ua.vslobo.examples.rich.model.extreme.mock.ExpenseTypeDictEntity;
import ua.vslobo.examples.rich.model.refactoring.builder.mock.ExpenseService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ExpenseRepo expenseRepo;


    // Before
    @Override
    @Transactional
    public void createExpenseBefore(ExpenseDTO expenseDTO) {
        ExpenseEntity expenseEntity = new ExpenseEntity();
        initExpenseEntity(expenseEntity, expenseDTO);
        expenseRepo.save(expenseEntity);
    }

    // should not be in service
    private void initExpenseEntity(ExpenseEntity expenseEntity, ExpenseDTO expenseDTO) {
        UserEntity userEntity = authenticationService.getLoggedUser();

        expenseEntity.setUser(userEntity);
        expenseEntity.setPrice(expenseDTO.getPrice());
        expenseEntity.setComment(expenseDTO.getComment());
        if (expenseDTO.getDate() != null)
            expenseEntity.setDate(expenseDTO.getDate());
        else
            expenseEntity.setDate(LocalDateTime.now());
        expenseEntity.setExpenseTypeDict(findExpenseTypes(expenseDTO.getTypes()));
    }


    // After
    @Override
    @Transactional
    public void createExpense(ExpenseDTO expenseDTO) {
        ExpenseEntity expenseEntity = createExpenseEntity(expenseDTO);
        expenseRepo.save(expenseEntity);
    }

    private ExpenseEntity createExpenseEntity(ExpenseDTO expenseDTO) {
        return new ExpenseEntity.Builder()
                .fromDTO(expenseDTO)
                .withUser(authenticationService.getLoggedUser())
                .withExpenseTypes(findExpenseTypes(expenseDTO.getTypes()))
                .build();
    }



    // common
    private List<ExpenseTypeDictEntity> findExpenseTypes(List<String> types) {
        List<ExpenseTypeDictEntity> list = new ArrayList<>();
        // find types
        return list;
    }

}
