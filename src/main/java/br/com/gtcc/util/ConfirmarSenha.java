package br.com.gtcc.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ConfirmarSenhaValidador.class})
public @interface ConfirmarSenha {
 
    String message() default "Campos não corresponde";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String field() default "confirmarSenha";
    
    String baseField();
 
    String matchField();
 
}
