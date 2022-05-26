package ca.robinssoftware.mpack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public abstract class JSONFile {

    private final File file;
    JSONObject options;

    public JSONFile(File file) throws IOException {
        this(file, false, null);
    }

    public JSONFile(File file, boolean create) throws IOException {
        this(file, create, null);
    }

    public JSONFile(File file, boolean create, JSONObject defaults) throws IOException {
        this.file = file;

        if (!file.exists()) {
            if (!create)
                return;

            file.getParentFile().mkdirs();
            file.createNewFile();
            options = defaults;
            save();
            return;
        }

        char[] buffer = new char[(int) file.length()];
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        reader.read(buffer);
        reader.close();
        options = new JSONObject(new String(buffer));
    }

    public void save() throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8);
        String output = options == null ? "{}" : options.toString(2);
        writer.write(output);
        writer.close();
    }

}
