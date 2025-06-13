package student.point.web.api;

import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import student.point.domain.Accounts;
import student.point.repository.AccountsRepository;
import student.point.service.api.dto.LoginRequest;

@Service
public class LoginDelegateImpl implements LoginApiDelegate {

    private final AccountsRepository accountsRepository;

    public LoginDelegateImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public ResponseEntity<Map<String, Object>> login(LoginRequest loginRequest) throws Exception {
        Accounts accounts = accountsRepository.findFirstByLoginAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        if (Objects.isNull(accounts)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
