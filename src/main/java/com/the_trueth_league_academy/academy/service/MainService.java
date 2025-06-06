package com.the_trueth_league_academy.academy.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.the_trueth_league_academy.academy.dto.CPDTO;
import com.the_trueth_league_academy.academy.dto.McqAnsDTO;
import com.the_trueth_league_academy.academy.dto.PaperQuestionsDTO;
import com.the_trueth_league_academy.academy.dto.PapersDTO;
import com.the_trueth_league_academy.academy.dto.QuesImageDTO;
import com.the_trueth_league_academy.academy.dto.QuesRecodingDTO;
import com.the_trueth_league_academy.academy.dto.QuestTypeDTO;
import com.the_trueth_league_academy.academy.dto.QuestionDTO;
import com.the_trueth_league_academy.academy.dto.QuestionRequestDTO;
import com.the_trueth_league_academy.academy.dto.QuestionResponseDTO;
import com.the_trueth_league_academy.academy.dto.ScheduleExamDTO;
import com.the_trueth_league_academy.academy.dto.UsersHasExamsDTO;
import com.the_trueth_league_academy.academy.exceptions.CustomValidationException;
import com.the_trueth_league_academy.academy.exceptions.ResourceNotFoundException;
import com.the_trueth_league_academy.academy.models.Exams;
import com.the_trueth_league_academy.academy.models.McqAns;
import com.the_trueth_league_academy.academy.models.Papers;
import com.the_trueth_league_academy.academy.models.PapersHasQuestions;
import com.the_trueth_league_academy.academy.models.QuesImage;
import com.the_trueth_league_academy.academy.models.QuesRecoding;
import com.the_trueth_league_academy.academy.models.QuestType;
import com.the_trueth_league_academy.academy.models.Question;
import com.the_trueth_league_academy.academy.models.UsersHasExams;
import com.the_trueth_league_academy.academy.repo.ExamsRepo;
import com.the_trueth_league_academy.academy.repo.MCQAnsRepo;
import com.the_trueth_league_academy.academy.repo.PapersHasQuesRepo;
import com.the_trueth_league_academy.academy.repo.PapersRepo;
import com.the_trueth_league_academy.academy.repo.QuesRecRepo;
import com.the_trueth_league_academy.academy.repo.QuesimagesRepo;
import com.the_trueth_league_academy.academy.repo.QuestionRepo;
import com.the_trueth_league_academy.academy.repo.QuestionTypeRepo;
import com.the_trueth_league_academy.academy.repo.UserHasExamsRepo;

@Transactional
@Service
public class MainService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private QuestionTypeRepo questionTypeRepo;

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private MCQAnsRepo ansRepo;

    @Autowired
    private QuesimagesRepo quesimagesRepo;

    @Autowired
    private QuesRecRepo recRepo;

    @Autowired
    private PapersRepo papersRepo;

    @Autowired
    private PapersHasQuesRepo papersHasQuesRepo;

    @Autowired
    private ExamsRepo examsRepo;

    @Autowired
    private UserHasExamsRepo userHasExamsRepo;

    public List<QuestTypeDTO> getAllQTypes() {
        List<QuestType> list = questionTypeRepo.findAll();

        return modelMapper.map(list, new TypeToken<List<QuestTypeDTO>>() {
        }.getType());
    }

    public QuestionResponseDTO getQuestionById(int id) {
        Question question = questionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id, 203));

        QuestionResponseDTO questionResponseDTO = modelMapper.map(question, QuestionResponseDTO.class);

        List<McqAns> answers = ansRepo.findByQuestion(question);
        questionResponseDTO.setAnswers(answers.stream()
                .map(McqAns::getAns)
                .collect(Collectors.toList()));

        List<QuesImage> images = quesimagesRepo.findByQuestion(question);
        questionResponseDTO.setImageUrls(images.stream()
                .map(QuesImage::getLocation)
                .collect(Collectors.toList()));

        Optional<QuesRecoding> audio = recRepo.findByQuestion(question);
        audio.ifPresent(recoding -> questionResponseDTO.setAudioUrl(recoding.getLocation()));

        return questionResponseDTO;
    }

    @SuppressWarnings("unused")
    public String createQuestion(QuestionRequestDTO questionRequestDTO) {

        if (!questionRequestDTO.getQuestion().equals("Image Question")) {
            Question q = questionRepo.findByQues(questionRequestDTO.getQuestion(),
                    questionRequestDTO.getQuestionType());
            if (q != null) {
                throw new CustomValidationException("The Question is already inserted", 202);
            }
        }

        List<MultipartFile> images = questionRequestDTO.getImages();
        MultipartFile audio = questionRequestDTO.getAudio();

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(questionRequestDTO.getId());
        questionDTO.setQues(questionRequestDTO.getQuestion());
        questionDTO.setQuestTypeId(questionRequestDTO.getQuestionType());
        questionDTO.setAns(questionRequestDTO.getAns());
        questionDTO.setStatusId(1);
        questionDTO.setRelation(questionRequestDTO.getRelation());

        Question ques = questionRepo.save(modelMapper.map(questionDTO, Question.class));

        if (ques != null) {
            for (String answer : questionRequestDTO.getAnswers()) {
                McqAnsDTO ansDTO = new McqAnsDTO();
                ansDTO.setAns(answer);
                ansDTO.setQuestion(ques);
                ansRepo.save(modelMapper.map(ansDTO, McqAns.class));
            }

            if (images != null) {
                for (MultipartFile image : images) {
                    String path = saveFile(image, "images", String.valueOf(ques.getId()));
                    if (path.startsWith("Failed")) {
                        throw new CustomValidationException("Image file saving failed", 202);
                    }
                    QuesImageDTO imageDTO = new QuesImageDTO();
                    imageDTO.setLocation(path);
                    imageDTO.setQuestion(ques);
                    quesimagesRepo.save(modelMapper.map(imageDTO, QuesImage.class));
                }
            }

            if (audio != null && !audio.isEmpty()) {
                String path = saveFile(audio, "audio", String.valueOf(ques.getId()));
                if (path.startsWith("Failed")) {
                    throw new CustomValidationException("Audio file saving failed", 202);
                }
                QuesRecodingDTO recodingDTO = new QuesRecodingDTO();
                recodingDTO.setLocation(path);
                recodingDTO.setQuestion(ques);
                recRepo.save(modelMapper.map(recodingDTO, QuesRecoding.class));
            }
        } else {
            throw new CustomValidationException("Question Insertion failed", 202);
        }

        return "Question Saved successfully";
    }

    @SuppressWarnings("unused")
    public String updateQuestion(QuestionRequestDTO questionRequestDTO) {
        Question question = questionRepo.findById(questionRequestDTO.getId()).get();

        List<QuesImage> imgs = quesimagesRepo.findByQuestion(question);
        for (QuesImage quesImage : imgs) {
            deleteFile(quesImage.getLocation());
            if (!Files.exists(Paths.get("src/main/resources/static" + quesImage.getLocation()))) {
                quesimagesRepo.delete(quesImage);
            } else {
                throw new CustomValidationException("An Error Occured dusring the Progress", 203);
            }
        }

        Optional<QuesRecoding> d = recRepo.findByQuestion(question);
        if (d.isPresent()) {
            deleteFile(d.get().getLocation());
            if (!Files.exists(Paths.get("src/main/resources/static" + d.get().getLocation()))) {
                recRepo.delete(d.get());
            } else {
                throw new CustomValidationException("An Error Occured dusring the Progress", 203);
            }
        }

        if (questionRequestDTO.getAnswers().size() > 0) {
            List<McqAns> ans = ansRepo.findByQuestion(question);
            for (int i = 0; i < 4; i++) {
                ans.get(i).setAns(questionRequestDTO.getAnswers().get(i));
                ansRepo.save(ans.get(i));
            }
        }

        if (!questionRequestDTO.getQuestion().equals("Image Question")) {
            Question q = questionRepo.findByQues(questionRequestDTO.getQuestion(),
                    questionRequestDTO.getQuestionType());
            if (q != null && !q.getQues().equals(questionRequestDTO.getQuestion())) {
                throw new CustomValidationException("The Question is already inserted", 202);
            }
        }

        List<MultipartFile> images = questionRequestDTO.getImages();
        MultipartFile audio = questionRequestDTO.getAudio();

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(questionRequestDTO.getId());
        questionDTO.setQues(questionRequestDTO.getQuestion());
        questionDTO.setQuestTypeId(questionRequestDTO.getQuestionType());
        questionDTO.setAns(questionRequestDTO.getAns());
        questionDTO.setStatusId(1);
        questionDTO.setRelation(questionRequestDTO.getRelation());

        Question ques = questionRepo.save(modelMapper.map(questionDTO, Question.class));

        if (ques != null) {

            if (images != null) {
                for (MultipartFile image : images) {
                    String path = saveFile(image, "images", String.valueOf(ques.getId()));
                    if (path.startsWith("Failed")) {
                        throw new CustomValidationException("Image file saving failed", 202);
                    }
                    QuesImageDTO imageDTO = new QuesImageDTO();
                    imageDTO.setLocation(path);
                    imageDTO.setQuestion(ques);
                    quesimagesRepo.save(modelMapper.map(imageDTO, QuesImage.class));
                }
            }

            if (audio != null && !audio.isEmpty()) {
                String path = saveFile(audio, "audio", String.valueOf(ques.getId()));
                if (path.startsWith("Failed")) {
                    throw new CustomValidationException("Audio file saving failed", 202);
                }
                QuesRecodingDTO recodingDTO = new QuesRecodingDTO();
                recodingDTO.setLocation(path);
                recodingDTO.setQuestion(ques);
                recRepo.save(modelMapper.map(recodingDTO, QuesRecoding.class));
            }
        } else {
            throw new CustomValidationException("Question Insertion failed", 202);
        }

        return "Question Saved successfully";
    }

    public String deleteFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return "Failed: File path is null or empty";
        }

        try {
            Path absolutePath = Paths.get("src/main/resources/static" + filePath);

            if (Files.exists(absolutePath)) {
                Files.delete(absolutePath);
                return "File deleted successfully: " + filePath;
            } else {
                return "Failed: File does not exist at path: " + filePath;
            }
        } catch (IOException e) {
            return "Failed to delete file: " + e.getMessage();
        }
    }

    public List<QuestionDTO> getAllQuestions() {
        return modelMapper.map(questionRepo.findAll(), new TypeToken<List<QuestionDTO>>() {
        }.getType());
    }

    public String saveFile(MultipartFile file, String subDirectory, String id) {
        if (file.isEmpty()) {
            return "File is empty";
        }

        String contentType = file.getContentType();
        if (contentType == null || (!contentType.startsWith("image/") && !contentType.startsWith("audio/"))) {
            return "Failed: Invalid file type";
        }

        if (file.getSize() > 10 * 1024 * 1024) {
            return "Failed: File size exceeds limit";
        }

        try {
            String baseDirectory = "src/main/resources/static/uploads/";
            Path uploadPath = Paths.get(baseDirectory + subDirectory);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID().toString() + "-" + id + "-" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath);

            return "/uploads/" + subDirectory + "/" + fileName;
        } catch (IOException e) {
            return "Failed to save file";
        }
    }

    private boolean isStringInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<QuestionDTO> findQuesByQues(String ques, int qTypeId) {

        List<Question> data;

        if (qTypeId == 0) {
            if (isStringInteger(ques)) {
                data = questionRepo.findByIdLike(ques);
            } else {
                data = questionRepo.findByQuesLike(ques);
            }
            return modelMapper.map(data, new TypeToken<List<QuestionDTO>>() {
            }.getType());
        }

        if (isStringInteger(ques)) {
            data = questionRepo.findByIdLike(ques, qTypeId);
        } else {
            data = questionRepo.findByQuesLike(ques, qTypeId);
        }
        return modelMapper.map(data, new TypeToken<List<QuestionDTO>>() {
        }.getType());
    }

    public String createPaper(PaperQuestionsDTO dto) {
        PapersDTO papersDTO = modelMapper.map(dto, PapersDTO.class);
        papersDTO.setCDAT(LocalDateTime.now());

        while (papersRepo.findByName(papersDTO.getName()).isPresent()) {
            papersDTO.setName(papersDTO.getName() + "_1");
        }

        int pId = papersRepo.save(modelMapper.map(papersDTO, Papers.class)).getId();

        if (pId > 0) {
            dto.getData().forEach(item -> item.setPapersId(pId));

            List<PapersHasQuestions> entities = dto.getData().stream()
                    .map(item -> {
                        PapersHasQuestions entity = modelMapper.map(item, PapersHasQuestions.class);
                        entity.setId(0);
                        return entity;
                    })
                    .collect(Collectors.toList());

            papersHasQuesRepo.saveAll(entities);
        } else {
            throw new RuntimeException();
        }

        return "Paper created successfully";
    }

    public List<CPDTO> getCPQuestions(int pid) {
        List<CPDTO> data = new ArrayList<>();
        List<PapersHasQuestions> paperQ = papersHasQuesRepo.findAllByPapersId(pid);
        if (paperQ.size() > 0) {
            for (PapersHasQuestions item : paperQ) {
                CPDTO q = modelMapper.map(item.getQuestion(), CPDTO.class);

                q.setQDifficultyId(item.getQDifficulty().getId());
                q.setTime(item.getTime());

                q.setQuesImage(modelMapper.map(quesimagesRepo.findByQuestion(item.getQuestion()),
                        new TypeToken<List<CPDTO.Ques>>() {
                        }.getType()));

                q.setAnswers(modelMapper.map(ansRepo.findByQuestion(item.getQuestion()),
                        new TypeToken<List<CPDTO.Ans>>() {
                        }.getType()));

                Optional<QuesRecoding> qR = recRepo.findByQuestion(item.getQuestion());

                if (qR.isPresent()) {
                    q.setQuesRecoding(modelMapper.map(qR.get(), CPDTO.Ques.class));
                }

                data.add(q);
            }
            return data;
        } else {
            throw new CustomValidationException("Paper Do not exits", 203);
        }

    }

    public List<PapersDTO> getPaperD(String text) {
        return modelMapper.map(papersRepo.findByNameLike(text), new TypeToken<List<PapersDTO>>() {
        }.getType());
    }

    public String scheduleExam(ScheduleExamDTO dto) {
        dto.setStatusId(2);

        if (examsRepo.findAllByExId(dto.getExId()) != null) {
            return "Exam Already Exist with this Examination Id";
        }
        int eId = examsRepo.save(modelMapper.map(dto, Exams.class)).getId();

        dto.getStudents().forEach(student -> {
            UsersHasExamsDTO examsDTO = new UsersHasExamsDTO();
            examsDTO.setExamsId(eId);
            examsDTO.setUsersId(student.getId());
            userHasExamsRepo.save(modelMapper.map(examsDTO, UsersHasExams.class));
        });
        return "Exam Registered Successful";
    }
}
