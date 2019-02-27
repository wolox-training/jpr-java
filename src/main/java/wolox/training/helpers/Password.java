package wolox.training.helpers;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Password {
    @NotNull
    @NotEmpty
    private String password;

    public String getPassword() {
        return password;
    }
}
