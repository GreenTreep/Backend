package fr.parisnanterre.greentrip.backend.service;

import fr.parisnanterre.greentrip.backend.entity.InvalidatedToken;
import fr.parisnanterre.greentrip.backend.repository.InvalidatedTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogoutService {

    private final InvalidatedTokenRepository tokenRepository;

    public LogoutService(InvalidatedTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public void logout(String token) {
        InvalidatedToken invalidatedToken = new InvalidatedToken();
        invalidatedToken.setToken(token);
        invalidatedToken.setInvalidatedAt(new Date());
        tokenRepository.save(invalidatedToken);
    }

    public boolean isTokenInvalidated(String token) {
        return tokenRepository.existsByToken(token);
    }
}

