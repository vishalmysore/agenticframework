package io.github.vishalmysore.loop;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

/**
 * This class uses cyclic behaviour to continuously send messages to the LoopReceiverAgent.
 * There are other types of behaviours that can be used to send messages, such as OneShotBehaviour.
 * and WakerBehaviour. WakerBehaviour can be used to send messages at specific intervals.
 * here is the list of other behaviours: https://jade.tilab.com/doc/api/jade/core/behaviours/Behaviour.html
 and
 */
@Log
public class LoopSenderAgent extends Agent {
    protected void setup() {
        log.info("LoopSenderAgent " + getLocalName() + " is ready.");

        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                // Create a new ACLMessage
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.setContent("Hello, ReceiverAgent!");

                // Set the receiver of the message to the ReceiverAgent
                msg.addReceiver(new jade.core.AID("LoopReceiverAgent", jade.core.AID.ISLOCALNAME));

                // Send the message
                send(msg);

                // Receive the reply
                ACLMessage reply = receive();
                if (reply != null) {
                    log.info("Received reply: " + reply.getContent());
                } else {
                    block();
                }

                // Add delay
                try {
                    Thread.sleep(2000); // Delay of 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
