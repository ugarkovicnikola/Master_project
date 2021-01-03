package ef.master.faq.exception;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlers {

  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @ExceptionHandler(value = {BusinessException.class})
  public String handleBusinessException(BusinessException exception) {

    return exception.getMessage();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = {ConstraintViolationException.class})
  public Set<String> handleConstraintViolationException(ConstraintViolationException exception) {

    return exception.getConstraintViolations().stream()
        .map(violation -> String.format("%s %s", ((PathImpl) violation
                .getPropertyPath())
                .getLeafNode(),
            violation.getMessage()))
        .collect(Collectors.toSet());
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(value = {EntityNotFoundException.class})
  public String handleEntityNotFoundException(EntityNotFoundException exception) {

    return exception.getMessage();
  }
}
