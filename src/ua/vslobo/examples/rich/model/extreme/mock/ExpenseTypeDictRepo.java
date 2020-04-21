package ua.vslobo.examples.rich.model.extreme.mock;

import ua.vslobo.examples.anemic.model.mock.ExpenseTypeDictEntity;

import java.util.Optional;

public interface ExpenseTypeDictRepo {
    Optional<ExpenseTypeDictEntity> findByNameIgnoreCase(String trim);

    ExpenseTypeDictEntity save(ExpenseTypeDictEntity typeDict);
}
