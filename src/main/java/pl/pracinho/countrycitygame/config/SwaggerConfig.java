package pl.pracinho.countrycitygame.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@OpenAPIDefinition(
        info = @Info(
                title = Properties.NAME,
                version = Properties.VERSION,
                description = Properties.DESCRIPTION
        )
)
public class SwaggerConfig {
}
