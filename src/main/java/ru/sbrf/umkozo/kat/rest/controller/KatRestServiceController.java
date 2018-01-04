package ru.sbrf.umkozo.kat.rest.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import ru.sbrf.umkozo.kat.rest.model.KatTypeRatingEntity;
import ru.sbrf.umkozo.kat.rest.model.KatUserEntity;
import ru.sbrf.umkozo.kat.rest.service.IKatTypeRatingService;
import ru.sbrf.umkozo.kat.rest.service.IKatUserService;

@RestController
public class KatRestServiceController {
 
    @Autowired
    IKatUserService katUserService;

    @Autowired
    IKatTypeRatingService katTypeRatingService;

    //-------------------Retrieve All Users--------------------------------------------------------
     
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<KatUserEntity>> listAllUsers() {
        List<KatUserEntity> users = katUserService.findAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<List<KatUserEntity>>(HttpStatus.NOT_FOUND); //maybe NO_CONTENT is better?
        }
        return new ResponseEntity<List<KatUserEntity>>(users, HttpStatus.OK);
    }
 

    //-------------------Retrieve Single User--------------------------------------------------------
     
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KatUserEntity> getUser(@PathVariable("id") int id) {
        System.out.println("Fetching User with id " + id);
        KatUserEntity user = katUserService.findById(id);
        if (user == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<KatUserEntity>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<KatUserEntity>(user, HttpStatus.OK);
    }
     

    //-------------------Create a User--------------------------------------------------------
     
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody KatUserEntity user, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + user.getFullName());
 
        if (katUserService.isUserExist(user)) {
            System.out.println("A User with name " + user.getFullName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        katUserService.saveUser(user);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
 
     
    //------------------- Update a User --------------------------------------------------------
     
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<KatUserEntity> updateUser(@PathVariable("id") int id, @RequestBody KatUserEntity user) {
        System.out.println("Updating User " + id);

        KatUserEntity currentUser = katUserService.findById(id);
         
        if (currentUser==null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<KatUserEntity>(HttpStatus.NOT_FOUND);
        }
 
        currentUser.setLogin(user.getLogin());
        currentUser.setFullName(user.getFullName());

        katUserService.updateUser(currentUser);
        return new ResponseEntity<KatUserEntity>(currentUser, HttpStatus.OK);
    }
 
    //------------------- Delete a User --------------------------------------------------------
     
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<KatUserEntity> deleteUser(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting User with id " + id);

        KatUserEntity user = katUserService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<KatUserEntity>(HttpStatus.NOT_FOUND);
        }
 
        katUserService.deleteUserById(id);
        return new ResponseEntity<KatUserEntity>(HttpStatus.NO_CONTENT);
    }
 
     
    //------------------- Delete All Users --------------------------------------------------------
     
    //@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    //public ResponseEntity<User> deleteAllUsers() {
    //    System.out.println("Deleting All Users");
 
    //    userService.deleteAllUsers();
    //    return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    //}

    //-------------------Retrieve All TypeRatings--------------------------------------------------------

    @RequestMapping(value = "/type_rating/", method = RequestMethod.GET)
    public ResponseEntity<List<KatTypeRatingEntity>> listAllTypeRatings() {
        List<KatTypeRatingEntity> typeRatings = katTypeRatingService.findAllTypeRatings();
        if(typeRatings.isEmpty()){
            return new ResponseEntity<List<KatTypeRatingEntity>>(HttpStatus.NOT_FOUND); //maybe NO_CONTENT is better?
        }
        return new ResponseEntity<List<KatTypeRatingEntity>>(typeRatings, HttpStatus.OK);
    }


    //-------------------Retrieve Single TypeRating--------------------------------------------------------

    @RequestMapping(value = "/type_rating/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KatTypeRatingEntity> getTypeRating(@PathVariable("id") int id) {
        System.out.println("Fetching TypeRating with id " + id);
        KatTypeRatingEntity typeRating = katTypeRatingService.findById(id);
        if (typeRating == null) {
            System.out.println("TypeRating with id " + id + " not found");
            return new ResponseEntity<KatTypeRatingEntity>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<KatTypeRatingEntity>(typeRating, HttpStatus.OK);
    }


    //-------------------Create a TypeRating--------------------------------------------------------

    @RequestMapping(value = "/type_rating/", method = RequestMethod.POST)
    public ResponseEntity<Void> createTypeRating(@RequestBody KatTypeRatingEntity typeRating, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating TypeRating " + typeRating.getRatingName());

        if (katTypeRatingService.isTypeRatingExist(typeRating)) {
            System.out.println("A TypeRating with name " + typeRating.getRatingName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        katTypeRatingService.saveTypeRating(typeRating);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/type_rating/{id}").buildAndExpand(typeRating.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a TypeRating --------------------------------------------------------

    @RequestMapping(value = "/type_rating/{id}", method = RequestMethod.PUT)
    public ResponseEntity<KatTypeRatingEntity> updateTypeRating(@PathVariable("id") int id, @RequestBody KatTypeRatingEntity typeRating) {
        System.out.println("Updating TypeRating " + id);

        KatTypeRatingEntity currentTypeRating = katTypeRatingService.findById(id);

        if (currentTypeRating==null) {
            System.out.println("TypeRating with id " + id + " not found");
            return new ResponseEntity<KatTypeRatingEntity>(HttpStatus.NOT_FOUND);
        }

        currentTypeRating.setRatingName(typeRating.getRatingName());
        currentTypeRating.setScore(typeRating.getScore());

        katTypeRatingService.updateTypeRating(currentTypeRating);
        return new ResponseEntity<KatTypeRatingEntity>(currentTypeRating, HttpStatus.OK);
    }

    //------------------- Delete a TypeRating --------------------------------------------------------

    @RequestMapping(value = "/type_rating/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<KatTypeRatingEntity> deleteTypeRating(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting TypeRating with id " + id);

        KatTypeRatingEntity typeRating = katTypeRatingService.findById(id);
        if (typeRating == null) {
            System.out.println("Unable to delete. TypeRating with id " + id + " not found");
            return new ResponseEntity<KatTypeRatingEntity>(HttpStatus.NOT_FOUND);
        }

        katTypeRatingService.deleteTypeRatingById(id);
        return new ResponseEntity<KatTypeRatingEntity>(HttpStatus.NO_CONTENT);
    }

}