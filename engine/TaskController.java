package engine;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@RestController
public class TaskController {

    private List<Quiz> quizzes = new ArrayList<>();
    private int counter = 1;

    @GetMapping("/api/quizzes/{id}")
    public Quiz getQuizById(@PathVariable int id) {
        try {
            return quizzes.get(id - 1);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/quizzes")
    public List<Quiz> getAllQuizzes() {
        return quizzes;
    }

    @PostMapping("/api/quizzes")
    public Quiz checkAnswer(@RequestBody Quiz quiz) {
        Quiz quizToAdd = new Quiz();
        quizToAdd.setId(counter);
        quizToAdd.setTitle(quiz.getTitle());
        quizToAdd.setText(quiz.getText());
        quizToAdd.setOptions(quiz.getOptions());
        quizToAdd.setAnswer(quiz.getAnswer());
        quizzes.add(quizToAdd);
        counter++;
        return quizToAdd;

    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Response checkAnswer(@PathVariable int id, @RequestParam("answer") int answer) {
        try {
            if (quizzes.get(id-1).getAnswer() == answer) {
                return new Response(true, "Congratulations, you're right!");
            } else {
                return new Response(false, "Wrong answer! Please, try again.");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
