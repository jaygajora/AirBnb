package com.jay.AirBnb.Service;


import com.jay.AirBnb.Dto.GuestDTO;
import com.jay.AirBnb.Entity.GuestEntity;
import com.jay.AirBnb.Entity.UserEntity;
import com.jay.AirBnb.Enums.Gender;
import com.jay.AirBnb.Exceptions.ResourceNotFoundException;
import com.jay.AirBnb.Exceptions.UnauthorisedException;
import com.jay.AirBnb.Repository.GuestRepository;
import com.jay.AirBnb.Service.Interface.GuestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImplementation implements GuestService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    GuestRepository guestRepository;

    // add guest (GuestDTO guestDTO)
    public GuestDTO addGuest(GuestDTO guestDTO){
        String name = guestDTO.getName();
        int age = guestDTO.getAge();
        Gender gender = guestDTO.getGender();

        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Please Enter Guest's name");
        }

        if(age < 0 || age > 120){
            throw new IllegalArgumentException("Age should be GREATER THAN 0 and LESS THAN 120");
        }

        if(gender == null){
            throw new IllegalArgumentException("Please enter your Guest's gender");
        }

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        GuestEntity guest = modelMapper.map(guestDTO, GuestEntity.class);
        guest.setUser(user);
        guestRepository.save(guest);

        return modelMapper.map(guest, GuestDTO.class);
    }

    public GuestDTO getGuestById(Long guestId){
        if(guestId == null){
            throw new IllegalArgumentException("Please enter guest id");
        }

        GuestEntity guest = guestRepository.findById(guestId).orElseThrow(() -> new ResourceNotFoundException("Guest NOT FOUND!"));

        return modelMapper.map(guest, GuestDTO.class);
    }

    // update guest (Long guestId, GuestDTO guestDTO)
    public GuestDTO updateGuest(Long guestId, GuestDTO guestDTO){
        if(guestId == null){
            throw new IllegalArgumentException("Invalid Guest Id");
        }

        GuestEntity guest = guestRepository.findById(guestId).orElseThrow(() -> new IllegalArgumentException("No guest found with Id: " + guestId));

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(guest.getUser())){
            throw new UnauthorisedException("You did not add this user and hence you cannot update this guest!");
        }

        String name = guestDTO.getName();
        int age = guestDTO.getAge();
        Gender gender = guestDTO.getGender();

        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Please enter Guest's name");
        }

        if(age < 0 || age > 120){
            throw new IllegalArgumentException("Age should be GREATER THAN 0 and LESS THAN 120");
        }

        if(gender == null){
            throw new IllegalArgumentException("Please enter your Guest's gender");
        }

        if(!name.equals(guest.getName())){
            guest.setName(name);
        }

        if(age != guest.getAge()){
            guest.setAge(age);
        }

        if(gender != guest.getGender()){
            guest.setGender(gender);
        }

        guestRepository.save(guest);

        return modelMapper.map(guest, GuestDTO.class);
    }

    //getAllGuests()   -> of the loggedInUser
    public List<GuestDTO> getAllGuests(){
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<GuestDTO> guests = guestRepository.findByUser(user);

        if(guests == null){
            throw new ResourceNotFoundException("No guests found which were added by user: " + user.getEmail());
        }

        return guests;
    }

    //deleteGuest(Long guestId)

    public String deleteGuest(Long guestId){
        if(guestId == null){
            throw new IllegalArgumentException("Please enter a Guest Id");
        }

        GuestEntity guest = guestRepository.findById(guestId).orElseThrow(() -> new ResourceNotFoundException("No Guest found with guest Id: " + guestId));

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(guest.getUser())){
            throw new UnauthorisedException("You did not add the Guest and hence you CANNOT DELETE this Guest!");
        }

        String name = guest.getName();

        guestRepository.deleteById(guestId);

        return "Guest '" +  name + "' has been DELETED!";
    }

}
