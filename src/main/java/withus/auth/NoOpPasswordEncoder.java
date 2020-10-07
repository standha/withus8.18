package withus.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class NoOpPasswordEncoder implements PasswordEncoder {
    private static NoOpPasswordEncoder singleton;

    private NoOpPasswordEncoder() {
    }

    public static NoOpPasswordEncoder getInstance() {
        if (singleton == null) {
            singleton = new NoOpPasswordEncoder();
        }

        return singleton;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return String.valueOf(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return String.valueOf(rawPassword).equals(encodedPassword);
    }
}
