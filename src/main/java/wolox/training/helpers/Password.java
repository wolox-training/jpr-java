package wolox.training.helpers;

import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotEmpty;

public class Password {
    @NotNull
    @NotEmpty
    private String password;

    public String getPassword() {
        return password;
    }
}
