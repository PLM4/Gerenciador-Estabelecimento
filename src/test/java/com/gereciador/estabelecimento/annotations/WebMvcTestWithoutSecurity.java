package com.gereciador.estabelecimento.annotations;

import com.gereciador.estabelecimento.config.SecurityFilter;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@WebMvcTest(
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = { SecurityFilter.class }
        )
    }
)
@AutoConfigureMockMvc(addFilters = false)
public @interface WebMvcTestWithoutSecurity {
  Class<?>[] controllers() default {};
}
