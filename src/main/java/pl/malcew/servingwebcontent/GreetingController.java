package pl.malcew.servingwebcontent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.malcew.servingwebcontent.accessingdatamysql.Message;
import pl.malcew.servingwebcontent.accessingdatamysql.MessageRepository;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String index(Map<String, Object> model) {
        Iterable<Message> messageRepositoryAll = messageRepository.findAll();
        model.put("messages", messageRepositoryAll);
        return "main";
    }

    @PostMapping
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
                filter !=null && !filter.isEmpty()
                ? messageRepository.findByTag(filter)
                : messageRepository.findAll();
        model.put("messages",messages);
        return "main";
    }
}
