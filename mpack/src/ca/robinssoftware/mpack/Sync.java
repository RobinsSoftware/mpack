package ca.robinssoftware.mpack;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.json.JSONObject;

import ca.robinssoftware.mpack.Language.Fields;

public class Sync extends Thread {

    private final Main main;
    private final CommandSender sender;
    private static volatile boolean launched = false;

    Sync(Main main, CommandSender sender) {
        this.main = main;
        this.sender = sender;

        if (launched) {
            sender.sendMessage(main.language.get(Fields.PREFIX)
                    + main.language.get(Fields.SYNC_FAILED, "Sync has already started"));
            return;
        }

        this.setName("Mpack Sync");
        this.start();

        launched = true;
    }

    @Override
    public void run() {
        sender.sendMessage(main.language.get(Fields.PREFIX) + main.language.get(Fields.SYNC));
        main.loaded.clear();

        for (Overlay o : main.overlays) {
            try {
                Socket socket = new Socket(o.getHost().split(":")[0], Integer.parseInt(o.getHost().split(":")[1]));
                
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                String name = new JSONObject(input.readLine()).getString("name");
                
                output.write(new JSONObject().put("request", "id_list").toString() + "\n");
                
                JSONObject in = new JSONObject(input.readLine());
                for (String str : in.keySet()) {
                    File file = new File(main.getDataFolder().getPath() + "/packagedata/" + str + ".mpack");
                    
                    if (file.exists()) {
                        Package pkg = new Package(main, file);
                        
                        if (pkg.getRevision() < in.getInt(str)) {
                            output.write(new JSONObject().put("request", "package").put("id", str).toString() + "\n");
                            
                            pkg.options = new JSONObject(input.readLine());
                            pkg.save();
                            pkg.load();
                        }
                        
                        main.loaded.add(pkg);
                        main.nameMap.put(pkg.getName(), pkg);
                    } else {
                        output.write(new JSONObject().put("request", "package").put("id", str).toString() + "\n");
                        
                        Package pkg = new Package(main, UUID.fromString(str), new JSONObject(input.readLine()));
                        main.loaded.add(pkg);
                        main.nameMap.put(pkg.getName(), pkg);
                    }
                }
                
                sender.sendMessage(main.language.get(Fields.PREFIX) + main.language.get(Fields.SYNC_OVERLAY, name));
                
                output.close();
                input.close();
                socket.close();
            } catch (NumberFormatException | IOException e) {
                e.printStackTrace();
            }
        }
        
        // finalize
        sender.sendMessage(main.language.get(Fields.PREFIX) + main.language.get(Fields.SYNC_FINISHED));
        launched = false;
    }

}
