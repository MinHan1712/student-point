package student.point.service.mapper;

import org.mapstruct.*;
import student.point.domain.Accounts;
import student.point.service.dto.AccountsDTO;

/**
 * Mapper for the entity {@link Accounts} and its DTO {@link AccountsDTO}.
 */
@Mapper(componentModel = "spring")
public interface AccountsMapper extends EntityMapper<AccountsDTO, Accounts> {}
