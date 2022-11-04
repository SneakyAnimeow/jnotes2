package club.anims.jnotes2.requests;

import com.google.gson.Gson;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest(String json){
        var request = new Gson().fromJson(json, LoginRequest.class);
        this.username = request.username;
        this.password = request.password;
    }
}
