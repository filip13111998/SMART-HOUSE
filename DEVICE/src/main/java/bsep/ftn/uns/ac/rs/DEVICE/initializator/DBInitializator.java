package bsep.ftn.uns.ac.rs.DEVICE.initializator;

import bsep.ftn.uns.ac.rs.DEVICE.model.*;
import bsep.ftn.uns.ac.rs.DEVICE.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class DBInitializator {

    @Autowired
    private MongoTemplate mongoTemplate;

//    @Autowired
//    private AdminRepository ar;

    @Autowired
    private OwnerRepository or;

    @Autowired
    private TenantRepository tr;

    @Autowired
    private DeviceMessageRepository dmr; // Device message repository

    @Autowired
    private TypeRepository dtr;// Device type repository

    @Autowired
    private DeviceRepository dr;// Device repository

    @Autowired
    private HouseRepository hr; // House repository

    @PostConstruct
    public void write(){
        System.out.println("ADD USERS TO MONGO DB");

        mongoTemplate.dropCollection("DEVICE_TYPE");
        mongoTemplate.dropCollection("DEVICE_MESSAGE");
        mongoTemplate.dropCollection("DEVICE");
        mongoTemplate.dropCollection("HOUSE");
//        mongoTemplate.getDb().drop();
//        mongoTemplate.getDb().createCollection("DB");

        Owner o1 = or.findByUsername("o1");

        Owner o2 = or.findByUsername("o2");

        Owner o3 = or.findByUsername("o3");

        Tenant t1 = tr.findByUsername("t1");

        Tenant t2 = tr.findByUsername("t2");

        Tenant t3 = tr.findByUsername("o2");

        //DEVICE 1
        //------------------------------------------------
        DeviceMessage deviceMessage1 = DeviceMessage.builder()
                .id(UUID.randomUUID().toString())
                .status("NORMAL")
                .text("Temperatura je 20 stepeni")
                .build();

        DeviceMessage deviceMessage2 = DeviceMessage.builder()
                .id(UUID.randomUUID().toString())
                .status("OKAY")
                .text("Temperatura je 30 stepeni")
                .build();

        DeviceMessage deviceMessage3 = DeviceMessage.builder()
                .id(UUID.randomUUID().toString())
                .status("WARNING")
                .text("Temperatura je 37 stepeni")
                .build();

        DeviceMessage deviceMessage4 = DeviceMessage.builder()
                .id(UUID.randomUUID().toString())
                .status("ERROR")
                .text("Temperatura je 50 stepeni")
                .build();

        //DEVICE 2
        //------------------------------------------------
        DeviceMessage deviceMessage5 = DeviceMessage.builder()
                .id(UUID.randomUUID().toString())
                .status("NORMAL")
                .text("Pritisak je 800 mbar")
                .build();

        DeviceMessage deviceMessage6 = DeviceMessage.builder()
                .id(UUID.randomUUID().toString())
                .status("OKAY")
                .text("Pritisak je 1000 mbar")
                .build();

        DeviceMessage deviceMessage7 = DeviceMessage.builder()
                .id(UUID.randomUUID().toString())
                .status("WARNING")
                .text("Pritisak je 1200 mbar")
                .build();

        DeviceMessage deviceMessage8 = DeviceMessage.builder()
                .id(UUID.randomUUID().toString())
                .status("ERROR")
                .text("Pritisak je 1600 mbar")
                .build();


        //DEVICE 3
        //------------------------------------------------
        DeviceMessage deviceMessage9 = DeviceMessage.builder()
                .id(UUID.randomUUID().toString())
                .status("NORMAL")
                .text("Vlaznost je 40 %")
                .build();

        DeviceMessage deviceMessage10 = DeviceMessage.builder()
                .id(UUID.randomUUID().toString())
                .status("OKAY")
                .text("Vlaznost je 50 %")
                .build();

        DeviceMessage deviceMessage11 = DeviceMessage.builder()
                .id(UUID.randomUUID().toString())
                .status("WARNING")
                .text("Vlaznost je 80 %")
                .build();

        DeviceMessage deviceMessage12 = DeviceMessage.builder()
                .id(UUID.randomUUID().toString())
                .status("ERROR")
                .text("Vlaznost je 92 %")
                .build();

        DeviceMessage deviceMessage13 = DeviceMessage.builder()
                .id(UUID.randomUUID().toString())
                .status("ERROR")
                .text("Vlaznost je 97 %")
                .build();

        dmr.save(deviceMessage1);
        dmr.save(deviceMessage2);
        dmr.save(deviceMessage3);
        dmr.save(deviceMessage4);

        dmr.save(deviceMessage5);
        dmr.save(deviceMessage6);
        dmr.save(deviceMessage7);
        dmr.save(deviceMessage8);

        dmr.save(deviceMessage9);
        dmr.save(deviceMessage10);
        dmr.save(deviceMessage11);
        dmr.save(deviceMessage12);
        dmr.save(deviceMessage13);

        List<DeviceMessage> deviceMessageList1 = Arrays.asList(deviceMessage1,deviceMessage2,deviceMessage3,deviceMessage4);

        List<DeviceMessage> deviceMessageList2 = Arrays.asList(deviceMessage5,deviceMessage6,deviceMessage7,deviceMessage8);

        List<DeviceMessage> deviceMessageList3 = Arrays.asList(deviceMessage9,deviceMessage10,deviceMessage11,
                                                                deviceMessage12,deviceMessage13);

        DeviceType deviceType1 = DeviceType.builder()
                .id(UUID.randomUUID().toString())
                .name("MERAC_TEMP") // merac temperature
                .messages(deviceMessageList1)
                .build();

        DeviceType deviceType2 = DeviceType.builder()
                .id(UUID.randomUUID().toString())
                .name("MERAC_PRIT") // merac pritiska
                .messages(deviceMessageList2)
                .build();

        DeviceType deviceType3 = DeviceType.builder()
                .id(UUID.randomUUID().toString())
                .name("MERAC_VLAZ") // merac vlaznosti
                .messages(deviceMessageList3)
                .build();

        dtr.save(deviceType1);
        dtr.save(deviceType2);
        dtr.save(deviceType3);

        Device device1 = Device.builder()
                .id(UUID.randomUUID().toString())
                .name("MOJ MERAC 1")
                .deviceType(deviceType1)
                .build();

        Device device2 = Device.builder()
                .id(UUID.randomUUID().toString())
                .name("MOJ MERAC 2")
                .deviceType(deviceType1)
                .build();

        Device device3 = Device.builder()
                .id(UUID.randomUUID().toString())
                .name("Merac pritisak 1")
                .deviceType(deviceType2)
                .build();

        Device device4 = Device.builder()
                .id(UUID.randomUUID().toString())
                .name("Merac prit 22")
                .deviceType(deviceType2)
                .build();

        Device device5 = Device.builder()
                .id(UUID.randomUUID().toString())
                .name("MOJ MERAC vlaznosti 1")
                .deviceType(deviceType3)
                .build();

//        dr.save(device1);
//        dr.save(device2);
//        dr.save(device3);
//        dr.save(device4);
//        dr.save(device5);

        House house1 = House.builder()
                .id(UUID.randomUUID().toString())
                .name("Kucica 1")
                .devices(Arrays.asList(device1,device2))
                .ownerList(Arrays.asList(o1))
                .tenantList(Arrays.asList(t2))
                .build();

        House garage1 = House.builder()
                .id(UUID.randomUUID().toString())
                .name("Garaza 1")
                .devices(Arrays.asList(device3))
                .ownerList(Arrays.asList(o1))
                .tenantList(Arrays.asList(t3))
                .build();

        House house2 = House.builder()
                .id(UUID.randomUUID().toString())
                .name("Objekat 2")
                .devices(Arrays.asList(device4,device5))
                .ownerList(Arrays.asList(o1,o2))
                .tenantList(Arrays.asList(t1,t2))
                .build();

        device1.setHouse(house1);
        device2.setHouse(house1);

        device3.setHouse(garage1);

        device4.setHouse(house2);
        device5.setHouse(house2);

        hr.save(house1);
        hr.save(garage1);
        hr.save(house2);

        dr.save(device1);
        dr.save(device2);
        dr.save(device3);
        dr.save(device4);
        dr.save(device5);

        o1.getHouseList().add(house1);
        o1.getHouseList().add(garage1);
        o1.getHouseList().add(house2);

        o2.getHouseList().add(house2);

        t2.getHouseList().add(house1);
        t3.getHouseList().add(garage1);

        t1.getHouseList().add(house2);
        t2.getHouseList().add(house2);

        or.save(o1);
        or.save(o2);
        or.save(o3);


        tr.save(t1);
        tr.save(t2);
        tr.save(t3);

    }

}
