package io.github.vishalmysore.loop;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Log
public class LoopReceiverAgent extends Agent {

    protected void setup() {
        log.info("LoopReceiverAgent " + getLocalName() + " is ready.");

        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    log.info("Message received: " + msg.getContent());

                    // Create a reply message
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("Message received, thank you!");

                    // Send the reply
                    send(reply);
                    // Add delay
                    try {
                        Thread.sleep(2000); // Delay of 1 second
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