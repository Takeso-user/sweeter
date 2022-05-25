package pl.malcew.servingwebcontent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.malcew.servingwebcontent.accessingdatamysql.Message;
import pl.malcew.servingwebcontent.accessingdatamysql.MessageRepository;

import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {

        return "greeting";
    }

    @GetMapping("/main")
    public String index(Map<String, Object> model) {
        Iterable<Message> messageRepositoryAll = messageRepository.findAll();
        model.put("messages", messageRepositoryAll);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @RequestParam String text,
            @RequestParam String tag,
            Map<String, Object> model) {
        Message message = new Message(text, tag);
        messageRepository.save(message);
        Iterable<Message> messageRepositoryAll = messageRepository.findAll();
        model.put("messages", messageRepositoryAll);
        return "main";
    }

    @PostMapping("filter")
    public String filter(
            @RequestParam String filter,
//            @RequestParam String tag,
            Map<String, Object> model) {

        Iterable<Message> messages =
                filter != null && !filter.isEmpty()
                        ? messageRepository.findByTag(filter)
                        : messageRepository.findAll();
        model.put("messages", messages);
        return "main";
    }
}
