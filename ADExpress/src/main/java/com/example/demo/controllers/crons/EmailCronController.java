package com.example.demo.controllers.crons;


import com.example.demo.services.Impl.PromotionService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/cron/email")
public class EmailCronController {

    private final PromotionService promotionService;

    @Autowired
    public EmailCronController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @PostMapping(value="/send/promotion", produces = "application/json")
    public ResponseEntity<Boolean> sendPromotionToUsers(@RequestParam(value = "interval") String interval,
                                                  @RequestParam(value = "from") String from,
                                                  @RequestParam(value = "subject") String subject,
                                                  @RequestBody String message) throws SchedulerException {

        if (interval.isEmpty()) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        } else {
            this.promotionService.schedulePromotionJob(interval,from,subject,message);
            return new ResponseEntity<>(true,HttpStatus.OK);
        }
    }
}
