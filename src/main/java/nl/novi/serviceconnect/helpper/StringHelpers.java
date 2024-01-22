package nl.novi.serviceconnect.helpper;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class StringHelpers {
    public static boolean isNotNullOrEmpty(String value) { return value != null && !value.trim().isEmpty(); }
    public static ResponseEntity<Object> getObjectResponseEntity(BindingResult br) {
        StringBuilder sb = new StringBuilder();
        for (FieldError fe : br.getFieldErrors()) {
            sb.append(fe.getField());
            sb.append(" : ");
            sb.append(fe.getDefaultMessage());
            sb.append("\n");
        }
        return ResponseEntity.badRequest().body(sb.toString());
    }
}
