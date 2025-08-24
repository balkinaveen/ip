public class Lists {
    Task[] list;
    int counter = 0;
    public Lists(int size) {
        list = new Task[size];
    }

    public void add(Task t) {
        NovaGPT.printer("added: " + t.description());
        list[counter] = t;
        counter++;
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
