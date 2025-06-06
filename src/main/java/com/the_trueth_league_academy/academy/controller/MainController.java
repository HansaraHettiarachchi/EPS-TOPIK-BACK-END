package com.the_trueth_league_academy.academy.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.the_trueth_league_academy.academy.dto.CPDTO;
import com.the_trueth_league_academy.academy.dto.PaperQuestionsDTO;
import com.the_trueth_league_academy.academy.dto.PapersDTO;
import com.the_trueth_league_academy.academy.dto.QuestTypeDTO;
import com.the_trueth_league_academy.academy.dto.QuestionDTO;
import com.the_trueth_league_academy.academy.dto.QuestionRequestDTO;
import com.the_trueth_league_academy.academy.dto.QuestionResponseDTO;
import com.the_trueth_league_academy.academy.dto.ScheduleExamDTO;
import com.the_trueth_league_academy.academy.service.MainService;

import jakarta.validation.Valid;

@RestController
// @CrossOrigin(value = "http://localhost:5173")
// @CrossOrigin
@RequestMapping(value = "/api/v1/")
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping("")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("dfsdf");
    }

    @GetMapping("getQType")
    public ResponseEntity<List<QuestTypeDTO>> getQType() {
        return ResponseEntity.ok(mainService.getAllQTypes());
    }

    @GetMapping("getQuesDataById/{id}")
    public ResponseEntity<QuestionResponseDTO> getQuesDataById(@Valid @PathVariable int id) {
        return ResponseEntity.ok(mainService.getQuestionById(id));
    }

    @GetMapping("getAllQuestions")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        return ResponseEntity.ok(mainService.getAllQuestions());
    }

    @GetMapping("getAllQuesByQues")
    public ResponseEntity<List<QuestionDTO>> getAllQuesByQues(@Valid @RequestParam String ques, int qType) {

        return ResponseEntity.ok(mainService.findQuesByQues(ques, qType));
    }

    // @GetMapping("getAllQuesById/{id}")
    // public String getAllQuesById(@Valid @PathVariable String id) {
    // return "it's id " + id;
    // }

    @PostMapping("createQuestion")
    public ResponseEntity<String> createQuestion(
            // public QuestionRequestDTO createQuestion(
            @Valid @RequestParam("question") String question,
            @RequestParam("id") String id,
            @RequestParam("questionType") int questionType,
            @RequestParam("answers0") String ans1,
            @RequestParam("answers1") String ans2,
            @RequestParam("answers2") String ans3,
            @RequestParam("answers3") String ans4,
            @RequestParam("ans") String ans,
            @RequestParam("relation") String relation,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @RequestPart(value = "audio", required = false) MultipartFile audio) {

        List<String> answers = new LinkedList<>();
        answers.add(ans1.split(" : ")[1]);
        answers.add(ans2.split(" : ")[1]);
        answers.add(ans3.split(" : ")[1]);
        answers.add(ans4.split(" : ")[1]);

        QuestionRequestDTO request = new QuestionRequestDTO();
        if (!id.equals("0")) {
            request.setId(Integer.parseInt(id));
        }
        request.setQuestion(question);
        request.setAns(ans);
        request.setQuestionType(questionType);
        request.setAnswers(answers);
        request.setImages(images);
        request.setAudio(audio);
        request.setRelation(Integer.parseInt(relation));

        // return request;
        return ResponseEntity.ok(mainService.createQuestion(request));

    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("updateQuestion")
    public QuestionRequestDTO updateQuestion(
    // public ResponseEntity<String> updateQuestion(
            @Valid @RequestParam("question") String question,
            @RequestParam("id") String id,
            @RequestParam("questionType") int questionType,
            @RequestParam("answers0") String ans1,
            @RequestParam("answers1") String ans2,
            @RequestParam("answers2") String ans3,
            @RequestParam("answers3") String ans4,
            @RequestParam("ans") String ans,
            @RequestParam("relation") String relation,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @RequestPart(value = "audio", required = false) MultipartFile audio) {

        List<String> answers = new LinkedList<>();
        answers.add(ans1.split(" : ")[1]);
        answers.add(ans2.split(" : ")[1]);
        answers.add(ans3.split(" : ")[1]);
        answers.add(ans4.split(" : ")[1]);

        QuestionRequestDTO request = new QuestionRequestDTO();
        if (!id.equals("0")) {
            request.setId(Integer.parseInt(id));
        }
        request.setQuestion(question);
        request.setAns(ans);
        request.setQuestionType(questionType);
        request.setAnswers(answers);
        request.setImages(images);
        // request.setAudio(audio);
        request.setRelation(Integer.parseInt(relation));

        return request;
        // return ResponseEntity.ok(mainService.updateQuestion(request));

    }

    @PostMapping("setPaper")
    public ResponseEntity<String> setPaper(@Valid @RequestBody PaperQuestionsDTO dto) {

        return ResponseEntity.ok(mainService.createPaper(dto));
    }

    @GetMapping("getQuestionsByPId/{pid}")
    public ResponseEntity<List<CPDTO>> getQuestionsByPId(@PathVariable int pid) {
        return ResponseEntity.ok(mainService.getCPQuestions(pid));
    }

    @GetMapping("getPaperD")
    public ResponseEntity<List<PapersDTO>> getPaperD(@RequestParam String text) {
        return ResponseEntity.ok(mainService.getPaperD(text));
    }

    // @PostMapping("scheduleExam")
    // public ScheduleExamDTO scheduleExam(@Valid @RequestBody ScheduleExamDTO
    // scheduleExamDTO) {
    // return scheduleExamDTO;
    // }
    @PostMapping("scheduleExam")
    public String scheduleExam(@Valid @RequestBody ScheduleExamDTO scheduleExamDTO) {
        return mainService.scheduleExam(scheduleExamDTO);
    }

}
