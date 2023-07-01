package bg.softuni.accounts;

import bg.softuni.accounts.services.AccountService;
import bg.softuni.accounts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class ConsoleRunner implements CommandLineRunner {

    private final AccountService accountService;

    private final UserService userService;

    @Autowired
    public ConsoleRunner(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
//        User someuser = new User("someuser", 20);
//        userService.register(someuser);

        accountService.depositMoney(BigDecimal.valueOf(0), 1L);


    }
}
