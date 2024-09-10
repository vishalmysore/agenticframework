package io.github.vishalmysore.simple;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import lombok.extern.slf4j.Slf4j;

// First agent that sends a message
/**
 * HelloAgent is a JADE agent that sends a single message to another agent.
 * This agent uses the OneShotBehaviour to send a message once and then terminate.
 */
@Slf4j
public class HelloAgent extends Agent {

    /**
     * The setup method is called when the agent is started.
     * It adds a OneShotBehaviour to the agent, which sends a message to another agent.
     */
    protected void setup() {
        log.info("Hello! Agent " + getLocalName() + " is ready.");

        addBehaviour(new OneShotBehaviour() {
            /**
             * The action method is called when the behaviour is executed.
             * It creates a new ACLMessage, sets the receiver and content, and sends the message.
             */
            public void action() {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("ReceiverAgent", AID.ISLOCALNAME));
                msg.setContent("Hello from HelloAgent!");
                send(msg);
                log.info("Message sent: " + msg.getContent());
            }
        });
    }
}


