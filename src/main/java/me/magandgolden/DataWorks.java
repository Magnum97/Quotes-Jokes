package me.magandgolden;

import de.leonhard.storage.Yaml;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class DataWorks {

	private final QuotesJokes plugin = QuotesJokes.getPlugin();
	private final Yaml file; // This is final - but not initalized. See below
	private final LinkedList <String> list = new LinkedList <>(); // List of joke / quotes
	private final TreeMap <String, String> map = new TreeMap <>(); // Map of joke & uuid
	/* final means that it can NOT be changed once it is set.
	 * The reason we do not need to update is every time you to /joke
	 * is creates a new instance of DataWorks, with fresh list and map.
	 */

	/* No need to pass file to each method anymore
	 * because we do new DataWorks (file) */
	public DataWorks (Yaml file) { // No need to pass file to each method, as it is send
		// This sets the value of file from above everytime we call on DataWorks
		// so it does not need initialized.
		this.file = file;
		// Update map every time we call Dataworks
		for (String key : file.keySet()) {
			List <String> userList = new ArrayList <>(file.getStringList(key));
			userList.forEach(j -> map.put(j, key));
			list.addAll(userList);
		}
	}

	void sendRandom (CommandSender sender) {
		int jokeCount = list.size();
		int choice = ThreadLocalRandom.current().nextInt(jokeCount);
		String message = ChatColor.translateAlternateColorCodes('&', list.get(choice));
		sender.sendMessage(message);
	}

	/* No need to pass anything besides command sender */
	void sendList (CommandSender sender) {
		for (int i = 0; i < list.size(); i++) {
			String message = ChatColor.translateAlternateColorCodes('&', list.get(i));
			sender.sendMessage((i + 1) + ": " + message);
		}

	}

	/* Command class sets uuid to player id OR console if issues from console
	 * no need to pass file name or type */
	void addFile (CommandSender sender, String uuid, String text) {
		// First get a list of joke THIS user id has
		List <String> localList = new ArrayList <>(file.getStringList(uuid));
		// Add to local list
		localList.add(text);
		// Add to file with uuid, and updated list
		file.set(uuid, localList);
		file.write();
		sender.sendMessage(text + " has been added to " + uuid);
	}

	/* I'm sure you got this already but STILL no need to pass
	 * file or type to method ^_^
	 */
	void removeFile (CommandSender sender, int remove) {
		remove--;
		sender.sendMessage("Removed " + list.get(remove));
		String uuid = map.get(remove); // Find out who owns joke
		map.remove(list.get(remove)); // Remove joke from their uuid
		file.set(uuid,map); // Set new map to file
		file.write();
	}

	boolean userOverLimit (String uuid, String type) {
		// Ignore limit for console
		if (uuid.equals("console"))
			return false;
		return (file.getStringList(uuid).size() >= plugin.getCfg().getInt("user-limit." + type));
	}

}
