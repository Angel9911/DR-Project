package com.example.demo.private_lib;

import com.example.demo.model.Customer;
import com.example.demo.model.Packages;
import com.example.demo.private_lib.impl.UserImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public abstract class User {
    public abstract Object Login(String username) throws ValidationException;
    public abstract Object Update(Object object);
    public abstract void Insert(Object object);
}
