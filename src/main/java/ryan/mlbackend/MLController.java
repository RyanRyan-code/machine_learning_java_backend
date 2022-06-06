package ryan.mlbackend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ryan.mlbackend.entity.Session;
import ryan.mlbackend.entity.User;
import ryan.mlbackend.repository.SessionRepository;
import ryan.mlbackend.repository.UserRepository;
import ryan.mlbackend.mapping_return_result.LoginResult;
import java.time.LocalDate;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class MLController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;

    // validate cookie
    @GetMapping("/check")
    public boolean check(@CookieValue(value = "email",defaultValue = "") String email, @CookieValue(value = "s_password",defaultValue = "") String s_password) {

        boolean res = false;

        Session s = sessionRepository.findByEmail(email).orElse(null);
        if(s!=null){
            if(Objects.equals(s_password, s.getSessionPassword()) && LocalDate.now().isBefore(s.getExpirationDate())){
                res = true;
            }
        }
        return res;
    }

    @GetMapping("/getUser/")
    public User getUser(@CookieValue(value = "email",defaultValue = "") String email, @CookieValue(value = "s_password",defaultValue = "") String s_password){

        User u = null;




        return u;
    }

    //first check the login details, then check the session table
    //if the user already has a session, update it
    @PostMapping("/login")
    public LoginResult login(@RequestBody String s) throws JsonProcessingException {

        LoginResult returnResult = new LoginResult();

        //use java jackson to parse string to json
        ObjectMapper mapper = new ObjectMapper();
        JsonNode parsedS = mapper.readTree(s);

        User u = userRepository.findByEmail(parsedS.get("email").textValue()).orElse(null);

        //email exists in users table
        if(u != null){

            //set email
            returnResult.setEmail(u.getEmail());

            //validate login password
            String password = u.getPassword();
            if(Objects.equals(password, parsedS.get("password").textValue())){
                //now returnResult has
                returnResult.setLoginDetailsRight(true);
                System.out.println("email and password match!");
            }

            //login details are correct
            if(returnResult.isLoginDetailsRight()){

                Session existingSession = sessionRepository.findByEmail(u.getEmail()).orElse(null);

                //delete outdated cookies
                if(existingSession!=null && LocalDate.now().isBefore(existingSession.getExpirationDate())){
                    sessionRepository.delete(existingSession);
                }

                //check again whether the email exists in sessions table after possible deletion
                existingSession = sessionRepository.findByEmail(u.getEmail()).orElse(null);

                //email doesn't exist in sessions table
                if(existingSession==null){

                    LocalDate date = LocalDate.now().plusDays(14);

                    RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange('0', '9')
                            .build();
                    String generatedPasswordForCookie = pwdGenerator.generate(10);

                    //save new entry in sessions table
                    Session newSession = new Session(u.getEmail(), generatedPasswordForCookie, date);
                    sessionRepository.save(newSession);

                    returnResult.setSessionPassword(generatedPasswordForCookie);

                //email exists in sessions table
                }else{
                    returnResult.setSessionPassword(existingSession.getSessionPassword());
                }
            }
        }

        return returnResult;
    }

    @PostMapping("/signup")
    public boolean signUp(@RequestBody String s) throws JsonProcessingException {
        boolean res=false;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode parsedS = mapper.readTree(s);

        User newUser = new User();
        newUser.setFirst_name(parsedS.get("firstName").textValue());
        newUser.setLast_name(parsedS.get("lastName").textValue());
        newUser.setEmail(parsedS.get("email").textValue());
        newUser.setPassword(parsedS.get("password").textValue());

        User savedUser = userRepository.save(newUser);
        if(savedUser.getEmail().length()>0){
            res = true;
        }

        return res;
    }

    @PostMapping("/emailIsNotTaken")
    public boolean emailIsNotTaken(@RequestBody String s){

        System.out.println("received request on emailIsNotTaken");

        return userRepository.findByEmail(s).isEmpty();

    }

    @PostMapping("/emailPassesTests")
    public boolean emailPassesTests(@RequestBody String s){

        System.out.println("received request on emailPassesTests");

        return s.contains("@") && (s.contains(".com") || s.contains(".au"));
    }

    @PostMapping("/passwordPassesTests")
    public boolean passwordPassesTests(@RequestBody String s){
        return s.length()>=8;
    }



}
