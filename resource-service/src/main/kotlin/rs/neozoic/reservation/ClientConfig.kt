package rs.neozoic.reservation

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import rs.neozoic.reservation.domain.constants.DateConstants
import java.text.SimpleDateFormat
import java.util.*

@Configuration
class ClientConfig {
    @Bean
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setDateFormat(SimpleDateFormat(DateConstants.DATE_FORMAT))
            .setTimeZone(TimeZone.getTimeZone(DateConstants.TIME_ZONE))
    }
}