package robotics.scouting.current;

import android.util.Log;

import java.util.Comparator;

public class recycler {
    String team;
    public recycler(String team) {
        this.team = team;
    }
    public static Comparator<recycler> teamComparator = (t1, t2) -> t1.team.compareTo(t2.team);
}