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

import ru.sbrf.umkozo.kat.rest.model.db.*;
import ru.sbrf.umkozo.kat.rest.model.service.AnswerForSave;
import ru.sbrf.umkozo.kat.rest.model.service.QuestionForSave;
import ru.sbrf.umkozo.kat.rest.model.service.RatingForSave;
import ru.sbrf.umkozo.kat.rest.service.*;

@RestController
public class KatRestServiceController {
 
    @Autowired
    IKatUserService katUserService;
    @Autowired
    IKatTypeRatingService katTypeRatingService;
    @Autowired
    IKatSubjectQuestionService katSubjectQuestionService;
    @Autowired
    IKatQuestionService katQuestionService;
    @Autowired
    IKatAnswerService katAnswerService;
    @Autowired
    IKatRatingService katRatingService;

    //---------------------------------------------------------------------------------------------------
    //------------------- Retrieve All Users ------------------------------------------------------------
     
    @RequestMapping(value = "/user/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<List<KatUserEntity>> listAllUsers() {
        List<KatUserEntity> users = katUserService.findAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<List<KatUserEntity>>(HttpStatus.NOT_FOUND); //maybe NO_CONTENT is better?
        }
        return new ResponseEntity<List<KatUserEntity>>(users, HttpStatus.OK);
    }
 

    //------------------- Retrieve Single User ----------------------------------------------------------
     
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<KatUserEntity> getUser(@PathVariable("id") int id) {
        System.out.println("Fetching User with id " + id);
        KatUserEntity user = katUserService.findById(id);
        if (user == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<KatUserEntity>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<KatUserEntity>(user, HttpStatus.OK);
    }
     

    //------------------- Create a User -----------------------------------------------------------------
     
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
 
     
    //------------------- Update a User -----------------------------------------------------------------
     
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
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
 
    //------------------- Delete a User -----------------------------------------------------------------
     
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
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
 
     
    //------------------- Delete All Users --------------------------------------------------------------
     
    //@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    //public ResponseEntity<User> deleteAllUsers() {
    //    System.out.println("Deleting All Users");
 
    //    userService.deleteAllUsers();
    //    return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    //}

    //---------------------------------------------------------------------------------------------------
    //-------------------Retrieve All TypeRatings--------------------------------------------------------

    @RequestMapping(value = "/type_rating/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<List<KatTypeRatingEntity>> listAllTypeRatings() {
        List<KatTypeRatingEntity> typeRatings = katTypeRatingService.findAllTypeRatings();
        if(typeRatings.isEmpty()){
            return new ResponseEntity<List<KatTypeRatingEntity>>(HttpStatus.NOT_FOUND); //maybe NO_CONTENT is better?
        }
        return new ResponseEntity<List<KatTypeRatingEntity>>(typeRatings, HttpStatus.OK);
    }


    //------------------- Retrieve Single TypeRating ----------------------------------------------------

    @RequestMapping(value = "/type_rating/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<KatTypeRatingEntity> getTypeRating(@PathVariable("id") int id) {
        System.out.println("Fetching TypeRating with id " + id);
        KatTypeRatingEntity typeRating = katTypeRatingService.findById(id);
        if (typeRating == null) {
            System.out.println("TypeRating with id " + id + " not found");
            return new ResponseEntity<KatTypeRatingEntity>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<KatTypeRatingEntity>(typeRating, HttpStatus.OK);
    }


    //------------------- Create a TypeRating -----------------------------------------------------------

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


    //------------------- Update a TypeRating -----------------------------------------------------------

    @RequestMapping(value = "/type_rating/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
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

    //------------------- Delete a TypeRating -----------------------------------------------------------

    @RequestMapping(value = "/type_rating/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
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


    //---------------------------------------------------------------------------------------------------
    //------------------- Retrieve All SubjectQuestions -------------------------------------------------

    @RequestMapping(value = "/subj_quest/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<List<KatSubjectQuestionEntity>> listAllSubjectQuestions() {
        List<KatSubjectQuestionEntity> subjectQuestions = katSubjectQuestionService.findAllSubjectQuestions();
        if(subjectQuestions.isEmpty()){
            return new ResponseEntity<List<KatSubjectQuestionEntity>>(HttpStatus.NOT_FOUND); //maybe NO_CONTENT is better?
        }
        return new ResponseEntity<List<KatSubjectQuestionEntity>>(subjectQuestions, HttpStatus.OK);
    }


    //------------------- Retrieve Single SubjectQuestion -----------------------------------------------

    @RequestMapping(value = "/subj_quest/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<KatSubjectQuestionEntity> getSubjectQuestion(@PathVariable("id") int id) {
        System.out.println("Fetching SubjectQuestion with id " + id);
        KatSubjectQuestionEntity subjectQuestion = katSubjectQuestionService.findById(id);
        if (subjectQuestion == null) {
            System.out.println("SubjectQuestion with id " + id + " not found");
            return new ResponseEntity<KatSubjectQuestionEntity>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<KatSubjectQuestionEntity>(subjectQuestion, HttpStatus.OK);
    }


    //------------------- Create a SubjectQuestion ------------------------------------------------------

    @RequestMapping(value = "/subj_quest/", method = RequestMethod.POST)
    public ResponseEntity<Void> createSubjectQuestion(@RequestBody KatSubjectQuestionEntity subjectQuestion, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating SubjectQuestion " + subjectQuestion.getSubject());

        if (katSubjectQuestionService.isSubjectQuestionExist(subjectQuestion)) {
            System.out.println("A SubjectQuestion with name " + subjectQuestion.getSubject() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        katSubjectQuestionService.saveSubjectQuestion(subjectQuestion);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/subj_quest/{id}").buildAndExpand(subjectQuestion.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a SubjectQuestion ------------------------------------------------------

    @RequestMapping(value = "/subj_quest/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<KatSubjectQuestionEntity> updateSubjectQuestion(@PathVariable("id") int id, @RequestBody KatSubjectQuestionEntity subjectQuestion) {
        System.out.println("Updating SubjectQuestion " + id);

        KatSubjectQuestionEntity currentSubjectQuestion = katSubjectQuestionService.findById(id);

        if (currentSubjectQuestion==null) {
            System.out.println("SubjectQuestion with id " + id + " not found");
            return new ResponseEntity<KatSubjectQuestionEntity>(HttpStatus.NOT_FOUND);
        }

        currentSubjectQuestion.setSubject(subjectQuestion.getSubject());
        currentSubjectQuestion.setDescription(subjectQuestion.getDescription());

        katSubjectQuestionService.updateSubjectQuestion(currentSubjectQuestion);
        return new ResponseEntity<KatSubjectQuestionEntity>(currentSubjectQuestion, HttpStatus.OK);
    }


    //------------------- Delete a SubjectQuestion ------------------------------------------------------

    @RequestMapping(value = "/subj_quest/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<KatSubjectQuestionEntity> deleteSubjectQuestion(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting SubjectQuestion with id " + id);

        KatSubjectQuestionEntity subjectQuestion = katSubjectQuestionService.findById(id);
        if (subjectQuestion == null) {
            System.out.println("Unable to delete. SubjectQuestion with id " + id + " not found");
            return new ResponseEntity<KatSubjectQuestionEntity>(HttpStatus.NOT_FOUND);
        }

        katSubjectQuestionService.deleteSubjectQuestionById(id);
        return new ResponseEntity<KatSubjectQuestionEntity>(HttpStatus.NO_CONTENT);
    }


    //---------------------------------------------------------------------------------------------------
    //------------------- Retrieve All Questions --------------------------------------------------------

    @RequestMapping(value = "/question/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<List<KatQuestionEntity>> listAllQuestions() {
        List<KatQuestionEntity> questions = katQuestionService.findAllQuestions();
        if(questions.isEmpty()){
            return new ResponseEntity<List<KatQuestionEntity>>(HttpStatus.NOT_FOUND); //maybe NO_CONTENT is better?
        }
        return new ResponseEntity<List<KatQuestionEntity>>(questions, HttpStatus.OK);
    }


    //------------------- Retrieve Single Question ------------------------------------------------------

    @RequestMapping(value = "/question/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<KatQuestionEntity> getQuestion(@PathVariable("id") int id) {
        System.out.println("Fetching Question with id " + id);
        KatQuestionEntity question = katQuestionService.findById(id);
        if (question == null) {
            System.out.println("Question with id " + id + " not found");
            return new ResponseEntity<KatQuestionEntity>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<KatQuestionEntity>(question, HttpStatus.OK);
    }


    //------------------- Create a Question -------------------------------------------------------------

    @RequestMapping(value = "/question/", method = RequestMethod.POST)
    public ResponseEntity<Void> createQuestion(@RequestBody QuestionForSave question, UriComponentsBuilder ucBuilder) {
        System.out.println("Constructing Question " + question.getQuestion());

        KatQuestionEntity saveQuestion = new KatQuestionEntity();

        KatUserEntity saveUser = katUserService.findById(question.getUserId());
        if (saveUser == null){
            System.out.println("User with id " + question.getUserId() + " not found");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        KatSubjectQuestionEntity saveSubject = katSubjectQuestionService.findById(question.getSubjectId());
        if (saveSubject == null){
            System.out.println("Subject with id " + question.getSubjectId() + " not found");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        saveQuestion.setDateCreate(new java.sql.Date(new java.util.Date().getTime()));
        saveQuestion.setQuestion(question.getQuestion());
        saveQuestion.setKatUserByIdUser(saveUser);
        saveQuestion.setKatSubjectQuestionByIdSubject(saveSubject);

        System.out.println("Creating Question " + saveQuestion.getQuestion());

        if (katQuestionService.isQuestionExist(saveQuestion)) {
            System.out.println("A Question with text " + saveQuestion.getQuestion() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        katQuestionService.saveQuestion(saveQuestion);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/question/{id}").buildAndExpand(saveQuestion.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a Question -------------------------------------------------------------

    @RequestMapping(value = "/question/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<KatQuestionEntity> updateQuestion(@PathVariable("id") int id, @RequestBody QuestionForSave question) {
        System.out.println("Updating Question " + id);

        KatQuestionEntity currentQuestion = katQuestionService.findById(id);

        if (currentQuestion==null) {
            System.out.println("Question with id " + id + " not found");
            return new ResponseEntity<KatQuestionEntity>(HttpStatus.NOT_FOUND);
        }

        KatUserEntity saveUser = katUserService.findById(question.getUserId());
        if (saveUser == null){
            System.out.println("User with id " + question.getUserId() + " not found");
            return new ResponseEntity<KatQuestionEntity>(HttpStatus.CONFLICT);
        }

        KatSubjectQuestionEntity saveSubject = katSubjectQuestionService.findById(question.getSubjectId());
        if (saveSubject == null){
            System.out.println("Subject with id " + question.getSubjectId() + " not found");
            return new ResponseEntity<KatQuestionEntity>(HttpStatus.CONFLICT);
        }

        currentQuestion.setQuestion(question.getQuestion());
        currentQuestion.setKatUserByIdUser(saveUser);
        currentQuestion.setKatSubjectQuestionByIdSubject(saveSubject);

        katQuestionService.updateQuestion(currentQuestion);
        return new ResponseEntity<KatQuestionEntity>(currentQuestion, HttpStatus.OK);
    }


    //------------------- Delete a Question -------------------------------------------------------------

    @RequestMapping(value = "/question/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<KatQuestionEntity> deleteQuestion(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting Question with id " + id);

        KatQuestionEntity question = katQuestionService.findById(id);
        if (question == null) {
            System.out.println("Unable to delete. Question with id " + id + " not found");
            return new ResponseEntity<KatQuestionEntity>(HttpStatus.NOT_FOUND);
        }

        katQuestionService.deleteQuestionById(id);
        return new ResponseEntity<KatQuestionEntity>(HttpStatus.NO_CONTENT);
    }


    //---------------------------------------------------------------------------------------------------
    //------------------- Retrieve All Answers ----------------------------------------------------------

    @RequestMapping(value = "/answer/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<List<KatAnswerEntity>> listAllAnswers() {
        List<KatAnswerEntity> answers = katAnswerService.findAllAnswers();
        if(answers.isEmpty()){
            return new ResponseEntity<List<KatAnswerEntity>>(HttpStatus.NOT_FOUND); //maybe NO_CONTENT is better?
        }
        return new ResponseEntity<List<KatAnswerEntity>>(answers, HttpStatus.OK);
    }


    //------------------- Retrieve Single Answer --------------------------------------------------------

    @RequestMapping(value = "/answer/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<KatAnswerEntity> getAnswer(@PathVariable("id") int id) {
        System.out.println("Fetching Answer with id " + id);
        KatAnswerEntity answer = katAnswerService.findById(id);
        if (answer == null) {
            System.out.println("Answer with id " + id + " not found");
            return new ResponseEntity<KatAnswerEntity>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<KatAnswerEntity>(answer, HttpStatus.OK);
    }


    //------------------- Create a Answer ---------------------------------------------------------------

    @RequestMapping(value = "/answer/", method = RequestMethod.POST)
    public ResponseEntity<Void> createAnswer(@RequestBody AnswerForSave answer, UriComponentsBuilder ucBuilder) {
        System.out.println("Constructing Answer " + answer.getAnswer());

        KatAnswerEntity saveAnswer = new KatAnswerEntity();

        KatQuestionEntity saveQuestion = katQuestionService.findById(answer.getQuestionId());

        if (saveQuestion == null){
            System.out.println("Question with id " + answer.getQuestionId() + " not found");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        saveAnswer.setAnswer(answer.getAnswer());
        saveAnswer.setIsRight(answer.getIsRight());
        saveAnswer.setKatQuestionByIdQuestion(saveQuestion);

        System.out.println("Creating Answer " + saveAnswer.getAnswer());

        if (katAnswerService.isAnswerExist(saveAnswer)) {
            System.out.println("An Answer with text " + saveAnswer.getAnswer() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        katAnswerService.saveAnswer(saveAnswer);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/answer/{id}").buildAndExpand(saveAnswer.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a Answer ---------------------------------------------------------------

    @RequestMapping(value = "/answer/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<KatAnswerEntity> updateAnswer(@PathVariable("id") int id, @RequestBody AnswerForSave answer) {
        System.out.println("Updating Answer " + id);

        KatAnswerEntity currentAnswer = katAnswerService.findById(id);

        if (currentAnswer==null) {
            System.out.println("Answer with id " + id + " not found");
            return new ResponseEntity<KatAnswerEntity>(HttpStatus.NOT_FOUND);
        }

        KatQuestionEntity saveQuestion = katQuestionService.findById(answer.getQuestionId());

        if (saveQuestion == null){
            System.out.println("Question with id " + answer.getQuestionId() + " not found");
            return new ResponseEntity<KatAnswerEntity>(HttpStatus.CONFLICT);
        }

        currentAnswer.setAnswer(answer.getAnswer());
        currentAnswer.setIsRight(answer.getIsRight());
        currentAnswer.setKatQuestionByIdQuestion(saveQuestion);

        katAnswerService.updateAnswer(currentAnswer);
        return new ResponseEntity<KatAnswerEntity>(currentAnswer, HttpStatus.OK);
    }


    //------------------- Delete a Answer ---------------------------------------------------------------

    @RequestMapping(value = "/answer/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<KatAnswerEntity> deleteAnswer(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting Answer with id " + id);

        KatAnswerEntity answer = katAnswerService.findById(id);
        if (answer == null) {
            System.out.println("Unable to delete. Answer with id " + id + " not found");
            return new ResponseEntity<KatAnswerEntity>(HttpStatus.NOT_FOUND);
        }

        katAnswerService.deleteAnswerById(id);
        return new ResponseEntity<KatAnswerEntity>(HttpStatus.NO_CONTENT);
    }


    //---------------------------------------------------------------------------------------------------
    //------------------- Retrieve All Ratings ----------------------------------------------------------

    @RequestMapping(value = "/rating/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<List<KatRatingEntity>> listAllRatings() {
        List<KatRatingEntity> ratings = katRatingService.findAllRatings();
        if(ratings.isEmpty()){
            return new ResponseEntity<List<KatRatingEntity>>(HttpStatus.NOT_FOUND); //maybe NO_CONTENT is better?
        }
        return new ResponseEntity<List<KatRatingEntity>>(ratings, HttpStatus.OK);
    }


    //------------------- Retrieve Single Rating --------------------------------------------------------

    @RequestMapping(value = "/rating/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<KatRatingEntity> getRating(@PathVariable("id") int id) {
        System.out.println("Fetching Rating with id " + id);
        KatRatingEntity rating = katRatingService.findById(id);
        if (rating == null) {
            System.out.println("Rating with id " + id + " not found");
            return new ResponseEntity<KatRatingEntity>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<KatRatingEntity>(rating, HttpStatus.OK);
    }


    //------------------- Create a Rating ---------------------------------------------------------------

    @RequestMapping(value = "/rating/", method = RequestMethod.POST)
    public ResponseEntity<Void> createRating(@RequestBody RatingForSave rating, UriComponentsBuilder ucBuilder) {
        System.out.println("Constructing Rating " + rating.getComments());

        KatRatingEntity saveRating = new KatRatingEntity();

        KatUserEntity saveUser = katUserService.findById(rating.getUserId());
        if (saveUser==null){
            System.out.println("User with id " + rating.getQuestionId() + " not found");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        KatQuestionEntity saveQuestion = katQuestionService.findById(rating.getQuestionId());
        if (saveQuestion == null){
            System.out.println("Question with id " + rating.getQuestionId() + " not found");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        KatTypeRatingEntity saveTypeRating = katTypeRatingService.findById(rating.getTypeRatingId());
        if (saveTypeRating==null){
            System.out.println("Type with id " + rating.getQuestionId() + " not found");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        saveRating.setComments(rating.getComments());
        saveRating.setDateRating(new java.sql.Date(new java.util.Date().getTime()));
        saveRating.setKatUserByIdUser(saveUser);
        saveRating.setKatQuestionByIdQuestion(saveQuestion);
        saveRating.setKatTypeRatingByIdTypeRating(saveTypeRating);

        System.out.println("Creating Rating " + saveRating.getComments());

        if (katRatingService.isRatingExist(saveRating)) {
            System.out.println("A Rating with text " + saveRating.getComments() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        katRatingService.saveRating(saveRating);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/rating/{id}").buildAndExpand(saveRating.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a Rating ---------------------------------------------------------------

    @RequestMapping(value = "/rating/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<KatRatingEntity> updateRating(@PathVariable("id") int id, @RequestBody RatingForSave rating) {
        System.out.println("Updating Rating " + id);

        KatRatingEntity currentRating = katRatingService.findById(id);

        if (currentRating==null) {
            System.out.println("Rating with id " + id + " not found");
            return new ResponseEntity<KatRatingEntity>(HttpStatus.NOT_FOUND);
        }

        KatUserEntity saveUser = katUserService.findById(rating.getUserId());
        if (saveUser==null){
            System.out.println("User with id " + rating.getQuestionId() + " not found");
            return new ResponseEntity<KatRatingEntity>(HttpStatus.CONFLICT);
        }

        KatQuestionEntity saveQuestion = katQuestionService.findById(rating.getQuestionId());
        if (saveQuestion == null){
            System.out.println("Question with id " + rating.getQuestionId() + " not found");
            return new ResponseEntity<KatRatingEntity>(HttpStatus.CONFLICT);
        }

        KatTypeRatingEntity saveTypeRating = katTypeRatingService.findById(rating.getTypeRatingId());
        if (saveTypeRating==null){
            System.out.println("Type with id " + rating.getQuestionId() + " not found");
            return new ResponseEntity<KatRatingEntity>(HttpStatus.CONFLICT);
        }

        currentRating.setComments(rating.getComments());
        currentRating.setKatUserByIdUser(saveUser);
        currentRating.setKatQuestionByIdQuestion(saveQuestion);
        currentRating.setKatTypeRatingByIdTypeRating(saveTypeRating);

        katRatingService.updateRating(currentRating);
        return new ResponseEntity<KatRatingEntity>(currentRating, HttpStatus.OK);
    }


    //------------------- Delete a Rating ---------------------------------------------------------------

    @RequestMapping(value = "/rating/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<KatRatingEntity> deleteRating(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting Rating with id " + id);

        KatRatingEntity answer = katRatingService.findById(id);
        if (answer == null) {
            System.out.println("Unable to delete. Rating with id " + id + " not found");
            return new ResponseEntity<KatRatingEntity>(HttpStatus.NOT_FOUND);
        }

        katRatingService.deleteRatingById(id);
        return new ResponseEntity<KatRatingEntity>(HttpStatus.NO_CONTENT);
    }
}