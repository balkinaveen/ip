# NovaGPT Chatbot

NovaGPT is a task management chatbot with a simple GUI built using JavaFX. It helps users manage various type of tasks, track completion, search by keyword and get reminders for upcoming tasks.

This project was iteratively developed based on a greenfield Java project named Duke as part of **CS2103T: Software Engineering** at National University of Singapore. Given below are instructions on how to use it.

## Features of the Chatbot
- **Task Management**
  - Add Todo, Deadline and Event Tasks
  - List (List all stored Tasks)
  - Mark/Unmark tasks as done
  - Delete tasks
- **Search**
  - Find tasks by keyword
- **Reminders**
  - Get notified about tasks due in the next specified number of days
- **Save**
  - Taks are automatically saved to a file and loaded on startup

## Getting started
Prerequisites: JDK 17

1. Download the latest novagpt.jar file from [here](https://github.com/balkinaveen/ip/releases/).<br>
2. Open a terminal in the folder where the jar is located.
3. Run:
   ```
   java -jar novagpt.jar
   ```
## Usage of the Chatbot
When NovaGPT starts, you'll see:
<p align="left">
<img width="591" height="740" alt="image" src="https://github.com/user-attachments/assets/60ebbcfe-d3f9-417b-ac0f-bc6a334bf29e" />
</p>

### Example Commands
`todo read book` → Adds a todo task to the list <br>
`deadline submit report /by 15/09/2025 2359` → Adds a deadline to the list <br>
`event CS 2103T project meeting /from 15/09/2025 1500 /to 15/09/2025 1700` → Adds an event to the list <br>
`list` → Lists all tasks <br>
`mark 1` → Marks task 1 in the list as done <br>
`unmark 2` → Unmarks task 2 <br>
`delete 3` → Deletes task 3 <br>
`find report` → Finds task containing "report" <br>
`reminder 5` → Shows upcomcing tasks within the next 5 days <br>
`man` → Shows the list of supported prompts
`bye` → Exits the program <br>

## Data Storage
- Tasks are stored in a text file under the `./data` directory
- On startup, NovaGPT automatically loads tasks from this file
- If the file and directories does not exist, a new one is created.
