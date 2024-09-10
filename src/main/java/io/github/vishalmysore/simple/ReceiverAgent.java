package io.github.vishalmysore.simple;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * ReceiverAgent is a JADE agent that receives a message from another agent.
 * This agent uses a Behaviour to continuously check for incoming messages.
 */
@Slf4j
public class ReceiverAgent extends Agent {

    /**
     * The setup method is called when the agent is started.
     * It adds a Behaviour to the agent, which continuously checks for incoming messages.
     */
    protected void setup() {
        log.info("ReceiverAgent " + getLocalName() + " is ready.");

        addBehaviour(new Behaviour() {
            boolean messageReceived = false;
            /**
             * The action method is called when the behaviour is executed.
             * It checks for an incoming message and prints it if one is received.
             */
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    log.info("Message received: " + msg.getContent());
                    messageReceived = true;
                } else {
                    block();
                }
            }
            /**
             * The done method is called to determine if the behaviour is done executing.
             * It returns true if a message has been received, false otherwise.
             */
            public boolean done() {
                return messageReceived;
            }
        });
    }
}