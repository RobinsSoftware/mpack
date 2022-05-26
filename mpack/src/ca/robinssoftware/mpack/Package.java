package ca.robinssoftware.mpack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.json.JSONObject;

public class Package extends JSONFile {
    
    private Category category;
    private String name, author, latest, info;
    private HashMap<String, String> versions;
    private UUID id;
    private int revision;
    
    Package(Main main, UUID id, JSONObject options) throws IOException {
        super(new File(main.getDataFolder().getPath() + "/packagedata/" + id + ".mpack"), true, options);
    }
    
    Package(Main main, File file) throws IOException {
        super(file);
    }
    
    Package(Main main, UUID id) throws IOException {
        super(new File(main.getDataFolder().getPath() + "/packagedata/" + id + ".mpack"));
        
        name = options.getString("name");
        author = options.getString("author");
        this.id = UUID.fromString(options.getString("id"));
        revision = options.getInt("revision");
        category = Category.of(options.getString("category"));
        latest = options.getString("latest");
        info = options.getString("info");
        JSONObject v = options.getJSONObject("versions");
        for (String s : v.keySet())
            versions.put(s, v.getString(s));
    }
    
    public Category getCategory() {
        return category;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public HashMap<String, String> getVersions() {
        return versions;
    }
    
    public UUID getId() {
        return id;
    }
    
    public int getRevision() {
        return revision;
    }
    
    public String getInfoURL() {
        return info;
    }
    
    public String getLatestURL() {
        return latest;
    }
    
    public void addLatestVersion(String name, String url) {
        versions.put(name, url);
        options.getJSONObject("versions").put(name, url);
        options.put("latest", url);
        
        revision++;
        options.put("revision", revision);
        
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getJSON() {
        return options.toString();
    }
    
    public void load() {
        versions.clear();
        
        name = options.getString("name");
        author = options.getString("author");
        this.id = UUID.fromString(options.getString("id"));
        revision = options.getInt("revision");
        category = Category.of(options.getString("category"));
        latest = options.getString("latest");
        info = options.getString("info");
        JSONObject v = options.getJSONObject("versions");
        for (String s : v.keySet())
            versions.put(s, v.getString(s));
    }
    
}
