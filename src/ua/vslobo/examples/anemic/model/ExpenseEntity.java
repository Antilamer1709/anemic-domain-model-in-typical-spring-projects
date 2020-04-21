package ua.vslobo.examples.anemic.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ua.vslobo.examples.anemic.model.mock.ExpenseTypeDictEntity;
import ua.vslobo.examples.layered.architecture.mock.UserEntity;

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
    @JoinTable(name = "expense_to_expense_type_dict",
            joinColumns = @JoinColumn(name = "expense_id"),
            inverseJoinColumns = @JoinColumn(name = "expense_type_id"))
    @Fetch(FetchMode.SUBSELECT)
    private List<ExpenseTypeDictEntity> expenseTypeDict;

}
