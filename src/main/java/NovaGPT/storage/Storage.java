package novagpt.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import novagpt.task.Deadline;
import novagpt.task.Event;
import novagpt.task.Task;
import novagpt.task.Todo;


/**
 * Handles saving and loading tasks from a text file
 */
public class Storage {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private final Path filePath;

    /**
     * Constructs a Storage object with the given file path
     * @param filepath to the file used for saving and loading of tasks
     */
    public Storage(String filepath) {
        this.filePath = Paths.get(filepath);
    }

    /**
     * Loads tasks from the storage file
     * Creates the file or parent directories if they do not exist
     * @return the ArrayList
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            ensureFileDirectoriesExists();
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                Task t = parseLine(line);
                if (t != null) {
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            //throw new IOException("OOPS: Error loading file" + e.getMessage());
            System.out.println("OOPS: Error loading file" + e.getMessage());
        }
        return tasks;
    }
    private void ensureFileDirectoriesExists() throws IOException {
        Path parent = filePath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }

    /**
     * Parses a line of text and reconstructs the corresponds tasks
     * @param line from the storage file
     * @return reconstructed task or null if the line is invalid
     */
    public Task parseLine(String line) {
        try {
            String[] details = line.split("\\|");
            for (int i = 0; i < details.length; i++) {
                details[i] = details[i].trim();
            }
            String taskType = details[0].toUpperCase();
            boolean isDone = details[1].equals("1");
            Task task = null;

            switch (taskType) {
            case "T":
                task = new Todo(details[2]);
                break;
            case "D":
                task = new Deadline(details[2], details[3]);
                break;
            case "E":
                task = new Event(details[2], details[3], details[4]);
                break;
            default:
                return null;
            }

            if (task != null && isDone) {
                task.mark();
            }
            return task;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Saves tasks into specified text file
     * @param tasks list of tasks to save
     */
    public void save(ArrayList<Task> tasks) {
        try {
            List<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                String line;
                String status = task.getStatus() ? "1" : "0";
                if (task instanceof Todo) {
                    line = String.join(" | ", "T",
                            status,
                            task.getTaskDescription());
                } else if (task instanceof Deadline) {
                    line = String.join(" | ", "D",
                            status,
                            task.getTaskDescription(), ((Deadline) task).getEndTimeAndDate().format(FORMAT));
                } else if (task instanceof Event) {
                    line = String.join(" | ", "E",
                            status,
                            task.getTaskDescription(), (
                                    (Event) task).getStartTimeAndDate().format(FORMAT), (
                                    (Event) task).getEndTimeAndDate().format(FORMAT));
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
