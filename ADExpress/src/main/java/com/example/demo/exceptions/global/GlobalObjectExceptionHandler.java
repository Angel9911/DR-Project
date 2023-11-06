package com.example.demo.exceptions.global;

import com.example.demo.config.JwtAuthEntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalObjectExceptionHandler extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(GlobalObjectExceptionHandler.class);

    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView handleObjectNotFoundException(ObjectNotFoundException e){

        /*ModelAndView modelAndView = new ModelAndView("object-not-found");
        modelAndView.addObject("object", e.getUserMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body().build(); */
        ModelAndView modelAndView = new ModelAndView("object-not-found");

        modelAndView.setStatus(HttpStatus.NOT_FOUND);

        logger.info(" not found "+e.getUserMessage());

        return modelAndView;
    }

    @ExceptionHandler(ObjectNotValidException.class)
    public ModelAndView handleObjectNotValidException(ObjectNotValidException e){

        ModelAndView modelAndView = new ModelAndView("object-not-valid");
        modelAndView.addObject("object",e.getMessage());
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);

        logger.info(" not valid "+e.getMessage());

        return modelAndView;
    }
}
