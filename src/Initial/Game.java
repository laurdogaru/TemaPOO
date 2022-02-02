package Initial;

import Cells.*;
import Entities.*;
import GUI.LoginWindow;
import Tests.*;
import Utils.*;

import java.awt.*;
import java.io.IOException;
import java.util.*;

public class Game {
    public ArrayList<Account> accounts;
    HashMap<CellEnum, ArrayList<String>> stories;
    private static Game instance = null;
    private Random random = new Random();
    public static boolean startMode;

    private Game() {

    }

    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }
        return instance;
    }
    public void run(Grid grid) throws FontFormatException {
        Scanner scanner = new Scanner(System.in);
        accounts = JSONUtils.getAccounts("src\\Extras\\accounts.json");
        stories = JSONUtils.getStories("src\\Extras\\stories.json");
        System.out.println("0 - PredefinedCLI");
        System.out.println("1 - CLI");
        System.out.println("2 - GUI");
        System.out.print("Choose gamemode: ");
        int mode = scanner.nextInt();
        if( mode == 0 || mode == 1 ) {
            startMode = true;
            for(int i = 0 ; i < accounts.size(); i++) {
                System.out.println(i + ": " + accounts.get(i).information.getName());
            }
            System.out.print("Choose account:");
            int index = scanner.nextInt();
            Account chosenAccount = accounts.get(index);
            chosenAccount.mapsCompleted++;

            for(int i = 0 ; i < chosenAccount.characters.size(); i++) {
                System.out.println(i + ": " + chosenAccount.characters.get(i).name);
            }
            System.out.print("Choose character:");
            index = scanner.nextInt();
            scanner.nextLine();
            if(mode == 1) {
                CLITest.cliTest(chosenAccount.characters.get(index));
            }
            else {
                grid.currentCharacter = chosenAccount.characters.get(index);
            }
        } else if(mode == 2) {
            startMode = false;
            try {
                new LoginWindow();
            }
            catch (FontFormatException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            System.exit(0);
        }
    }
    public void showOptions(Cell currentCell) {
        if( currentCell.type == CellEnum.FINISH) {
            System.out.println("Congratulations! You won!");
            System.exit(0);
        }
        System.out.print("Next command: ");
    }
    public void showStory(Cell curentCell) {
        if( !curentCell.visited ) {
            ArrayList<String> storyType = stories.get(curentCell.type);
            int index = random.nextInt(storyType.size());
            System.out.println(storyType.get(index));
            curentCell.visited = true;
        }
    }
}
