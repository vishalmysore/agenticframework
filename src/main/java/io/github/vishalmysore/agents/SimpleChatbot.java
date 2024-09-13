package io.github.vishalmysore.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class SimpleChatbot extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                // Receive the next message
                ACLMessage msg = receive();

                if (msg != null) {
                    // React to the message
                    String content = msg.getContent();
                    String response;

                    // Define responses based on the content of the message
                    if (content.contains("Hello")) {
                        response = "Hello, how can I assist you today?";
                    } else if (content.contains("How are you")) {
                        response = "I'm a bot, I don't have feelings, but thank you for asking!";
                    } else {
                        response = "I'm sorry, I didn't understand that. Could you please rephrase?";
                    }

                    // Create a reply
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent(response);

                    // Send the reply
                    send(reply);
                } else {
                    block();
                }
            }
        });
    }
}