package ua.vslobo.examples.rich.model.fetch;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ua.vslobo.examples.rich.model.extreme.mock.ExpenseTypeDictEntity;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table
public class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    // Other fields


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "expense_to_expense_type_dict", schema = "expenses",
            joinColumns = @JoinColumn(name = "expense_id"),
            inverseJoinColumns = @JoinColumn(name = "expense_type_id"))
    @Fetch(FetchMode.SUBSELECT) // when we call one element of the list - hibernate will query whole list instead.
    private List<ExpenseTypeDictEntity> expenseTypeDict;


    // Other methods
}
