package ua.vslobo.examples.rich.model.extreme;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.vslobo.examples.anemic.model.mock.ExpenseTypeDictEntity;
import ua.vslobo.examples.layered.architecture.mock.AuthenticationService;
import ua.vslobo.examples.layered.architecture.mock.ExpenseDTO;
import ua.vslobo.examples.layered.architecture.mock.UserEntity;
import ua.vslobo.examples.rich.model.extreme.mock.ExpenseRepo;
import ua.vslobo.examples.rich.model.extreme.mock.ExpenseTypeDictRepo;
import ua.vslobo.examples.rich.model.extreme.mock.UserRepo;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private Integer price;

    @Column
    private String comment;

    @Column
    private LocalDateTime date;

    @Column
    private UserEntity user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "expense_to_expense_type_dict",
            joinColumns = @JoinColumn(name = "expense_id"),
            inverseJoinColumns = @JoinColumn(name = "expense_type_id"))
    private List<ExpenseTypeDictEntity> expenseTypeDict;
    // Other columns


    @Autowired // The model has dependency from other layers and from infrastructure
    private ExpenseRepo expenseRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ExpenseTypeDictRepo expenseTypeDictRepo;
    @Autowired
    private AuthenticationService authenticationService;
    // Other injections



    @Transactional
    public void createExpense(ExpenseDTO expenseDTO) {
        // model does too much!
        validateExpense(expenseDTO);
        ExpenseEntity expenseEntity = new ExpenseEntity();
        initExpenseEntity(expenseEntity, expenseDTO);
        expenseRepo.save(expenseEntity);
    }

    private void validateExpense(ExpenseDTO expenseDTO) {
        // validations (DTO, permissions, database, etc)
    }

    private void initExpenseEntity(ExpenseEntity expenseEntity, ExpenseDTO expenseDTO) {
        UserEntity userEntity = authenticationService.getLoggedUser();

        expenseEntity.setUser(userEntity);
        expenseEntity.setPrice(expenseDTO.getPrice());
        expenseEntity.setComment(expenseDTO.getComment());
        expenseEntity.setDate(LocalDateTime.now());
        initExpenseTypes(expenseEntity, expenseDTO.getTypes());
    }

    private void initExpenseTypes(ExpenseEntity expenseEntity, List<String> types) {
        // Model knows about datasource!
        List<ExpenseTypeDictEntity> expenseTypes = new ArrayList<>();

        types.forEach(x -> {
            ExpenseTypeDictEntity typeDict = expenseTypeDictRepo // runs queries
                    .findByNameIgnoreCase(x.trim())
                    .orElseGet(() -> createExpenseTypeDict(x));
            typeDict.setUsedCount(typeDict.getUsedCount() + 1);
            typeDict = expenseTypeDictRepo.save(typeDict); // persists to a DB other objects!
            expenseTypes.add(typeDict);
        });

        expenseEntity.setExpenseTypeDict(expenseTypes);
    }

    private ExpenseTypeDictEntity createExpenseTypeDict(String name) {
        ExpenseTypeDictEntity type = new ExpenseTypeDictEntity();
        // init expense type
        return type;
    }
}
