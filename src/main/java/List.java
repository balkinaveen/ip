public class List {
    String[] str;
    int counter = 0;
    public List(int size) {
        str = new String[size];
    }

    public void add(String text) {
        str[counter] = (counter + 1) + ". " + text;
        counter++;
    }
    @Override
    public String toString() {
        String output  = "";
        for(int j = 0; j < counter; j++) {
            if (j < counter - 1) {
                output += str[j] + "\n";
            } else {
                output += str[j];
            }
        }
        return output;
    }

}
