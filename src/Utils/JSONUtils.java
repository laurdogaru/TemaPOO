package Utils;

import Cells.CellEnum;
import Entities.Account;
import Entities.Character;
import Entities.CharacterFactory;
import Entities.Credentials;
import Exceptions.InformationIncompleteException;
import org.json.*;
import java.io.IOException;
import java.io.File;
import java.util.*;

public class JSONUtils {
    public static ArrayList<Account> getAccounts(String fileName) {
        String jsonAccountsString = null;
        try {
            jsonAccountsString = new Scanner(new File(fileName)).
                    useDelimiter("\\Z").next();
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }

        JSONObject jsonAccountsObject = new JSONObject(jsonAccountsString);
        JSONArray accountArray = jsonAccountsObject.getJSONArray("accounts");
        ArrayList<Account> accounts = new ArrayList<>();

        for(int i = 0 ; i < accountArray.length() ; i++) {
            Credentials credentials = new Credentials();
            credentials.setMail(accountArray.getJSONObject(i).
                    getJSONObject("credentials").getString("email"));
            credentials.setPassword(accountArray.getJSONObject(i).
                    getJSONObject("credentials").getString("password"));

            TreeSet<String> favouriteGames = new TreeSet<>();
            JSONArray gamesArray = accountArray.getJSONObject(i).getJSONArray("favorite_games");
            for(int j = 0 ; j < gamesArray.length() ; j++) {
                favouriteGames.add(gamesArray.getString(j));
            }
            Account.Information inf = null;
            try {
                inf = new Account.Information.InformationBuilder
                        (credentials,
                                accountArray.getJSONObject(i).getString("name")).
                        country(accountArray.getJSONObject(i).getString("country")).
                        favouriteGames(favouriteGames).
                        build();
            }
            catch (InformationIncompleteException e) {
                System.out.println(e.getMessage());
            }

            ArrayList<Character> characters = new ArrayList<>();
            JSONArray charactersArray = accountArray.getJSONObject(i).getJSONArray("characters");
            for(int j = 0 ; j < charactersArray.length() ; j++) {
                Character character = CharacterFactory.createCharacter(CharacterFactory.CharacterType
                                .valueOf(charactersArray.getJSONObject(j).getString("profession").toUpperCase()),
                        charactersArray.getJSONObject(j).getString("name"),
                        charactersArray.getJSONObject(j).getInt("level"),
                        charactersArray.getJSONObject(j).getInt("experience"));
                characters.add(character);
            }
            int mapsCompleted = accountArray.getJSONObject(i).getInt("maps_completed");

            Account account = new Account(inf, characters, mapsCompleted);
            accounts.add(account);
        }
        return accounts;
    }
    public static HashMap<CellEnum, ArrayList<String>> getStories(String fileName) {
        String jsonStoriesString = null;
        try {
            jsonStoriesString = new Scanner(new File(fileName)).
                    useDelimiter("\\Z").next();
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }

        JSONObject jsonStoriesObject = new JSONObject(jsonStoriesString);
        JSONArray storiesArray = jsonStoriesObject.getJSONArray("stories");
        HashMap<CellEnum, ArrayList<String>> map = new HashMap<>();
        for(CellEnum element : CellEnum.values()) {
            map.put(element, new ArrayList<>());
        }
        for(int i = 0 ; i < storiesArray.length(); i++) {
            String type = storiesArray.getJSONObject(i).getString("type");
            String value = storiesArray.getJSONObject(i).getString("value");
            map.get(CellEnum.valueOf(type)).add(value);
        }
        return map;
    }
}
