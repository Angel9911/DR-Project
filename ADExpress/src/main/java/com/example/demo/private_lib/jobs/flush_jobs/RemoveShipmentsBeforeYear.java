package com.example.demo.private_lib.jobs.flush_jobs;

import com.example.demo.models.entity.Packages;
import com.example.demo.repositories.PackageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class RemoveShipmentsBeforeYear {
    private static final Logger logger = LoggerFactory.getLogger(RemoveShipmentsBeforeYear.class);

    @Autowired
    private PackageRepository packageRepository;
    private final LinkedHashMap<Long, Date> shipmentsToDelete;

    public RemoveShipmentsBeforeYear() {
        this.shipmentsToDelete = new LinkedHashMap<>();
    }


    //@Scheduled(fixedRate = 1000)
    public void execute() throws InterruptedException {

        List<Packages> getAllShipments = packageRepository.findAll(); // TODO: add this implementation in Service class(PackagesServiceImpl)

        for(Packages shipment: getAllShipments){

            Date deliveryShipmentDate = shipment.getDate_delivery_package();

            if(deliveryShipmentDate != null){

                shipmentsToDelete.put(shipment.getPackageId(),deliveryShipmentDate);
            }
        }

        if(!shipmentsToDelete.isEmpty()){

            TreeMap<Long, Long> shipmentPastDeliveryDays = this.getShipmentDeliveryDays();
            shipmentPastDeliveryDays.forEach((shipmentId,countDays) -> logger.info("Shipment id -> count days passed: {} -> {}", shipmentId, countDays));

            List<Integer> deliveredShipmentsBeforeYear = this.getDeliveredShipmentsBeforeYear(shipmentPastDeliveryDays);

            deliveredShipmentsBeforeYear.forEach((shipmentId) -> logger.info(" Shipment id delivered before year -> {}",shipmentId));

            this.removeShipmentsBeforeYear(deliveredShipmentsBeforeYear);
        }else{
            logger.info("There are no shipments delivered before year");
        }
    }

    private void removeShipmentsBeforeYear(List<Integer> deliveredShipmentsBeforeYear) {
        //packageRepository.deleteAllById(deliveredShipmentsBeforeYear); // TODO: add this implementation in Service class(PackagesServiceImpl)
    }

    private List<Integer> getDeliveredShipmentsBeforeYear(TreeMap<Long, Long> shipmentPastDeliveryDays) {
        List<Integer> shipmentKeys = new ArrayList<>();

        shipmentPastDeliveryDays.forEach((key, value) -> {

            int pastDays = value.intValue();
            // if one year has passed
            if (pastDays >= 365) {
                shipmentKeys.add(key.intValue());
            }
        });
        return shipmentKeys;
    }

    private TreeMap<Long, Long> getShipmentDeliveryDays() {

        TreeMap<Long,Long> getShipmentsDeliveryDays = new TreeMap<>();

        LocalDate getCurrentDate = java.time.LocalDate.now();

        for(Map.Entry<Long, Date>shipment:shipmentsToDelete.entrySet()){

            Date deliveryDate = shipment.getValue();

            LocalDate convertDate = deliveryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            long countPastDays = convertDate.datesUntil(getCurrentDate).count();
            getShipmentsDeliveryDays.put(shipment.getKey(),countPastDays);
        }
        return getShipmentsDeliveryDays;
    }

}
