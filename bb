package com.treespond.core.interceptors;

  import com.treespond.core.beans.OXUser;
  import com.treespond.core.security.IOXSecurity;
  import org.slf4j.Logger;
  import org.slf4j.LoggerFactory;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.http.HttpRequest;
  import org.springframework.http.MediaType;
  import org.springframework.http.client.ClientHttpRequestExecution;
  import org.springframework.http.client.ClientHttpRequestInterceptor;
  import org.springframework.http.client.ClientHttpResponse;
  import org.springframework.stereotype.Component;

  import java.io.IOException;

  @Component
public class BearerHttpInterseptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(BearerHttpInterseptor.class);

    @Autowired
    IOXSecurity sec;

    public BearerHttpInterseptor() {

    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        if(sec.isUserAuthenticated()){
            OXUser u = sec.getCurrentUser();
            String accessToken= u.getToken().getAccessToken();
            logger.info("adding bearer to header");
//            String token = Base64Utils.encodeToString((this.username + ":" + this.password).getBytes(StandardCharsets.UTF_8));
            request.getHeaders().add("Authorization", "Bearer " + accessToken);
            request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            //            retu            request.getHeaders().setContentType();rn execution.execute(request, body);
        }else {
            logger.info("not intercepting cause user is not authenticated");
        }


        return execution.execute(request, body);
    }
}
