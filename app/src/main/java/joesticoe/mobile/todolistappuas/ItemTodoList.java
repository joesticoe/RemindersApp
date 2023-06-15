package joesticoe.mobile.todolistappuas;

public class ItemTodoList {
    private int id;
    private String what, time;

    public ItemTodoList(int id,String what,String time){
        this.id = id;
        this.what = what;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
