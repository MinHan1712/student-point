package student.point.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AccountsCriteriaTest {

    @Test
    void newAccountsCriteriaHasAllFiltersNullTest() {
        var accountsCriteria = new AccountsCriteria();
        assertThat(accountsCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void accountsCriteriaFluentMethodsCreatesFiltersTest() {
        var accountsCriteria = new AccountsCriteria();

        setAllFilters(accountsCriteria);

        assertThat(accountsCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void accountsCriteriaCopyCreatesNullFilterTest() {
        var accountsCriteria = new AccountsCriteria();
        var copy = accountsCriteria.copy();

        assertThat(accountsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(accountsCriteria)
        );
    }

    @Test
    void accountsCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var accountsCriteria = new AccountsCriteria();
        setAllFilters(accountsCriteria);

        var copy = accountsCriteria.copy();

        assertThat(accountsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(accountsCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var accountsCriteria = new AccountsCriteria();

        assertThat(accountsCriteria).hasToString("AccountsCriteria{}");
    }

    private static void setAllFilters(AccountsCriteria accountsCriteria) {
        accountsCriteria.id();
        accountsCriteria.accountNumber();
        accountsCriteria.login();
        accountsCriteria.password();
        accountsCriteria.mail();
        accountsCriteria.phone();
        accountsCriteria.notes();
        accountsCriteria.status();
        accountsCriteria.createdBy();
        accountsCriteria.createdDate();
        accountsCriteria.lastModifiedBy();
        accountsCriteria.lastModifiedDate();
        accountsCriteria.distinct();
    }

    private static Condition<AccountsCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getAccountNumber()) &&
                condition.apply(criteria.getLogin()) &&
                condition.apply(criteria.getPassword()) &&
                condition.apply(criteria.getMail()) &&
                condition.apply(criteria.getPhone()) &&
                condition.apply(criteria.getNotes()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AccountsCriteria> copyFiltersAre(AccountsCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getAccountNumber(), copy.getAccountNumber()) &&
                condition.apply(criteria.getLogin(), copy.getLogin()) &&
                condition.apply(criteria.getPassword(), copy.getPassword()) &&
                condition.apply(criteria.getMail(), copy.getMail()) &&
                condition.apply(criteria.getPhone(), copy.getPhone()) &&
                condition.apply(criteria.getNotes(), copy.getNotes()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
