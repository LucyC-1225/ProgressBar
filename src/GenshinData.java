import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class GenshinData {
    private String characterUrl = "https://genshinlist.com/api/characters";
    private String weaponUrl = "https://genshinlist.com/api/weapons";

    private ArrayList<Character> characters;
    private ArrayList<Weapon> weapons;

    public GenshinData() {
        String characterAPICall = makeAPICall(characterUrl);
        characters = new ArrayList<Character>();
        parseJSONCharacters(characterAPICall, characters);

        String weaponAPICall = makeAPICall(weaponUrl);
        weapons = new ArrayList<Weapon>();
        parseJSONWeapons(weaponAPICall, weapons);

    }

    public String makeAPICall(String url) {
        try {
            URI myUri = URI.create(url); // creates a URI object from the url string
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void parseJSONCharacters(String json, ArrayList<Character> characters) {
        JSONArray characterList = new JSONArray(json);

        for (int i = 0; i < characterList.length(); i++)
        {
            JSONObject characterObj = characterList.getJSONObject(i);
            String characterName = characterObj.getString("name");
            String characterDescription = characterObj.getString("description");
            int rarity = characterObj.getInt("rarity");
            String vision = characterObj.getString("vision");
            String weapon = characterObj.getString("weapon");

            Character character = new Character(characterName, characterDescription, rarity, vision, weapon);
            this.characters.add(character);
        }
    }
    public void parseJSONWeapons(String json, ArrayList<Weapon> weapons) {
        JSONArray weaponList = new JSONArray(json);

        for (int i = 0; i < weaponList.length(); i++)
        {
            JSONObject weaponObj = weaponList.getJSONObject(i);
            String weaponName = weaponObj.getString("name");
            int rarity = weaponObj.getInt("rarity");
            String type = weaponObj.getString("type");

            Weapon weapon = new Weapon(weaponName, rarity, type);
            this.weapons.add(weapon);
        }
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }
}
