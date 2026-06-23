package com.apelisser.algashop.billing.infrastructure;

import com.apelisser.algashop.billing.utils.TestcontainerPostgreSqlConfig;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestcontainerPostgreSqlConfig.class})
public abstract class AbstractInfrastructureIT {

}
