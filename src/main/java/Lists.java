public class Lists {
    Task[] list;
    int counter = 0;
    public Lists(int size) {
        list = new Task[size];
    }

    public int getCounter() {
        return this.counter;
    }

    public void add(Task t) {
        NovaGPT.printer("Got it. I've added this task:\n"
                + t.toString() + "\nNow you have "
                + (this.getCounter() + 1)
                + " tasks in the list.");
        list[counter] = t;
        this.counter++;
    }

    public void mark(int num) {
        list[num].mark();
    }

    public void unMark(int num) {
        list[num].unMark();
    }

    public Task retriveTask(int num) {
        return list[num];
    }

    @Override
    public String toString() {
        String output  = "";
        for(int j = 0; j < counter; j++) {
            if (j < counter - 1) {
                output += (j + 1) + ". " + list[j].toString() + "\n";
            } else {
                output += (j + 1) + ". " + list[j].toString();
            }
        }
        return output;
    }

}
