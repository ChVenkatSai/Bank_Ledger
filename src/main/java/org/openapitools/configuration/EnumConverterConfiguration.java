package org.openapitools.configuration;

import dev.codescreen.model.DebitCredit;
import dev.codescreen.model.ResponseCode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class EnumConverterConfiguration {

    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.debitCreditConverter")
    Converter<String, DebitCredit> debitCreditConverter() {
        return new Converter<String, DebitCredit>() {
            @Override
            public DebitCredit convert(String source) {
                return DebitCredit.fromValue(source);
            }
        };
    }
    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.responseCodeConverter")
    Converter<String, ResponseCode> responseCodeConverter() {
        return new Converter<String, ResponseCode>() {
            @Override
            public ResponseCode convert(String source) {
                return ResponseCode.fromValue(source);
            }
        };
    }

}
