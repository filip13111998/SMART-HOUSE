package sbnz.ftn.uns.ac.rs.HOME.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sbnz.ftn.uns.ac.rs.HOME.dto.request.HouseSaveDTO;
import sbnz.ftn.uns.ac.rs.HOME.model.House;
import sbnz.ftn.uns.ac.rs.HOME.model.Owner;
import sbnz.ftn.uns.ac.rs.HOME.model.Tenant;
import sbnz.ftn.uns.ac.rs.HOME.repository.HouseRepository;
import sbnz.ftn.uns.ac.rs.HOME.repository.OwnerRepository;
import sbnz.ftn.uns.ac.rs.HOME.repository.TenantRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class HouseService {

    @Autowired
    private HouseRepository hr;

    @Autowired
    private OwnerRepository or;

    @Autowired
    private TenantRepository tr;

    private static final Logger logger = LoggerFactory.getLogger(HouseService.class);


    public boolean saveHouse(HouseSaveDTO hsdto) {

        Optional<House> houseOptional = hr.findAll().stream().filter(h->h.getName().equals(hsdto.getName())).findFirst();

        if(houseOptional.isPresent()){
            logger.warn("HOME-APP HouseService saveHouse.");

            return false;
        }

        List<Owner> ownerList = hsdto.getOwners().stream()
                .filter(o-> or.findByUsername(o) != null)
                .map(o-> or.findByUsername(o))
                .collect(Collectors.toList());

        if(hsdto.getOwners().size() != ownerList.size()){
            logger.warn("HOME-APP HouseService saveHouse.");

            return false;
        }

        List<Tenant> tenantList = hsdto.getTenants().stream()
                .filter(t-> tr.findByUsername(t) != null)
                .map(t-> tr.findByUsername(t))
                .collect(Collectors.toList());



        if(hsdto.getTenants().size() != tenantList.size()){
            logger.warn("HOME-APP HouseService saveHouse.");

            return false;
        }

        House house = House.builder()
                .id(UUID.randomUUID().toString())
                .name(hsdto.getName())
                .ownerList(ownerList)
                .tenantList(tenantList)
                .build();

        hr.save(house);

        logger.info("HOME-APP HouseService saveHouse.");

        return true;
    }

    public List<String> getAllHouses(String username) {

        List<String> houseNames = hr.findAll().stream().filter(h->h.getOwnerList().stream().filter(o->o.getUsername().equals(username)).findFirst().isPresent()).map(h-> h.getName()).collect(Collectors.toList());

        logger.info("HOME-APP HouseService getAllHouses.");

        return houseNames;
    }
}
