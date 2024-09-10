# AF4J

AF4J is Agentic Framework for Java. It demonstrates creation of AI agents using the Java Agent DEvelopment Framework (JADE) and [Tools4AI](https://github.com/vishalmysore/Tools4AI).

[JADE](https://jade-project.gitlab.io/page/guidesandtutorials/) (Java Agent DEvelopment Framework) is a software framework fully implemented in the Java language. It simplifies the implementation of multi-agent systems through a middle-ware that complies with the FIPA (Foundation for Intelligent Physical Agents) specifications and through a set of graphical tools that support the debugging and deployment phases. The agent platform can be distributed across machines (which do not even need to share the same OS) and the configuration can be controlled via a remote GUI. The configuration can be even changed at run-time by moving agents from one machine to another one, as and when required.

[FIPA](http://www.fipa.org/) is an IEEE Computer Society standards organization that promotes agent-based technology and the interoperability of its standards with other technologies. JADE, being FIPA-compliant, means that it follows the standards set by FIPA for interoperable intelligent multi-agent systems.

## Project Structure

The project consists of two main Java classes:

- `HelloAgent.java`: This class represents an agent that sends a message to another agent.
- `ReceiverAgent.java`: This class represents an agent that receives the message sent by `HelloAgent`.

## Dependencies

The project uses the following dependencies:

- `com.gitlab.jade-project:jade:master-SNAPSHOT`

These dependencies are managed using Maven.

## Building and Running

To build and run the project, use the following Maven command:

```bash
mvn clean install
```

Download the latest Jade binary from the [official website](https://jade-project.gitlab.io/page/download/) and add it to the project's classpath.

You will need to add openai key to the tools4ai.properties file. You can get the key from the OpenAI website.

to run the project, use the following command:

```bash
java -cp jade-4.5.0.jar;target/classes jade.Boot -gui HelloAgent:io.github.vishalmysore.simple.HelloAgent;ReceiverAgent:io.github.vishalmysore.simple.ReceiverAgent
```

or you can execute provide runSimple.bat script to run the project.

Another example which demonstrates the cyclical communication between agents is also provided in the project. To run the cyclical communication example, use the following command:

```bash
java -cp jade-4.5.0.jar;target/classes jade.Boot -gui LoopSenderAgent:io.github.vishalmysore.loop.LoopSenderAgent;LoopReceiverAgent:io.github.vishalmysore.loop.LoopReceiverAgent
```

To execute the AI Agents for Gemini and OpenAi which talk to each other, use the following command:

```bash
java -cp jade-4.5.0.jar;target/af4j-1.0-SNAPSHOT-jar-with-dependencies.jar jade.Boot -gui GeminiAiAgent:io.github.vishalmysore.ai.GeminiAiAgent;OpenAiAgent:io.github.vishalmysore.ai.OpenAiAgent
```

![Screenshot](screen.png)
