package fr.parisnanterre.greentrip.backend.controller.auth;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
