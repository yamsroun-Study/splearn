package yamsroun.splearnself;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class SplearnSelfApplicationTest {

    @Test
    void run() {
        try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            SplearnSelfApplication.main(new String[0]);
            mocked.verify(() -> SpringApplication.run(SplearnSelfApplication.class));
        }
    }
}
