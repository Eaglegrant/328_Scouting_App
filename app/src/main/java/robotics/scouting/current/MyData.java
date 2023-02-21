package robotics.scouting.current;

public class MyData {
    private String title;
    private String points;
    private String data;
    private String match;

    public MyData(String title, String pointsT, String dataT, String matchT) {
        this.title = title;
        this.points = pointsT;
        this.data = dataT;
        this.match = matchT;
    }

    public String getTitle() {
        return title;
    }
    public String getMatch() {
        return match;
    }

    public String getPoints() {
        return points;
    }
    public String getAllData(){
        return data;
    }
}