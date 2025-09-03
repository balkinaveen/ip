package NovaGPT.storage;

import NovaGPT.task.Task;
import NovaGPT.task.Todo;
import NovaGPT.task.Deadline;
import NovaGPT.task.Event;

import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

/**
 * Represents a Storage class, which reads and writes from a txt file
 * This class contains methods to load and save tasks
 */
public class Storage {
    private final Path filePath;
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    public Storage(String filepath) {
        this.filePath = Paths.get(filepath);
    }

    /**
     * checks if the directory and file exists
     * creates the directory and file if required
     * initialise an empty ArrayList
     * loads the tasks from the specified file into the ArrayList
     * returns the ArrayList
     */
    public ArrayList<Task> load(){
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                Task t = parseLine(line);
                if(t != null) {
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            //throw new IOException("OOPS: Error loading file" + e.getMessage());
            System.out.println("OOPS: Error loading file" + e.getMessage());
        }
        return tasks;
    }

    /**
     * helper method
     * Parses each line and re-creates the respective tasks with its details
     */
    public Task parseLine(String line) {
        try {
            String[] details = line.split("\\|");
            for (int i = 0; i < details.length; i++) {
                details[i] = details[i].trim();
            }
            String typeTask = details[0];
            boolean isDone = details[1].equals("1");
            Task t = null;

            if (details[0].toUpperCase().equals("T")) {
                t = new Todo(details[2]);
            } else if (details[0].toUpperCase().equals("D")) {
                t = new Deadline(details[2], details[3]);
            } else if (details[0].toUpperCase().equals("E")) {
                t = new Event(details[2], details[3], details[4]);
            }

            if(t != null && isDone) {
                t.mark();
            }
            return t;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * for each task in list, it saves its details as a String in a pre-determined format
     * and writes it to the file specified.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            List<String> lines = new ArrayList<>();
            for(int i = 0; i < tasks.size(); i++) {
                String line = "";
                Task t = tasks.get(i);
                String status = t.getStatus() ? "1" : "0";
                if (t instanceof Todo) {
                    line = String.join(" | ", "T", status, t.getTaskDescription());
                } else if (t instanceof Deadline) {
                    line = String.join(" | ", "D", status, t.getTaskDescription(), ((Deadline) t).getEndTimeAndDate().format(FORMAT));
                } else if (t instanceof Event) {
                    line = String.join(" | ", "E", status, t.getTaskDescription(), ((Event) t).getStartTimeAndDate().format(FORMAT), ((Event) t).getEndTimeAndDate().format(FORMAT));
                } else {
                    continue;
                }
                lines.add(line);
            }
            Files.write(filePath, lines);
        } catch (IOException e) {
            //throw new IOException("OOPS: Error loading file" + e.getMessage());
            System.out.println("OOPS: Error loading file" + e.getMessage());
        }
    }
}
