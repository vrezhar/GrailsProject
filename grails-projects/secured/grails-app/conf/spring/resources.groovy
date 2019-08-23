package spring

import com.secured.auth.UserPasswordEncoderListener
import com.secured.auth.CoordinateValidatorService
import com.secured.auth.TwoFactorAuthenticationDetailsSource
import com.secured.auth.TwoFactorAuthenticationProvider
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy
// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener)

    authenticationDetailsSource(TwoFactorAuthenticationDetailsSource)

    coordinateValidator(CoordinateValidatorService)

    twoFactorAuthenticationProvider(TwoFactorAuthenticationProvider) {
        coordinateValidator = ref('coordinateValidator')
        userDetailsService = ref('userDetailsService')
        passwordEncoder = ref('passwordEncoder')
        userCache = ref('userCache')
        saltSource = ref('saltSource')
        preAuthenticationChecks = ref('preAuthenticationChecks')
        postAuthenticationChecks = ref('postAuthenticationChecks')
        authoritiesMapper = ref('authoritiesMapper')
        hideUserNotFoundExceptions = true
    }

    sessionAuthenticationStrategy(NullAuthenticatedSessionStrategy)
}
