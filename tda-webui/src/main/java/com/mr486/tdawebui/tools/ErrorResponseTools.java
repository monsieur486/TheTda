package com.mr486.tdawebui.tools;

import com.mr486.tdawebui.dto.ErrorMessage;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class ErrorResponseTools {

    public ErrorMessage getErrorMessageFromJson(String json, String microserviceName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, ErrorMessage.class);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setStatus(503);
            errorMessage.setMessage(microserviceName + " ne répond pas.");
            return errorMessage;
        }
    }

    public ErrorMessage getErrorMessage(String exceptionMessage, String microserviceName) {
        try {
            int firstBrace = exceptionMessage.indexOf('{');
            int lastBrace = exceptionMessage.lastIndexOf('}');
            String json = exceptionMessage.substring(firstBrace, lastBrace + 1);

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, ErrorMessage.class);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setStatus(503);
            errorMessage.setMessage(microserviceName + " ne répond pas.");
            return errorMessage;
        }
    }
}
