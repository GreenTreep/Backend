package fr.parisnanterre.greentrip.backend.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request) {
        List<Map<String, String>> messages = request.getMessages();

        if (messages == null || messages.isEmpty()) {
            return new ChatResponse("❗ Je n’ai pas compris. Essaie de reformuler !");
        }

        String userMessage = messages.get(messages.size() - 1).get("content").toLowerCase();
        String botReply;

        if (userMessage.contains("randonnée") || userMessage.contains("rando")) {
            botReply = "🏞️ Voici quelques idées de randonnées en Île-de-France :\n"
                     + "• Forêt de Fontainebleau 🌳\n"
                     + "• Parc naturel du Vexin 🗺️\n"
                     + "• Gorges de Franchard 🥾\n"
                     + "• Promenade bleue le long de la Seine 🚶‍♀️";
        } else if (userMessage.contains("bonjour") || userMessage.contains("salut")) {
            botReply = "👋 Bonjour ! Je suis ton assistant GreenTrip. Comment puis-je t’aider aujourd’hui ?";
        } else if (userMessage.contains("merci")) {
            botReply = "🙏 Avec plaisir ! N’hésite pas si tu as d’autres questions.";
        } else {
            botReply = "🤖 Je suis en mode démo pour le moment. Pose-moi une question sur la randonnée, les trajets ou dis simplement bonjour !";
        }

        return new ChatResponse(botReply);
    }
}
