package rs.neozoic.reservation

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import rs.neozoic.reservation.domain.constants.DateConstants
import tools.jackson.databind.ObjectMapper
import tools.jackson.module.kotlin.jsonMapper
import java.text.SimpleDateFormat
import java.util.*

@Configuration
class ClientConfig {
    @Bean
    fun objectMapper(): ObjectMapper {
        return jsonMapper {
            defaultDateFormat(SimpleDateFormat(DateConstants.DATE_FORMAT))
            defaultTimeZone(TimeZone.getTimeZone(DateConstants.TIME_ZONE))
        }
    }
}