import controller.MVCController;
import view.MainView;

public class Main {

	public static void main(String[] args) {
		MainView v = new MainView();
		new MVCController(v);
	}
}
