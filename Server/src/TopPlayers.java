import java.io.Serializable;
import java.util.ArrayList;

public class TopPlayers implements Serializable {

	ArrayList<String> names;
	ArrayList<Integer> scores;

	public ArrayList<String> getNames() {
		return names;
	}

	public void setNames(ArrayList<String> names) {
		this.names = names;
	}

	public ArrayList<Integer> getScores() {
		return scores;
	}

	public void setScores(ArrayList<Integer> scores) {
		this.scores = scores;
	}

}
