//package sbnz.ftn.uns.ac.rs.ADMIN.initializator;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.stereotype.Component;

//@Component
//public class MongoDataInitializator implements ApplicationListener<ContextRefreshedEvent> {
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        System.out.println("POZVAN");
//        mongoTemplate.getDb().drop();
//        mongoTemplate.getDb().createCollection("DB");
//        // add more collections to be recreated
//    }
//}
