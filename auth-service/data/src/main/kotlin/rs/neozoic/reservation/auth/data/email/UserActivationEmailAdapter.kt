package rs.neozoic.reservation.auth.data.email

import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import rs.neozoic.reservation.domain.model.User
import rs.neozoic.reservation.domain.port.UserActivationEmailPort

@Component
class UserActivationEmailAdapter(
    private val mailSender: JavaMailSender,
    @Value("\${app.activation-base-url}") private val activationBaseUrl: String
) : UserActivationEmailPort {

    override fun sendActivationEmail(user: User, activationToken: String) {
        val message: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")
        helper.setTo(user.email)
        helper.setSubject("Activate your account")
        val link = "$activationBaseUrl?token=$activationToken"
        helper.setText(
            """
            <p>Hello ${user.firstName},</p>
            <p>Please activate your account by clicking the link below:</p>
            <p><a href="$link">Activate account</a></p>
            <p>The link expires in 24 hours.</p>
            """.trimIndent(),
            true
        )
        mailSender.send(message)
    }
}
