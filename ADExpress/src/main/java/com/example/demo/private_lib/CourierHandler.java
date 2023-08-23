package com.example.demo.private_lib;

import com.example.demo.models.entity.Courier;
import com.example.demo.models.entity.Roles;
import com.example.demo.models.entity.User_account;
import com.example.demo.repositories.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

public final class CourierHandler {
    private static CourierRepository courierRepository;
    private static final Random randomCouriers = new Random();
    @Value("${adexpress.app.courier.password}")
    private static String passwordSecret;
    private static PasswordEncoder encoder;

    private CourierHandler(){

    }

    public static User_account createCourierAccount(String fName, String lName, List<Roles> roles, PasswordEncoder encoder){
        String usernameCourier = fName.charAt(0) +lName;
        String passwordCourier = usernameCourier + passwordSecret;
        User_account user_account = new User_account(usernameCourier,encoder.encode(passwordCourier));
        user_account.setUserRoles(roles);
        return user_account;
    }

    public static Courier getRandomCourier(Map<Courier, Integer> couriers) {
        int randomKey = 0;
        Set<Courier> keySet = couriers.keySet();
        List<Courier> keyList = new ArrayList<>(keySet);
        int countCouriers = keyList.size();
        for (int i = 0; i < countCouriers; i++) {
            randomKey = randomCouriers.nextInt(countCouriers);
        }
        Courier randomCourier = keyList.get(randomKey);
        Integer randomValue = couriers.get(randomCourier);
        System.out.println("test random" + randomCourier.getPhone() + " " + randomValue);
        return randomCourier;
    }

    public static Boolean IfCourierHasEqualCountPackages(Map<Courier, Integer> couriers) {
        List<Integer> ListCouriers = new ArrayList<>(couriers.values());
        List<Integer> duplicateList = new ArrayList<>();
        for (int i = 0; i < ListCouriers.size(); i++) {
            for (int j = 1; j < ListCouriers.size(); j++) {
                if (ListCouriers.get(i).equals(ListCouriers.get(j)) && i != j) {
                    duplicateList.add(ListCouriers.get(i));
                    break;
                }
            }
        }
        System.out.println(duplicateList.isEmpty());
        if (duplicateList.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static Courier getCourierByCity(Map<Courier, Integer> couriers) {
        Courier getCourier = new Courier();
        List<Integer> sortedList = new ArrayList<>(couriers.values());
        for (Map.Entry<Courier, Integer> entry : couriers.entrySet()) {
            if (entry.getValue().equals(sortedList.get(0))) {
                System.out.println(entry.getKey().getPhone());
                getCourier.setCourier_id(entry.getKey().getCourier_id());
            }
        }
        return getCourier;
    }

}
