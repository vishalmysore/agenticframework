package io.github.vishalmysore.ai;

import com.t4a.processor.AIProcessingException;
import com.t4a.processor.GeminiV2ActionProcessor;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.java.Log;

@Log
public class GeminiAiAgent extends Agent {
    private GeminiV2ActionProcessor processor;
    private String reply = null;
    public GeminiAiAgent() {

        processor = new GeminiV2ActionProcessor();
        log.info("GeminiAiAgent constructor");
    }

    @Override
    protected void setup() {

        log.info("GeminiAiAgent " + getLocalName() + " is ready.");
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                // Create a new ACLMessage
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                if(reply != null) {
                    try {
                        reply = processor.query(reply);
                    } catch (AIProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    msg.setContent(reply);
                    log.info("GeminiAiAgent Sending reply: " + reply);
                } else {
                    try {
                        msg.setContent(processor.query("Give me a random question?"));
                    } catch (AIProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }


                // Set the receiver of the message to the ReceiverAgent
                msg.addReceiver(new jade.core.AID("OpenAiAgent", jade.core.AID.ISLOCALNAME));

                // Send the message
                send(msg);

                // Receive the reply
                ACLMessage replyACL = receive();
                log.info("GeminiAiAgent Received reply: " + replyACL);
                if (replyACL != null) {
                    log.info("GeminiAiAgent Received reply: " + replyACL.getContent());
                    reply = replyACL.getContent();

                } else {
                    block();
                }

                // Add delay
                try {
                    Thread.sleep(5000); // Delay of 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
