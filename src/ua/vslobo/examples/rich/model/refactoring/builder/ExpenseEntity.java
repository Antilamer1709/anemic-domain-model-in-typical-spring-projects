package ua.vslobo.examples.rich.model.refactoring.builder;

import lombok.Data;
import ua.vslobo.examples.layered.architecture.mock.ExpenseDTO;
import ua.vslobo.examples.layered.architecture.mock.UserEntity;
import ua.vslobo.examples.rich.model.extreme.mock.ExpenseTypeDictEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "expense_to_expense_type_dict", schema = "expenses",
            joinColumns = @JoinColumn(name = "expense_id"),
            inverseJoinColumns = @JoinColumn(name = "expense_type_id"))
    private List<ExpenseTypeDictEntity> expenseTypeDict;



    // Could be in separate file or package
    public static class Builder {

        private ExpenseEntity expense;

        public Builder() {
            expense = new ExpenseEntity();
        }

        public Builder fromDTO(ExpenseDTO expenseDTO) {
            expense.price = expenseDTO.getPrice();
            expense.comment = expenseDTO.getComment();
            if (expenseDTO.getDate() != null)
                expense.date = expenseDTO.getDate();
            else
                expense.date = LocalDateTime.now();

            return this;
        }

        public Builder withUser(UserEntity user) {
            expense.user = user;
            return this;
        }

        public Builder withExpenseTypes(List<ExpenseTypeDictEntity> expenseTypes) {
            expense.expenseTypeDict = expenseTypes;
            return this;
        }

        public ExpenseEntity build() {
            return expense;
        }
    }

}
