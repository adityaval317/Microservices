package com.aditya.microservices.accounts;

import com.aditya.microservices.accounts.dto.AccountContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountContactInfoDto.class})
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts Microservices REST API Documentation",
				description = "BankEazy Accounts Microservices REST API Documentation",
				version = "v1",
				contact = @Contact(name = "Aditya Valiveti", email = "adityavaliveti@example.com"),
				license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
				termsOfService = "https://www.apache.org/licenses/LICENSE-2.0"
		),
		externalDocs = @ExternalDocumentation(
				description = "BankEazy Accounts Microservices Documentation"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
