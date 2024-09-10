package io.github.vishalmysore.ai;

import com.t4a.processor.AIProcessingException;
import com.t4a.processor.OpenAiActionProcessor;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.java.Log;

@Log
public class OpenAiAgent extends Agent {
    private OpenAiActionProcessor processor;
    private String reply = null;
    public OpenAiAgent() {
        processor = new OpenAiActionProcessor();
    }
    protected void setup() {
        log.info("OpenAiAgent " + getLocalName() + " is ready.");

        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    log.info("OpenAI Message received: " + msg.getContent());

                    // Create a reply message
                    ACLMessage reply = msg.createReply();
                    reply.addReceiver(new jade.core.AID("GeminiAiAgent", jade.core.AID.ISLOCALNAME));
                    reply.setPerformative(ACLMessage.INFORM);
                    try {
                        String replyStr= processor.query(msg.getContent());
                        log.info("OpenAI Sending reply: " + replyStr);
                        reply.setContent(replyStr);
                    } catch (AIProcessingException e) {
                        throw new RuntimeException(e);
                    }

                    // Send the reply
                    send(reply);
                    // Add delay
                    try {
                        Thread.sleep(5000); // Delay of 1 second
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    block();
                }
            }
        });
    }


}
