package robotics.scouting.current;

import android.util.Log;

import java.util.Comparator;

public class recycler {
    String team;
    public recycler(String team) {
        this.team = team;
    }
    public static Comparator<recycler> teamComparator = new Comparator<recycler>() {
        @Override
        public int compare(recycler t1, recycler t2) {
            Log.d("TEAM COMPARE", "compare: "+t2.team.compareTo(t1.team));
            return t2.team.compareTo(t1.team);
        }
    };
}
