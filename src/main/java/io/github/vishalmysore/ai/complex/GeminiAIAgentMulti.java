package io.github.vishalmysore.ai.complex;

import com.t4a.processor.AIProcessingException;
import com.t4a.processor.GeminiV2ActionProcessor;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.java.Log;

@Log
public class GeminiAIAgentMulti  extends Agent {
    private GeminiV2ActionProcessor processor;

    public GeminiAIAgentMulti() {

        processor = new GeminiV2ActionProcessor();
        log.info("GeminiAIAgentMulti constructor");
    }

    @Override
    protected void setup() {

        log.info("GeminiAIAgentMulti " + getLocalName() + " is ready.");
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                String question = null;
                // Create a new ACLMessage
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

                    try {
                        question = processor.query("Give me a random question?");
                        msg.setContent(question);
                    } catch (AIProcessingException e) {
                        throw new RuntimeException(e);
                    }



                // Set the receiver of the message to the multiple
                msg.addReceiver(new jade.core.AID("OpenAiAgent", jade.core.AID.ISLOCALNAME));
                msg.addReceiver(new jade.core.AID("OpenAiAgent2", jade.core.AID.ISLOCALNAME));

                // Send the message
                send(msg);

                // Receive the reply
                MessageTemplate template1 = MessageTemplate.MatchSender(new AID("OpenAiAgent", AID.ISLOCALNAME));
                MessageTemplate template2 = MessageTemplate.MatchSender(new AID("OpenAiAgent2", AID.ISLOCALNAME));

                // Receive the replies using the templates
                ACLMessage replyACL1 = receive(template1);
                ACLMessage replyACL2 = receive(template2);
                if ((replyACL1 != null)&&(replyACL2 != null)){
                // Determine the senders
                String sender1 = replyACL1.getSender().getName();
                String sender2 = replyACL2.getSender().getName();

                String replyfrom1 = replyACL1.getContent();
                String replyfrom2 = replyACL2.getContent();
                log.info("Reply from " + sender1 + ": " + replyfrom1);
                log.info("Reply from " + sender2 + ": " + replyfrom2);

                    try {
                        String judge = processor.query("This was my question " + question + " and this was the answer from " + sender1 + " " + replyfrom1 + " and this was the answer from " + sender2 + " " + replyfrom2+" who is correct? Pick only 1");
                        log.info("=====================================");
                        log.info("=====================================");
                        log.info("Judge: " + judge);
                        log.info("=====================================");
                        log.info("=====================================");
                    } catch (AIProcessingException e) {
                        throw new RuntimeException(e);
                    }


                } else {
                    block();
                }

                // Add delay
                try {
                    Thread.sleep(8000); // Delay of 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}